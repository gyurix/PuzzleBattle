package org.puzzlebattle.client.games.bouncer;

import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.stage.Stage;
import org.puzzlebattle.client.screen.AbstractScreen;

import static org.puzzlebattle.client.ClientLauncher.lang;


/**
 * Screen for Ball Bouncer game. Bouncers are displayed with a ball and score.
 *
 * @author (Juraj Barath, Jakub Perdek)
 * @version (1.0)
 */

public class BallBouncerScreen extends AbstractScreen {
  private BouncerGame game;


  /**
   * Constructor which creates screen inherited from abstract screen.
   * Background is set and all components are added into Pane
   * Movement of the ball is simulated here too.
   */

  public BallBouncerScreen(Stage stage, BouncerGame game) {
    super(stage);
    this.game = game;
    pane.setBackground(new Background(new BackgroundFill(game.getSettings().getBackgroundColor(), null, null)));
    pane.getChildren().addAll(game.getBall(), game.getEnemy().getBouncer(), game.getEnemy().getGoals(), game.getYou().getBouncer(), game.getYou().getGoals());
    scheduleAtFixedRate(16, game::tick);
  }


  /**
   * An example of a method - replace this comment with your own
   *
   * @param  y  a sample parameter for a method
   * @return    the sum of x and y
   */

  @Override
  public double getHeight() {
    return game.getMapSize().getY();
  }


  /**
   * An example of a method - replace this comment with your own
   *
   * @param  y  a sample parameter for a method
   * @return    the sum of x and y
   */

  @Override
  public double getWidth() {
    return game.getMapSize().getX();
  }


  /**
   * Events which enables control bouncers by users are initialised here
   *
   * @param  scene  scene where key events should be applied
   */

  @Override
  public void registerEvents(Scene scene) {
    scene.setOnKeyPressed((e) -> game.onKeyEvent(e.getCode(), true));
    scene.setOnKeyReleased((e) -> game.onKeyEvent(e.getCode(), false));
  }


  /**
   * Method which is applied on the close
   *
   */

  @Override
  public void onClose() {
    lang.msg("end.finished", "name", "Puzzle Battle Client", "version", "1.0");
  }
}
