package org.puzzlebattle.server.game;

import org.puzzlebattle.core.entity.GameType;
import org.puzzlebattle.core.gamesettings.BallBouncerSettings;
import org.puzzlebattle.core.gamesettings.MainSettings;
import org.puzzlebattle.core.utils.ErrorAcceptedRunnable;
import org.puzzlebattle.server.ThreadUtils;
import org.puzzlebattle.server.db.entity.GameSettings;
import org.puzzlebattle.server.entity.Client;
import org.puzzlebattle.server.manager.ConfigManager;
import org.puzzlebattle.server.protocol.packets.out.ClientOutUpdateGame;

import java.util.Random;

import static java.lang.Math.abs;

public class BallBouncer extends Game implements ErrorAcceptedRunnable {
  private static final Random random = new Random();
  double ballX, ballY, ballVX, ballVY, ballRadius, p1X, p1Y, p2X, p2Y;
  int p1Goals;
  int p2Goals;
  private BallBouncerSettings settings;

  public BallBouncer(Client p1, Client p2, GameSettings settings) {
    super(p1, p2, settings);
  }

  private boolean checkBounce(double x, double y) {
    double distX = abs(ballX - x);
    double distY = abs(ballY - y);
    return distY < ballRadius + settings.getBouncerHeight() * 0.5 && distX < ballRadius + settings.getBouncerWidth() * 0.5;
  }

  @Override
  public void loadSettings() {
    settings = ConfigManager.getInstance().getConfig().getGameProfiles().getBallBouncer()
            .get(gameSettings.getProfileName());
  }

  @Override
  public MainSettings getSettings() {
    return settings;
  }

  @Override
  public GameType getType() {
    return GameType.BOUNCER;
  }

  @Override
  protected void start() {
    resetBall();
    resetPlayers();
    ThreadUtils.async(this);
  }

  @Override
  public void update(Client client, int[] data) {
    if (client == p1)
      p1X = Math.max(0, Math.min(settings.getMapMaxx() - settings.getBouncerWidth(), p1X + data[0] * settings.getMovementIntensity()));
    else
      p2X = Math.max(0, Math.min(settings.getMapMaxx() - settings.getBouncerWidth(), p2X + data[0] * settings.getMovementIntensity()));
  }

  public void resetBall() {
    ballX = settings.getMapMaxx() / 2;
    ballY = settings.getMapMaxy() / 2;
    ballRadius = settings.getMinBallRadius() + Math.random() * (settings.getMaxBallRadius() - settings.getMinBallRadius());
    ballVX = (random.nextBoolean() ? -1 : 1) * (1 + random.nextDouble() * 2);
    ballVY = (random.nextBoolean() ? -1 : 1) * (0.5 + random.nextDouble());
  }

  public void resetPlayers() {
    p1X = (settings.getMapMaxx() - settings.getBouncerWidth()) / 2;
    p1Y = settings.getMapMaxy() / 32 - 8;
    p2X = (settings.getMapMaxx() - settings.getBouncerWidth()) / 2;
    p2Y = settings.getMapMaxy() / 32 * 31 - 8;
  }

  @Override
  public void run() throws InterruptedException {
    while (p1.getGame() == this && p2.getGame() == this) {
      Thread.sleep(17);
      ballX += ballVX;
      ballY += ballVY;
      if (ballX - ballRadius < 0) {
        ballX = ballRadius;
        ballVX = -ballVX;
      } else if (ballX + ballRadius > settings.getMapMaxx()) {
        ballX = settings.getMapMaxx() - ballRadius;
        ballVX = -ballVX;
      }
      boolean p1Goal = false;
      if (checkBounce(p1X + settings.getBouncerWidth() / 2, p1Y + settings.getBouncerHeight())) {
        ballVX = (ballVX > 0 ? 1 : -1) * (Math.abs(ballVX / 2) + random.nextDouble());
        ballVY = Math.abs(ballVY) + random.nextDouble() / 2.0;
      } else
        p1Goal = ballY < p1Y - settings.getBouncerHeight() * 1.5;

      boolean p2Goal = false;
      if (checkBounce(p2X + settings.getBouncerWidth() / 2, p2Y + settings.getBouncerHeight())) {
        ballVX = (ballVX > 0 ? 1 : -1) * (Math.abs(ballVX / 2) + random.nextDouble());
        ballVY = -(Math.abs(ballVY) + random.nextDouble() / 2.0);
      } else
        p2Goal = ballY > p2Y + settings.getBouncerHeight() / 2;

      if (p1Goal || p2Goal) {
        if (p1Goal) {
          if (++p1Goals == settings.getMaxGoal()) {
            lose(p1);
            return;
          }
        } else {
          if (++p2Goals == settings.getMaxGoal()) {
            lose(p2);
            return;
          }
        }
        resetPlayers();
        resetBall();
      }
      p1.getHandler().sendPacket(new ClientOutUpdateGame(
              new int[]{p1Goal ? 1 : 0, p2Goal ? 1 : 0,
                      (int) p1X, (int) p1Y, (int) p2X, (int) p2Y,
                      (int) ballX, (int) ballY, (int) ballRadius}));

      p2.getHandler().sendPacket(new ClientOutUpdateGame(
              new int[]{p2Goal ? 1 : 0, p1Goal ? 1 : 0,
                      (int) p2X, (int) p2Y, (int) p1X, (int) p1Y,
                      (int) ballX, (int) ballY, (int) ballRadius}));
      if (p1Goal || p2Goal)
        Thread.sleep(200);
    }
  }
}
