package com.poshwolf.core;

public interface Solver {

  ResultWithOrder solve(TaskDefinition task, ProgressListener listener);
}


