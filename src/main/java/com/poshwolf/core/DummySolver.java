package com.poshwolf.core;

public class DummySolver implements Solver {

  @Override
  public Result solve(TaskDefinition task, ProgressListener listener) {
  
    long startTime = System.nanoTime();
    
    Result r = new Result();      
                  
    int optimalExecutionTimespan = Integer.MAX_VALUE;
    for (int i = 0; i < 10; i++) {
      optimalExecutionTimespan = Math.min(optimalExecutionTimespan, computeAnotherSolution());
      listener.onProgress((i+1)*10, optimalExecutionTimespan);
    }
    
    int[] o = new int[task.getJobCount()];
    for (int j = 0; j < task.getJobCount(); j++)
      o[j] = j + 1;
    
    double computationTime = (System.nanoTime() - startTime) / 1e9;
    
    r.setJobOrder(o);
    r.setExecutionTimespan(optimalExecutionTimespan);        
    r.setComputationTime(computationTime);
    
    return r;
  }
  
  private int computeAnotherSolution() {
    try {
      Thread.sleep(10);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    return (int)(Math.random() * 300 + 2000);
  }
}

