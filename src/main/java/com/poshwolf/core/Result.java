
package com.poshwolf.core;

public class Result {

  // The actual result - the minimal overall timespan of execution
  private int executionTimespan;

  // Time elapsed for computation (in seconds) must be provided
  private double computationTime;

  
  public int getExecutionTimespan() {
      return executionTimespan;
  }
  
  public void setExecutionTimespan(int executionTimespan) {
      this.executionTimespan = executionTimespan;
  }
  
  public double getComputationTime() {
      return computationTime;
  }
  
  public void setComputationTime(double computationTime) {
      this.computationTime = computationTime;
  }

}


