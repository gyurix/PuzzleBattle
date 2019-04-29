package org.puzzlebattle.client.games.bouncer;

import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.stage.Stage;
import org.puzzlebattle.client.screen.AbstractScreen;

import static org.puzzlebattle.core.utils.LangFile.lang;


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
   * Returns height of the map
   *
   * @return height of the map
   */
  @Override
  public double getHeight() {
    return game.getMapSize().getY();
  }

  /**
   * Returns width of the map
   *
   * @return width of the map
   */
  @Override
  public double getWidth() {
    return game.getMapSize().getX();
  }

  /**
   * Method which is applied on the close
   */
  @Override
  public void onClose() {
    lang.msg("end.finished", "name", "Puzzle Battle Client", "version", "1.0");
  }

  /**
   * Events which enables control bouncers by users are initialised here
   *
   * @param scene scene where key events should be applied
   */
  @Override
  public void registerEvents(Scene scene) {
    scene.setOnKeyPressed((e) -> game.onKeyEvent(e.getCode(), true));
    scene.setOnKeyReleased((e) -> game.onKeyEvent(e.getCode(), false));
  }
}
