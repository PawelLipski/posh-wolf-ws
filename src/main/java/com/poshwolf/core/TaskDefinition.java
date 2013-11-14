
package com.poshwolf.core;

public class TaskDefinition {
  
  private int jobCount;

  private int machineCount;

  private int[][] opDurationsForJobs; // matrix: jobCount x machineCount

  public TaskDefinition() {
  }
  
  public TaskDefinition(int jobCount, int machineCount, int[][] opDurationsForJobs) {
    this.jobCount = jobCount;
    this.machineCount = machineCount;
    this.opDurationsForJobs = opDurationsForJobs;
  }
  
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
  
  public int[] getOpDurationsForJobs(int job) {
      return opDurationsForJobs[job];
  }
  
  public void setOpDurationsForJobs(int job, int[] opDurationsForJob) {
      this.opDurationsForJobs[job] = opDurationsForJob;
  }
  
  public int getOpDurationsForJobs(int job, int op) {
      return opDurationsForJobs[job][op];
  }
  
  public void setOpDurationsForJobs(int job, int op, int duration) {
      this.opDurationsForJobs[job][op] = duration;
  }
}
