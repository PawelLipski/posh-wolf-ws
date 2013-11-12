package com.poshwolf.core;

public class DummySolver implements Solver {

  public ResultWithOrder solve(TaskDefinition task, ProgressListener listener) {
    ResultWithOrder r = new ResultWithOrder();

    r.setExecutionTimespan(2345);
    r.setComputationTime(23.45);

    int[][] o = new int[task.getMachineCount()][task.getJobCount()];;
    for (int m = 0; m < task.getMachineCount(); m++) 
      for (int j = 0; j < task.getJobCount(); j++)
        o[m][j] = (m + 1) * (j + 1);
    r.setJobOrderForMachines(o);

    return r;
  }
}


