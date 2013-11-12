
package com.poshwolf.core;


public class ResultWithOrder extends Result {

  // The order of jobs to be executed on each machine
  // found by the algorithm
  private int[][] jobOrderForMachines; // matrix: machinCount x jobCount

  public int[][] getJobOrderForMachines() {
      return jobOrderForMachines;
  }
  
  public void setJobOrderForMachines(int[][] jobOrderForMachines) {
      this.jobOrderForMachines = jobOrderForMachines;
  }
}
