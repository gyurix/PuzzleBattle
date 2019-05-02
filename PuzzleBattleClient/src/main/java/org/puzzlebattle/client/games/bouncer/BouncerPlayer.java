package org.puzzlebattle.client.games.bouncer;


import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import lombok.Getter;


/**
 * Write a description of class SkladPonuka here.
 *
 * @author (Juraj Barath)
 * @version (1.0)
 */
@Getter
public class BouncerPlayer {
  private Bouncer bouncer;
  private BouncerGame game;
  private int goalCount;
  private Text goals;

  /**
   * Constructor for objects of class SkladPonuka
   */
  public BouncerPlayer(BouncerGame game, Bouncer bouncer, Color textColor) {
    this.game = game;
    this.bouncer = bouncer;
    goals = new Text(game.getSettings().getMapMaxx() - game.getSettings().getMapMaxx() / 32,
            bouncer.getCenterY(), String.valueOf(goalCount));
    goals.setFont(new Font(24));
    goals.setFill(textColor);
  }

  /**
   * If goal was given
   */
  public void goal() {
    ++goalCount;
    goals.setText(String.valueOf(goalCount));
  }
}
