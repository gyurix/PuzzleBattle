package org.puzzlebattle.client.games;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Abstract class which represents game.
 *
 * @author (Juraj Barath)
 * @version (1.0)
 */
@AllArgsConstructor
@Getter
public abstract class Game {
  private Object serverConnection;

  public abstract void updateData(int[] data);
}
