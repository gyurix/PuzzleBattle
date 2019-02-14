package org.puzzlebattle.client.screen.games;

import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;
import org.puzzlebattle.client.games.ballbouncer.BallBouncerGame;
import org.puzzlebattle.client.screen.AbstractScreen;

public class BallBouncerScreen extends AbstractScreen {
  private Canvas canvas;
  private BallBouncerGame game;

  public BallBouncerScreen(Stage stage, BallBouncerGame game) {
    super(stage);
    this.game = game;
    scheduleAtFixedRate(16, this::render);
  }

  public void render() {
    System.out.println("Render");
    game.tick();
    game.getBall().render(canvas);
    game.getEnemy().getBouncer().render(canvas);
    game.getYou().getBouncer().render(canvas);
  }
}
