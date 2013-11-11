package com.poshwolf.core;

public interface Solver {

  ComputationResult solve(TaskDefinition task, ProgressListener listener);
}


