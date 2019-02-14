package org.puzzlebattle.client.screen;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import lombok.Getter;

import java.util.ArrayList;

@Getter
public abstract class AbstractScreen {
  @Getter
  private VBox root;
  private ArrayList<Timeline> scheduledTasks = new ArrayList<>();
  @Getter
  private Stage stage;

  public AbstractScreen(Stage stage) {
    this.stage = stage;
    this.root = new VBox();
  }

  public double getHeight() {
    return 480;
  }

  public String getTitle() {
    return "Puzzle Battle - " + getClass().getSimpleName().replace("Screen", "");
  }

  public double getWidth() {
    return 640;
  }

  public void onClose() {
  }

  private void onCloseHandler() {
    scheduledTasks.forEach(Timeline::stop);
    onClose();
  }

  public Timeline scheduleAtFixedRate(long duration, Runnable task) {
    Timeline tl = new Timeline(new KeyFrame(Duration.millis(duration), e -> task.run()));
    tl.setCycleCount(Timeline.INDEFINITE);
    tl.play();
    scheduledTasks.add(tl);
    return tl;
  }

  public void show() {
    Scene scene = new Scene(root, getWidth(), getHeight());
    stage.setTitle(getTitle());
    stage.setScene(scene);
    stage.show();
    stage.setOnCloseRequest(e -> onCloseHandler());
  }
}
