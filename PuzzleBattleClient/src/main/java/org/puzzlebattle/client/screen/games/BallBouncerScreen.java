package org.puzzlebattle.client.screen.games;

import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;
import org.puzzlebattle.client.games.ballbouncer.BallBouncerGame;
import org.puzzlebattle.client.screen.AbstractScreen;

import static org.puzzlebattle.client.ClientLauncher.lang;

public class BallBouncerScreen extends AbstractScreen {
  private Canvas canvas;
  private BallBouncerGame game;

  public BallBouncerScreen(Stage stage, BallBouncerGame game) {
    super(stage);
    this.game = game;
    scheduleAtFixedRate(16, this::render);
  }

  @Override
  public void onClose() {
    lang.msg("end.finished", "name", "Puzzle Battle Client", "version", "1.0");
  }

  public void render() {
    game.tick();
    game.getBall().render(canvas);
    game.getEnemy().getBouncer().render(canvas);
    game.getYou().getBouncer().render(canvas);
  }
}
