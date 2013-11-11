
package com.poshwolf.core;

public class TaskDefinition {
  
  private int jobCount;

  private int machineCount;

  private int[][] opDurationsForJobs; // matrix: jobCount x machineCount

  
  public int getJobCount() {
      return jobCount;
  }
  
  public void setJobCount(int jobCount) {
      this.jobCount = jobCount;
  }
  
  public int getMachineCount() {
      return machineCount;
  }
  
  public void setMachineCount(int machineCount) {
      this.machineCount = machineCount;
  }
  
  public int[][] getOpDurationsForJobs() {
      return opDurationsForJobs;
  }
  
  public void setOpDurationsForJobs(int[][] opDurationsForJobs) {
      this.opDurationsForJobs = opDurationsForJobs;
  }
}
