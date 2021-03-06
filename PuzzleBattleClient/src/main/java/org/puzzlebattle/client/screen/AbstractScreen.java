package org.puzzlebattle.client.screen;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;
import lombok.Getter;
import org.puzzlebattle.client.protocol.Client;
import org.puzzlebattle.core.utils.Logging;

import java.util.ArrayList;

import static org.puzzlebattle.core.utils.LangFile.lang;


/**
 * Screen where basic settings are prepared and Pane for drawing is created
 *
 * @author Juraj Barath, Jakub Perdek
 * @version 1.0
 */
@Getter
public abstract class AbstractScreen {
  protected final Client client;
  protected Pane pane;
  @Getter
  protected Stage stage;
  private Scene scene;
  private ArrayList<Timeline> scheduledTasks = new ArrayList<>();


  /**
   * Screen where panel for repainting objects is created, then stage is used
   */
  public AbstractScreen(Stage stage, Client client) {
    this.stage = stage;
    this.client = client;
    pane = new Pane();
  }

  /**
   * Default insets for any component used in inherited class
   *
   * @return insets with default values
   */
  protected Insets createDefaultInsets() {
    return new Insets(50, 100, 50, 100);
  }

  /**
   * Default font for every inherited screen
   *
   * @return
   */
  protected Font getDefaultFont() {
    return Font.font("Arial", FontWeight.BOLD, FontPosture.ITALIC, 25);
  }

  /**
   * Method which returns height of the screen
   *
   * @return height of the screen, 520 by default
   */
  public double getHeight() {
    return 520;
  }

  /**
   * Method which returns title of the screen
   *
   * @return title of the screen, Puzzle Battle -name-
   */
  public String getTitle() {
    return "Puzzle Battle - " + getClass().getSimpleName().replace("Screen", "");
  }

  /**
   * Method which returns width of the screen
   *
   * @return width of the screen, 640 by default
   */
  public double getWidth() {
    return 640;
  }

  /**
   * Method where is speciffied what to do after closing a window
   */
  public void onClose() {
    Logging.logInfo("Shutting down...");
    client.getConnection().getHandler().getChannel().close();
  }

  /**
   * In this method is specified what to do before closing a window.
   * All scheduled tasks must be stopped.
   */

  private void onCloseHandler() {
    scheduledTasks.forEach(Timeline::stop);
    onClose();
  }

  /**
   * Method where register events are set
   *
   * @param scene scene which is used to register events
   */
  public void registerEvents(Scene scene) {
  }

  /**
   * New TimeLine will be created here, and task in specified duration will be launched
   *
   * @param duration duration of the specific task
   * @param task     thread which will be launched
   * @return created TimeLine
   */
  public Timeline scheduleAtFixedRate(long duration, Runnable task) {
    Timeline tl = new Timeline(new KeyFrame(Duration.millis(duration), e -> task.run()));
    tl.setCycleCount(Timeline.INDEFINITE);
    tl.play();
    scheduledTasks.add(tl);
    return tl;
  }

  /**
   * New scene with applied pane where animations will be drawn.
   * Title, Height and width from this class will be set as size of the scene.
   */
  public void show() {
    scene = new Scene(pane, getWidth(), getHeight());
    registerEvents(scene);
    stage.setTitle(getTitle());
    stage.setScene(scene);
    stage.show();
    stage.setOnCloseRequest(e -> onCloseHandler());
    client.setOpenScreen(this);
  }

  /**
   * Shows alert with specified content and arguments
   *
   * @param type - type of alert
   * @param key  - specifies in language file type of information
   * @param args - arguments which should be displayed in context
   */
  public void showAlert(Alert.AlertType type, String key, Object... args) {
    Alert alert = new Alert(type);
    alert.setTitle(lang.get(key + ".title", args));
    alert.setContentText(lang.get(key + ".text", args));
    alert.showAndWait();
  }
}
