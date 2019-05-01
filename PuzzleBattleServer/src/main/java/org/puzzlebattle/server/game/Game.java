package org.puzzlebattle.server.game;

import org.puzzlebattle.server.entity.Client;
import org.puzzlebattle.server.protocol.packets.out.ClientOutEndGame;

public abstract class Game {
  private Client p1;
  private Client p2;
  private long started;

  public Game(Client p1, Client p2) {
    this.p1 = p1;
    this.p2 = p2;
    started = System.currentTimeMillis();
  }

  public void end(Client client) {
    GameWinner winner = client == p1 ? GameWinner.P2 : GameWinner.P1;
    if (client == p1) {
      p1.getHandler().sendPacket(new ClientOutEndGame(GameWinner.P2));
      p2.getHandler().sendPacket(new ClientOutEndGame(GameWinner.P1));
    } else {
      p2.getHandler().sendPacket(new ClientOutEndGame(GameWinner.P2));
      p1.getHandler().sendPacket(new ClientOutEndGame(GameWinner.P1));
    }
    p1.setGame(null);
    p2.setGame(null);
    long time = System.currentTimeMillis();
  }
}
