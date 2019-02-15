package org.puzzlebattle.client.screen.games;

import javafx.stage.Stage;
import org.puzzlebattle.client.games.ballbouncer.BallBouncerGame;
import org.puzzlebattle.client.screen.AbstractScreen;

import static org.puzzlebattle.client.ClientLauncher.lang;

public class BallBouncerScreen extends AbstractScreen {
  private BallBouncerGame game;

  public BallBouncerScreen(Stage stage, BallBouncerGame game) {
    super(stage);
    this.game = game;
    game.getBall().addBallOnCanvas(super.canvas,250,250);
    game.getYou().getBouncer().addRectangleOnCanvas(super.canvas,100,20);
    game.getEnemy().getBouncer().addRectangleOnCanvas(super.canvas,100,400);
    scheduleAtFixedRate(16, this::render);
  }

  @Override
  public void onClose() {
    lang.msg("end.finished", "name", "Puzzle Battle Client", "version", "1.0");
  }

  public void render() {
    game.tick();
    game.getBall().render(super.canvas);
    game.getEnemy().getBouncer().render(super.canvas);
    game.getYou().getBouncer().render(super.canvas);
  }
}
