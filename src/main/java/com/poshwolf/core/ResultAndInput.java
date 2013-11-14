
package com.poshwolf.core;

public class ResultAndInput {

  // The solution given by a Solver
  private Result result;
  
  // The input task definition provided to the solver
  private TaskDefinition task;


  public ResultAndInput() {
  }

  public ResultAndInput(TaskDefinition task, Result result) {
    this.task = task;
    this.result = result;
  }

  public TaskDefinition getTask() {
      return task;
  }
  
  public void setTask(TaskDefinition task) {
      this.task = task;
  }
  
  public Result getResult() {
      return result;
  }
  
  public void setResult(Result result) {
      this.result = result;
  }
}

