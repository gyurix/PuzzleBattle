package org.puzzlebattle.client.screen;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lombok.Getter;

import java.util.ArrayList;


/**
 * Screen where basic settings are prepared and Pane for drawing is created
 *
 * @author (Juraj Barath, Jakub Perdek)
 * @version (1.0)
 */

@Getter
public abstract class AbstractScreen {

  protected Pane pane;
  private Scene scene;

  private ArrayList<Timeline> scheduledTasks = new ArrayList<>();
  @Getter
  private Stage stage;


  /**
   * Screen where panel for repainting objects is created, then stage is used
   */

  public AbstractScreen(Stage stage) {
    this.stage = stage;
    pane = new Pane();
  }


  /**
   * Method which returns height of the screen
   *
   * @return    height of the screen, 480 by default
   */
  public double getHeight() {
    return 480;
  }


  /**
   * Method which returns title of the screen
   *
   * @return title of the screen, Puzzle Battle -name-
   *
   */
  public String getTitle() {
    return "Puzzle Battle - " + getClass().getSimpleName().replace("Screen", "");
  }


  /**
   * Method which returns width of the screen
   *
   * @return    width of the screen, 640 by default
   */

  public double getWidth() {
    return 640;
  }


  /**
   * Method where is speciffied what to do after closing a window
   *
   */

  public void onClose() {
  }


  /**
   * In this method is specified what to do before closing a window.
   * All scheduled tasks must be stopped.
   *
   */

  private void onCloseHandler() {
    scheduledTasks.forEach(Timeline::stop);
    onClose();
  }


  /**
   * New TimeLine will be created here, and task in specified duration will be launched
   *
   * @param  duration  duration of the specific task
   * @param  task  thread which will be launched
   * @return    created TimeLine
   */

  public Timeline scheduleAtFixedRate(long duration, Runnable task) {
    Timeline tl = new Timeline(new KeyFrame(Duration.millis(duration), e -> task.run()));
    tl.setCycleCount(Timeline.INDEFINITE);
    tl.play();
    scheduledTasks.add(tl);
    return tl;
  }

  /**
   * Method where register events are set
   *
   * @param  scene  scene which is used to register events
   */

  public void registerEvents(Scene scene) {
  }


  /**
   * New scene with applied pane where animations will be drawn.
   * Title, Height and width from this class will be set as size of the scene.
   *
   */
  public void show() {
    scene = new Scene(pane, getWidth(), getHeight());
    registerEvents(scene);
    stage.setTitle(getTitle());
    stage.setScene(scene);
    stage.show();
    stage.setOnCloseRequest(e -> onCloseHandler());
  }
}
