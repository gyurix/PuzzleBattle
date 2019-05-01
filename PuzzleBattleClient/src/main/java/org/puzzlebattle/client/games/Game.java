package org.puzzlebattle.client.games;

import lombok.Getter;
import org.puzzlebattle.client.protocol.Client;
import org.puzzlebattle.core.entity.GameType;

/**
 * Abstract class which represents game.
 *
 * @author (Juraj Barath)
 * @version (1.0)
 */
@Getter
public abstract class Game {
  public Game(Client client) {
    this.client = client;
    client.setGame(this);
  }

  protected Client client;

  public abstract GameType getType();

  public abstract void updateData(int[] data);
}
