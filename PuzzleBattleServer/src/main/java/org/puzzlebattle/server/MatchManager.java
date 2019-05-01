package org.puzzlebattle.server;

import lombok.Getter;
import org.puzzlebattle.core.entity.GameType;
import org.puzzlebattle.server.entity.Client;
import org.puzzlebattle.server.game.BallBouncer;
import org.puzzlebattle.server.game.FourInARow;

import java.util.HashMap;

public class MatchManager {
  @Getter
  private static final MatchManager instance = new MatchManager();
  private HashMap<GameType, Client> waitingClient = new HashMap<>();

  public void start(Client client, GameType type) {
    Client waiting = waitingClient.remove(type);
    if (waiting != null) {
      if (type == GameType.FOUR_IN_A_ROW)
        new FourInARow(waiting, client, null);
      else
        new BallBouncer(waiting, client, null);
      return;
    }
    for (GameType gt : GameType.values())
      waitingClient.remove(gt, client);
    waitingClient.put(type, client);
  }
}
