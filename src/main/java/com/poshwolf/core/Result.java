
package com.poshwolf.core;

public class Result {

  // The actual result - the minimal overall timespan of execution
  private int executionTimespan;

  // Time elapsed for computation (in seconds) must be provided
  private double computationTime;

  // The iteration in which the result has been reached
  private int iterationsUntilResult;
  
  // The order of jobs to be executed on each machine
  // found by the algorithm
  private int[] jobOrder; // array of length jobCount

  
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
  
  public int[] getJobOrder() {
      return jobOrder;
  }
  
  public void setJobOrder(int[] jobOrder) {
      this.jobOrder = jobOrder;
  }

  
  public int getIterationsUntilResult() {
      return iterationsUntilResult;
  }
  
  public void setIterationsUntilResult(int iterationsUntilResult) {
      this.iterationsUntilResult = iterationsUntilResult;
  }
}


