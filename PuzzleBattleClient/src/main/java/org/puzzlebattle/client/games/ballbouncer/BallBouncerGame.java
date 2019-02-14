package org.puzzlebattle.client.games.ballbouncer;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import lombok.Getter;

@Getter
public class BallBouncerGame {
  private Ball ball;
  private Point2D mapSize = new Point2D(400, 400);
  private BallBouncerPlayer you, enemy;

  public BallBouncerGame() {
    ball = new Ball(this);
    enemy = new BallBouncerPlayer(this,
            new Bouncer(
                    new Point2D(mapSize.getX() / 2, mapSize.getY() / 32),
                    new Point2D(15, 5), Color.RED)
    );
    you = new BallBouncerPlayer(this,
            new Bouncer(
                    new Point2D(mapSize.getX() / 2, mapSize.getY() - mapSize.getY() / 32),
                    new Point2D(15, 5), Color.GREEN)
    );
  }

  public Point2D getEnemyBouncingVelocity() {
    return null;
  }

  public Point2D getYouBouncingVelocity() {
    return null;
  }

  public boolean isEnemyFailed() {
    return false;
  }

  public boolean isSideBounce() {
    return ball.getLoc().getX() - ball.getRadius() < 0 || ball.getLoc().getX() + ball.getRadius() > mapSize.getX();
  }

  public boolean isYouFailed() {
    return false;
  }

  public void tick() {

  }
}