package org.puzzlebattle.client.games;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.puzzlebattle.client.protocol.Client;
import org.puzzlebattle.core.entity.GameType;

/**
 * Abstract class which represents game.
 *
 * @author (Juraj Barath)
 * @version (1.0)
 */
@AllArgsConstructor
@Getter
public abstract class Game {
  protected Client client;

  public abstract GameType getType();

  public abstract void updateData(int[] data);
}
