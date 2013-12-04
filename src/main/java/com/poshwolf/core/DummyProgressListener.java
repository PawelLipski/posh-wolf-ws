package com.poshwolf.core;

public class DummyProgressListener implements ProgressListener {
  
  @Override
  public void onProgress(int percentDone, int resultSoFar) {  
    System.out.printf("%d%%, %d\n", percentDone, resultSoFar);
  }
}

