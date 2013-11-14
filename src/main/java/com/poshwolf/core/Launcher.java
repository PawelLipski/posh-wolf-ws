
package com.poshwolf.core;

import java.util.Scanner;

public class Launcher {

  public static void main(String[] args) {    
    Scanner sc = new Scanner(System.in);
    
    int no = 0;
    while (sc.hasNextLine()) {      
      sc.nextLine();
      
      int jobCnt = sc.nextInt();
      
      int machineCnt = sc.nextInt();
      sc.nextInt();
      int upperBound = sc.nextInt();
      int lowerBound = sc.nextInt();          
      sc.nextLine();
      
      sc.nextLine();
      
      int[][] opDurationsForJobs = new int[jobCnt][machineCnt];
      
      for (int m = 0; m < machineCnt; m++) {
	for (int j = 0; j < jobCnt; j++) {
	  opDurationsForJobs[j][m] = sc.nextInt();
	  //System.out.println(opDurationsForJobs[j][m]);
	}
      }       
      sc.nextLine();
      
      Solver solver = new DummySolver();
      TaskDefinition task = new TaskDefinition(jobCnt, machineCnt, opDurationsForJobs);
      solver.solve(task, new DummyProgressListener());
      
      System.out.println(no + ": " + jobCnt + "/" + machineCnt);
      no++;
    }
  }
}
