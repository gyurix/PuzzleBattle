package org.puzzlebattle.server;

import javafx.application.Platform;
import lombok.Getter;
import org.puzzlebattle.core.utils.ErrorAcceptedRunnable;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadUtils {
  @Getter
  private static final ExecutorService executor = Executors.newSingleThreadExecutor();

  public static void async(ErrorAcceptedRunnable runnable) {
    executor.submit(runnable.toRunnable());
  }

  public static void ui(ErrorAcceptedRunnable runnable) {
    Platform.runLater(runnable.toRunnable());
  }
}
