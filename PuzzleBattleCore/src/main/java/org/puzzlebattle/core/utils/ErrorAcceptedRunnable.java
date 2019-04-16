package org.puzzlebattle.core.utils;

public interface ErrorAcceptedRunnable {
  void run() throws Throwable;

  default Runnable toRunnable() {
    return () -> {
      try {
        run();
      } catch (Throwable e) {
        e.printStackTrace();
      }
    };
  }
}
