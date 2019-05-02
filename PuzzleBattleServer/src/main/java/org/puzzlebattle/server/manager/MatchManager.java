package org.puzzlebattle.server.manager;

import lombok.Getter;
import org.puzzlebattle.core.entity.GameType;
import org.puzzlebattle.core.gamesettings.GameProfiles;
import org.puzzlebattle.core.utils.Logging;
import org.puzzlebattle.server.db.entity.GameSettings;
import org.puzzlebattle.server.entity.Client;
import org.puzzlebattle.server.game.BallBouncer;
import org.puzzlebattle.server.game.FourInARow;

import java.util.*;

public class MatchManager {
  @Getter
  private static final MatchManager instance = new MatchManager();
  private static final Random rand = new Random();
  private HashMap<GameType, Client> waitingClient = new HashMap<>();

  private String randomProfile(Map<String, ?> map) {
    List<String> profiles = new ArrayList<>(map.keySet());
    return profiles.get(rand.nextInt(profiles.size()));
  }

  public void start(Client client, GameType type) {
    Logging.logInfo("Adding to match waiting queue", "client", client, "gameType", type);
    Client waiting = waitingClient.remove(type);
    if (waiting != null) {
      GameProfiles gameProfiles = ConfigManager.getInstance().getConfig().getGameProfiles();
      if (type == GameType.FOUR_IN_A_ROW)
        new FourInARow(waiting, client,
                new GameSettings(type.ordinal(), randomProfile(gameProfiles.getFourInARow())));
      else
        new BallBouncer(waiting, client,
                new GameSettings(type.ordinal(), randomProfile(gameProfiles.getBallBouncer())));
      return;
    }
    for (GameType gt : GameType.values())
      waitingClient.remove(gt, client);
    waitingClient.put(type, client);
  }
}
