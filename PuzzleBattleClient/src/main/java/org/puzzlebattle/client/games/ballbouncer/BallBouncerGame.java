package org.puzzlebattle.client.games.ballbouncer;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import lombok.Getter;
import org.puzzlebattle.client.games.Game;

@Getter
public class BallBouncerGame extends Game {
  private Ball ball;
  private Point2D mapSize = new Point2D(400, 400);
  private BallBouncerPlayer you, enemy;

  private double radiusOfBall = 25;

  private double angleBouncer1 = 15.0;
  private double angleBouncer2 = 15.0;

  private double widthOfOurBouncer= 100;
  private double heightOfOurBouncer= 15;
  private double widthOfEnemyBouncer= 100;
  private double heightOfEnemyBouncer= 15;

  //simple bouncer
  public BallBouncerGame() {
    this(25,100,15,100,15,15,15);
  }

  //bouncer with additional settings
  public BallBouncerGame(double radiusOfBall, double widthOfOurBouncer,double heightOfOurBouncer, double widthOfEnemyBouncer,
                         double heightOfEnemyBouncer, double angle1, double angle2)
  {
      ball = new Ball(this,radiusOfBall);
      createEnemy();
      createYou();
      //initialising variables
      this.radiusOfBall= radiusOfBall;
      this.widthOfOurBouncer= widthOfOurBouncer;
      this.heightOfOurBouncer= heightOfOurBouncer;
      this.widthOfEnemyBouncer= widthOfEnemyBouncer;
      this.heightOfEnemyBouncer= heightOfEnemyBouncer;
      this.angleBouncer1= angle1;
      this.angleBouncer2= angle2;
  }

  private void createYou()
  {
    you = new BallBouncerPlayer(this,
            new Bouncer(
                    new Point2D(mapSize.getX() / 2, mapSize.getY() - mapSize.getY() / 32),
                    new Point2D(widthOfOurBouncer, heightOfEnemyBouncer),
                    Color.GREEN, angleBouncer1, angleBouncer2)
    );
  }

  private void createEnemy()
  {
    enemy = new BallBouncerPlayer(this,
            new Bouncer(
                    new Point2D(mapSize.getX() / 2, mapSize.getY() / 32),
                    new Point2D(widthOfEnemyBouncer, heightOfEnemyBouncer),
                    Color.RED, angleBouncer1, angleBouncer2)
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