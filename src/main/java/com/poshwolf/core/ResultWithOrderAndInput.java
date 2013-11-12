
package com.poshwolf.core;

public class ResultWithOrderAndInput extends ResultWithOrder {

  // The input task definition provided to the solver
  private TaskDefinition task;
  

  public TaskDefinition getTask() {
      return task;
  }
  
  public void setTask(TaskDefinition task) {
      this.task = task;
  }
}

