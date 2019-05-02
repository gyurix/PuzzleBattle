package org.puzzlebattle.client.games.bouncer;

import javafx.scene.input.KeyCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Player settings are stored here. For example movementIntensity or codes for keys,
 * which are used to control game while playing.
 *
 * @author (Juraj Barath)
 * @version (1.0)
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BouncerPlayerSettings {
  private String color, goalColor;
  private KeyCode left, right;
}
