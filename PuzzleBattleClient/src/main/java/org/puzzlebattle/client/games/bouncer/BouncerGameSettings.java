package org.puzzlebattle.client.games.bouncer;

import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import lombok.Data;
import org.puzzlebattle.client.databaseTables.GameSettings;

/**
 * Setting for a ball bouncer game are stored here. Settings for both players, you and enemy are created
 * here too. Special values which contain are ready for use.
 *
 * @author (Juraj Barath, Jakub Perdek)
 * @version (1.0)
 */
@Data
public class BouncerGameSettings extends GameSettings {

  private Color backgroundColor = Color.BLACK;
  private double bouncerArcRadius = 15;
  private Color bouncerBallColor = Color.BLUE;
  private BouncerPlayerSettings enemy = new BouncerPlayerSettings(Color.RED, Color.INDIANRED, KeyCode.A, KeyCode.D, 4.5);
  private BouncerPlayerSettings you = new BouncerPlayerSettings(Color.GREEN, Color.GREEN, KeyCode.LEFT, KeyCode.RIGHT, 4.5);

  public int getGameType() {
    return super.gameType;
  }

  public void setGameType(int gameType) {
    super.gameType = gameType;
  }

  public long getId() {
    return super.id;
  }

  public void setId(int idd) {
    super.id = idd;
  }
}
