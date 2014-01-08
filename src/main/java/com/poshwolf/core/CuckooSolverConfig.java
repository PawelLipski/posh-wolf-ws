
package com.poshwolf.core;

public class CuckooSolverConfig {

  private int maxIterations;

  private int nestNumber;

  private int nestsToAbandonNumber;

  private double discoveryProbability;
  
  
  public int getMaxIterations() {
      return maxIterations;
  }
  
  public void setMaxIterations(int maxIterations) {
      this.maxIterations = maxIterations;
  }
  
  public int getNestNumber() {
      return nestNumber;
  }
  
  public void setNestNumber(int nestNumber) {
      this.nestNumber = nestNumber;
  }
  
  public int getNestsToAbandonNumber() {
      return nestsToAbandonNumber;
  }
  
  public void setNestsToAbandonNumber(int nestsToAbandonNumber) {
      this.nestsToAbandonNumber = nestsToAbandonNumber;
  }
  
  public double getDiscoveryProbability() {
      return discoveryProbability;
  }
  
  public void setDiscoveryProbability(double discoveryProbability) {
      this.discoveryProbability = discoveryProbability;
  }
}


