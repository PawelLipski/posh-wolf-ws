package com.poshwolf.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class CuckooSolver implements Solver {

  //public final static int NEST_NUMBER = 7;
  public final static int GENERATION_CAP = 500000;
  public final static int FIVE_PERCENT_GENS = GENERATION_CAP / 20;
  public final static double DISCOVERY_PROBABILITY = .2;
  public final static int NESTS_TO_ABANDON_COUNT = 2;

  private ArrayList<Nest> nests;
  private Random random;
  private CuckooSolverConfig config;

  public CuckooSolver(CuckooSolverConfig config) {
    this.config = config;
    random = new Random(System.currentTimeMillis());
  }

  @Override
  public Result solve(TaskDefinition task, ProgressListener listener) {

    long startTime = System.nanoTime();

    /* initial nests */
    nests = new ArrayList<Nest>(config.getNestNumber());
    for (int i = 0; i < config.getNestNumber(); i++) {
      Nest nest = new Nest(random, task.getJobCount());
      nests.add(nest); // jobCount <=> solution (egg) size
      nest.setFitness(computeMakespan(task, nest.getEgg()));
    }

    Nest bestSoFar = getBestNest();
    int iterationsUntilResult = 0;

    for (int t = 1; t <= GENERATION_CAP; t++) {

      int jIndex = random.nextInt(config.getNestNumber());
      Nest jNest = nests.get(jIndex);

      Nest nestWithCuckooEgg = new NestWithCuckooEgg(jNest);

      nestWithCuckooEgg.setFitness(computeMakespan(task, nestWithCuckooEgg.getEgg()));

      if (nestWithCuckooEgg.getFitness() > jNest.getFitness()) {
        nests.remove(jIndex);
        nests.add(nestWithCuckooEgg);
      }

      // With probability DISCOVERY_P we will abandon our worst nest and replace with new nests
      if (random.nextDouble() < DISCOVERY_PROBABILITY) {

        Collections.sort(nests, new Comparator<Nest>() {
            @Override
            public int compare(Nest o1, Nest o2) {
            return o2.getFitness() - o1.getFitness();
            }
            });

        ArrayList<Nest> replacementNests = new ArrayList<Nest>(NESTS_TO_ABANDON_COUNT);
        for (int i = 0; i < NESTS_TO_ABANDON_COUNT; ++i) {
          Nest nest = new Nest(random, task.getJobCount());
          replacementNests.add(nest); 
          nest.setFitness(computeMakespan(task, nest.getEgg()));  
        }

        nests.subList(0, NESTS_TO_ABANDON_COUNT).clear();
        nests.addAll(replacementNests);
      } 


      Nest bestNow = getBestNest();
      if (bestNow.getFitness() < bestSoFar.getFitness()) {
        bestSoFar = bestNow;
        iterationsUntilResult = t;
      }

      if (t % FIVE_PERCENT_GENS == 1) {
        int resultSoFar = bestSoFar.getFitness();
        listener.onProgress(t / FIVE_PERCENT_GENS * 5, resultSoFar);
      }

    }

    Collections.sort(nests, new Comparator<Nest>() {
        @Override
        public int compare(Nest o1, Nest o2) {
        return o1.getFitness() - o2.getFitness(); // reverse to above
        }
        });



    double computationTime = (System.nanoTime() - startTime) / 1e9;

    Result r = new Result();          
    r.setExecutionTimespan(bestSoFar.getFitness());
    r.setComputationTime(computationTime);
    r.setIterationsUntilResult(iterationsUntilResult);

    int[] order = bestSoFar.getEgg();
    for (int i = 0; i < order.length; i++)
      order[i]++;
    r.setJobOrder(order);

    return r;
  }

  private Nest getBestNest() {
    Nest result = nests.get(0);
    for (Nest nest: nests) 
      if (nest.getFitness() < result.getFitness())
        result = nest;    
    return result;
  }

  private int computeMakespan(TaskDefinition task, int[] permutation) {
    int[] timeMachine = new int[task.getMachineCount()]; // C(pi, M) = 0

    for (int i = 0; i < permutation.length; i++) { // N, , timeMachine[j] contains C(pi_(i-1), j)
      int[] currentJob = task.getOpDurationsForJobs(permutation[i]); // p[pi_i] 
      timeMachine[0] += currentJob[0]; // C(pi_i, 1) += p[pi_i, 1]

      for (int j = 1; j < timeMachine.length; j++) { // M
        if (timeMachine[j] > timeMachine[j - 1]) {
          timeMachine[j] = timeMachine[j] + currentJob[j]; // C(pi_i, j) = C(pi_(i-1), j) + p[pi_i, j]
        } else {
          timeMachine[j] = timeMachine[j - 1] + currentJob[j]; // C(pi_i, j) = C(pi_(i-1), j-1) + p[pi_i, j]
        }
      }
    }


    return timeMachine[task.getMachineCount() - 1];
  }
}

