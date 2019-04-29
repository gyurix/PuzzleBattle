package org.puzzlebattle.client.games.bouncer;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import lombok.Getter;


/**
 * Bouncer with its attributes are prepared here.
 *
 * @author (Juraj Barath)
 * @version (1.0)
 */
@Getter
public class Bouncer extends Rectangle {
  private Color color;
  private BouncerGame game;

  /**
   * Constructor enables to create certain type of bouncer
   * Many attributes can be specified:
   * -color
   * -position
   * -size
   */
  public Bouncer(BouncerGame game, double x, double y, double width, double height, Color color) {
    super(x, y, width, height);
    this.game = game;
    setArcHeight(game.getSettings().getBouncerArcRadius());
    setArcWidth(game.getSettings().getBouncerArcRadius());
    this.color = color;
    setFill(color);
    setStroke(color);
  }

  /**
   * Tests if bouncer is on the ball
   *
   * @param ball - ball in BallBouncer game
   * @return true if ball is on bouncer, otherwise false
   */
  public boolean contains(BouncerBall ball) {
    for (double x = -1; x <= 1; x += 0.2) {
      for (double y = -1; y <= 1; y += 0.2) {
        if (contains(ball.getCenterX() + x * ball.getRadius(), ball.getCenterY() + y * ball.getRadius()))
          return true;
      }
    }
    return false;
  }

  /**
   * Apply velocity if ball bouncer hits a ball
   *
   * @param ball - ball in BallBouncer game
   * @param yMultiplier
   * @return null if bouncer contains a ball, otherwise new velocity
   */
  public Point2D getAppliedVelocity(BouncerBall ball, double yMultiplier) {
    if (!contains(ball))
      return null;
    double step = 5 / getWidth();
    double dif = (getX() - ball.getCenterX()) * step;
    return new Point2D(ball.getVelocity().getX() - dif, yMultiplier * (5 - Math.abs(dif) * 0.5));
  }

  /**
   * Returns center of ball bouncer game, position of bouncer and half of height
   *
   * @return center of y coordinate
   */
  public double getCenterY() {
    return getY() + getHeight() / 2;
  }
}
