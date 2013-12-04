package com.poshwolf.core;

import java.util.ArrayList;
import java.util.Collections;

public class NestWithCuckooEgg extends Nest {

	private ArrayList<Integer> indexes;

	/* change permutation, we assume there is at least 2 indexes */
	private int[] modifyEgg() {
		Collections.shuffle(indexes, random);
		int j = indexes.get(0);
		int d = indexes.get(1);
	    /* swap */
	    egg[j] = egg[d] ^ egg[j]; 
	    egg[d] = egg[j] ^ egg[d];
	    egg[j] = egg[d] ^ egg[j];
	    return egg;
	}
	
	public NestWithCuckooEgg(Nest nest) {
		random = nest.random;
		egg = nest.egg;
		if(nest instanceof NestWithCuckooEgg)
			indexes = ((NestWithCuckooEgg) nest).indexes;
		else {
			indexes = new ArrayList<Integer>();
			for (int i = 0; i < egg.length; i++)
				indexes.add(i);
		}
		egg = modifyEgg();
	}
}
