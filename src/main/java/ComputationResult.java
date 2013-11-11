package com.poshwolf.core;

public class ComputationResult {

  // The caller expects the task definition 
  // (i.e. number of jobs=J, number of machines=M, and op durations for jobs)
  // to be put into the computation result - into the 3 following fields
  private int jobCount;
  private int machineCount;
  private int[][] opDurationsForJobs; // matrix J x M


  // The order of jobs to be executed on each machine
  // found by the algorithm
  private int[][] jobOrderForMachines; // matrix M x J

  // The actual result - the minimal overall timespan of execution
  private int executionTimespan;

  // Time elapsed for computation (in seconds) must be provided
  private double computationTime;
}


