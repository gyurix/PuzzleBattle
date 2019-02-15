package org.puzzlebattle.client.games.bouncer;

import javafx.scene.Scene;
import javafx.stage.Stage;
import org.puzzlebattle.client.screen.AbstractScreen;

import static org.puzzlebattle.client.ClientLauncher.lang;

public class BallBouncerScreen extends AbstractScreen {
  private BouncerGame game;

  public BallBouncerScreen(Stage stage, BouncerGame game) {
    super(stage);
    this.game = game;
    pane.getChildren().addAll(game.getBall(), game.getEnemy().getBouncer(), game.getYou().getBouncer());
    scheduleAtFixedRate(16, game::tick);
  }

  @Override
  public void registerEvents(Scene scene) {
    scene.setOnKeyPressed((e) -> game.onKeyEvent(e.getCode(), true));
    scene.setOnKeyReleased((e) -> game.onKeyEvent(e.getCode(), false));
  }

  @Override
  public void onClose() {
    lang.msg("end.finished", "name", "Puzzle Battle Client", "version", "1.0");
  }
}
