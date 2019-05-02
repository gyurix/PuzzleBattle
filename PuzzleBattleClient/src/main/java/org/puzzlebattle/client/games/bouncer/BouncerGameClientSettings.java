package org.puzzlebattle.client.games.bouncer;

import lombok.Data;

/**
 * Setting for a ball bouncer game are stored here. Settings for both players, you and enemy are created
 * here too. Special values which contain are ready for use.
 *
 * @author (Juraj Barath, Jakub Perdek)
 * @version (1.0)
 */
@Data
public class BouncerGameClientSettings {

  private String backgroundColor;
  private double bouncerArcRadius;
  private String bouncerBallColor;
  private BouncerPlayerSettings enemy;
  private BouncerPlayerSettings you;
}
