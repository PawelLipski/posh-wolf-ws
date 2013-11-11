package com.poshwolf.core;

public interface ProgressListener {

  // TODO: set proper params for this method 
  // (basing on the actual implemention of the cuckoo algorithm)
  // these ones may be awkward/impossible to use
  void onProgress(int percentDone, int resultSoFar);
}

