package org.puzzlebattle.client.games.bouncer;


import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import lombok.Getter;

@Getter
public class BouncerPlayer {
  private BouncerGame game;
  private Bouncer bouncer;
  private int goalCount;
  private Text goals;

  public BouncerPlayer(BouncerGame game, Bouncer bouncer, Color textColor) {
    this.game = game;
    this.bouncer = bouncer;
    goals = new Text(game.getMapSize().getX() - game.getMapSize().getX() / 32, bouncer.getCenterY(), String.valueOf(goalCount));
    goals.setFont(new Font(24));
    goals.setFill(textColor);
  }

  public void goal() {
    ++goalCount;
    goals.setText(String.valueOf(goalCount));
    game.resetBall();
  }
}
