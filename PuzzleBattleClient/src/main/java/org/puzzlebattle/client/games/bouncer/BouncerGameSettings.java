package org.puzzlebattle.client.games.bouncer;

import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import lombok.Data;

@Data
public class BouncerGameSettings {
  private Color backgroundColor = Color.BLACK;
  private double bouncerArcRadius = 15;
  private Color bouncerBallColor = Color.BLUE;
  private BouncerPlayerSettings enemy = new BouncerPlayerSettings(Color.RED, Color.INDIANRED, KeyCode.A, KeyCode.D, 4.5);
  private BouncerPlayerSettings you = new BouncerPlayerSettings(Color.GREEN, Color.GREEN, KeyCode.LEFT, KeyCode.RIGHT, 4.5);
}
