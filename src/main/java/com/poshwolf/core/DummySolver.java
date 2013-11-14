package com.poshwolf.core;

public class DummySolver implements Solver {

  public Result solve(TaskDefinition task, ProgressListener listener) {
    Result r = new Result();

    r.setExecutionTimespan(2345);
    r.setComputationTime(23.45);

    int[] o = new int[task.getJobCount()];    
    for (int j = 0; j < task.getJobCount(); j++)
      o[j] = j + 1;
    r.setJobOrder(o);

    return r;
  }
}


