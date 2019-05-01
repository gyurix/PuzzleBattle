package org.puzzlebattle.server.game;

import org.puzzlebattle.core.entity.GameType;
import org.puzzlebattle.server.db.entity.GameSettings;
import org.puzzlebattle.server.entity.Client;

public class BallBouncer extends Game {
  private static final int MAP_MAXX = 720;
  private static final int MAP_MAXY = 720;
  double ballX, ballY, p1X, p2X;

  public BallBouncer(Client p1, Client p2, GameSettings settings) {
    super(p1, p2, settings);
  }

  @Override
  public GameType getType() {
    return GameType.BOUNCER;
  }

  @Override
  protected void start() {

  }

  @Override
  public void update(Client client, int[] data) {

  }
}
