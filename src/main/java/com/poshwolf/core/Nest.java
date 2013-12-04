package com.poshwolf.core;

import java.util.Random;

public class Nest {
	
	protected Random random;
	protected int fitness;
	protected int[] egg; 	/* EGG_NUMBER = 1 */
	protected Nest() {}
	
	public int getFitness() {
		return fitness;
	}
	
	public void setFitness(int fitness) {
		this.fitness = fitness;
	}
	
	public int[] getEgg() {
		return egg;
	}
	
	/* generate permutation */
	private int[] makeNewEgg(int eggSize) {
		int[] egg = new int[eggSize];
		for (int i = 0; i < egg.length; i++) {
	        int d = random.nextInt(i+1);
	        egg[i] = egg[d];
	        egg[d] = i;
	    }
	    return egg;
	}
	
	public Nest(Random random, int eggSize) {
		this.random = random;
		egg = makeNewEgg(eggSize);
	}
}
