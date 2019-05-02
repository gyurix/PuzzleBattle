package org.puzzlebattle.server.game;

import org.puzzlebattle.core.entity.GameType;
import org.puzzlebattle.core.utils.ErrorAcceptedRunnable;
import org.puzzlebattle.server.ThreadUtils;
import org.puzzlebattle.server.db.entity.GameSettings;
import org.puzzlebattle.server.entity.Client;
import org.puzzlebattle.server.protocol.packets.out.ClientOutUpdateGame;

import java.util.Random;

import static java.lang.Math.abs;

public class BallBouncer extends Game implements ErrorAcceptedRunnable {
  private static final double BOUNCER_HEIGHT = 15;
  private static final double BOUNCER_WIDTH = 100;
  private static final int MAP_MAXX = 720;
  private static final int MAP_MAXY = 720;
  private static final double MAX_BALL_RADIUS = 35;
  private static final int MAX_GOAL = 5;
  private static final double MIN_BALL_RADIUS = 10;
  private static final double MOVEMENT_INTENSITY = 4.5;
  private static final Random random = new Random();
  double ballX, ballY, ballVX, ballVY, ballRadius, p1X, p1Y, p2X, p2Y;
  int p1Goals;
  int p2Goals;

  public BallBouncer(Client p1, Client p2, GameSettings settings) {
    super(p1, p2, settings);
  }

  private boolean checkBounce(double x, double y) {
    double distX = abs(ballX - x);
    double distY = abs(ballY - y + BOUNCER_HEIGHT / 2);
    return distY < ballRadius + BOUNCER_HEIGHT && distX < ballRadius + BOUNCER_WIDTH * 0.5;
  }

  @Override
  public GameType getType() {
    return GameType.BOUNCER;
  }

  public void resetBall() {
    ballX = MAP_MAXX / 2;
    ballY = MAP_MAXY / 2;
    ballRadius = MIN_BALL_RADIUS + Math.random() * (MAX_BALL_RADIUS - MIN_BALL_RADIUS);
    ballVX = (random.nextBoolean() ? -1 : 1) * (1 + random.nextDouble() * 2);
    ballVY = (random.nextBoolean() ? -1 : 1) * (0.5 + random.nextDouble());
  }

  public void resetPlayers() {
    p1X = (MAP_MAXX - BOUNCER_WIDTH) / 2;
    p1Y = MAP_MAXY / 32 - 8;
    p2X = (MAP_MAXX - BOUNCER_WIDTH) / 2;
    p2Y = MAP_MAXY / 32 * 31 - 8;
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
      } else if (ballX + ballRadius > MAP_MAXX) {
        ballX = MAP_MAXX - ballRadius;
        ballVX = -ballVX;
      }
      boolean p1Goal = false;
      if (checkBounce(p1X + BOUNCER_WIDTH / 2, p1Y + BOUNCER_HEIGHT)) {
        ballVX = (ballVX > 0 ? 1 : -1) * (Math.abs(ballVX / 2) + random.nextDouble());
        ballVY = Math.abs(ballVY) + random.nextDouble() / 2.0;
      } else
        p1Goal = ballY < p1Y - BOUNCER_HEIGHT * 1.5;

      boolean p2Goal = false;
      if (checkBounce(p2X + BOUNCER_WIDTH / 2, p2Y + BOUNCER_HEIGHT)) {
        ballVX = (ballVX > 0 ? 1 : -1) * (Math.abs(ballVX / 2) + random.nextDouble());
        ballVY = -(Math.abs(ballVY) + random.nextDouble() / 2.0);
      } else
        p2Goal = ballY > p2Y + BOUNCER_HEIGHT / 2;

      if (p1Goal || p2Goal) {
        if (p1Goal) {
          if (++p1Goals == MAX_GOAL) {
            lose(p2);
            return;
          }
        } else {
          if (++p2Goals == MAX_GOAL) {
            lose(p1);
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

  @Override
  protected void start() {
    resetBall();
    resetPlayers();
    ThreadUtils.async(this);
  }

  @Override
  public void update(Client client, int[] data) {
    if (client == p1)
      p1X = Math.max(0, Math.min(MAP_MAXX - BOUNCER_WIDTH, p1X + data[0] * MOVEMENT_INTENSITY));
    else
      p2X = Math.max(0, Math.min(MAP_MAXX - BOUNCER_WIDTH, p2X + data[0] * MOVEMENT_INTENSITY));
  }
}
