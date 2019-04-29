package org.puzzlebattle.client.games.bouncer;

import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import lombok.AllArgsConstructor;
import lombok.Data;


/**
 * Player settings are stored here. For example movementIntensity or codes for keys,
 * which are used to control game while playing.
 *
 * @author (Juraj Barath)
 * @version (1.0)
 */
@AllArgsConstructor
@Data
public class BouncerPlayerSettings {
  private Color color, goalColor;
  private KeyCode left, right;
  private double movementIntensity;
}
