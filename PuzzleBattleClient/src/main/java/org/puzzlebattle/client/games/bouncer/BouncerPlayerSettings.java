package org.puzzlebattle.client.games.bouncer;

import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class BouncerPlayerSettings {
  private Color color;
  private KeyCode left, right;
  private double movementIntensity;
}
