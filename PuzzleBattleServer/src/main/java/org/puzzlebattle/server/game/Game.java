package org.puzzlebattle.server.game;

import org.puzzlebattle.core.entity.GameType;
import org.puzzlebattle.core.entity.GameWinner;
import org.puzzlebattle.core.gamesettings.MainSettings;
import org.puzzlebattle.core.utils.IOUtils;
import org.puzzlebattle.core.utils.Logging;
import org.puzzlebattle.server.db.entity.DurationDate;
import org.puzzlebattle.server.db.entity.GamePlayer;
import org.puzzlebattle.server.db.entity.GameResult;
import org.puzzlebattle.server.db.entity.GameSettings;
import org.puzzlebattle.server.entity.Client;
import org.puzzlebattle.server.protocol.packets.out.ClientOutEndGame;
import org.puzzlebattle.server.protocol.packets.out.ClientOutStartGame;

public abstract class Game {
  protected final GameSettings gameSettings;
  protected Client p1;
  protected Client p2;
  private GamePlayer gp1;
  private GamePlayer gp2;
  private long started;

  public Game(Client p1, Client p2, GameSettings gameSettings) {
    this.p1 = p1;
    this.p2 = p2;
    this.gameSettings = gameSettings;
    GamePlayer.withGamePlayer(p1.getUser(), getType(), (gp1) -> {
      this.gp1 = gp1;
      GamePlayer.withGamePlayer(p2.getUser(), getType(), (gp2) -> {
        this.gp2 = gp2;
        p1.setGame(this);
        p2.setGame(this);
        loadSettings();
        String settings = IOUtils.GSON.toJson(getSettings());
        p1.getHandler().sendPacket(new ClientOutStartGame(true, settings, getType()));
        p2.getHandler().sendPacket(new ClientOutStartGame(false, settings, getType()));
        start();
      });
    });
    started = System.currentTimeMillis();
  }

  public void draw() {
    Logging.logInfo("Marking game as draw");
    ClientOutEndGame packet = new ClientOutEndGame(GameWinner.DRAW);
    p1.getHandler().sendPacket(packet);
    p2.getHandler().sendPacket(packet);
    gp1.addScore(getSettings().getDrawScore());
    gp2.addScore(getSettings().getDrawScore());
    finish(GameWinner.DRAW);
  }

  public void finish(GameWinner winner) {
    p1.setGame(null);
    p2.setGame(null);
    long ended = System.currentTimeMillis();
    DurationDate duration = new DurationDate(started, ended);
    duration.persist((succeed) -> {
      if (succeed) {
        GameResult gameResult = new GameResult(duration, gameSettings, gp1, gp2, winner);
        gameResult.persist((r) ->
                Logging.logInfo("Added game result to the database succesfully",
                        "result", gameResult));
      }
    });
  }

  public abstract MainSettings getSettings();

  public abstract void loadSettings();

  public abstract GameType getType();

  public void lose(Client loser) {
    Logging.logInfo("Ending game", "loser", loser);
    if (loser == p1) {
      p1.getHandler().sendPacket(new ClientOutEndGame(GameWinner.P2));
      p2.getHandler().sendPacket(new ClientOutEndGame(GameWinner.P1));
      gp2.addScore(getSettings().getWinScore());
    } else {
      p2.getHandler().sendPacket(new ClientOutEndGame(GameWinner.P2));
      p1.getHandler().sendPacket(new ClientOutEndGame(GameWinner.P1));
      gp1.addScore(getSettings().getWinScore());
    }
    finish(loser == p1 ? GameWinner.P2 : GameWinner.P1);
  }

  protected abstract void start();

  public abstract void update(Client client, int[] data);
}
