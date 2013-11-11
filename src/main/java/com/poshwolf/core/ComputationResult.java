
package com.poshwolf.core;

public class ComputationResult {

  // The order of jobs to be executed on each machine
  // found by the algorithm
  private int[][] jobOrderForMachines; // matrix: machinCount x jobCount

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

  public int[][] getJobOrderForMachines() {
      return jobOrderForMachines;
  }
  
  public void setJobOrderForMachines(int[][] jobOrderForMachines) {
      this.jobOrderForMachines = jobOrderForMachines;
  }
}


