package org.puzzlebattle.client.games.bouncer;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import lombok.Getter;


@Getter
public class Bouncer extends Rectangle {
  private Color color;
  private BouncerGame game;


  public Bouncer(BouncerGame game, double x, double y, double width, double height, Color color) {
    super(x, y, width, height);
    this.game = game;
    setArcHeight(game.getSettings().getBouncerArcRadius());
    setArcWidth(game.getSettings().getBouncerArcRadius());
    this.color = color;
    setFill(color);
    setStroke(color);
  }

  public double getCenterY() {
    return getY() + getHeight() / 2;
  }

  public boolean contains(BouncerBall ball) {
    for (double x = -1; x <= 1; x += 0.2) {
      for (double y = -1; y <= 1; y += 0.2) {
        if (contains(ball.getCenterX() + x * ball.getRadius(), ball.getCenterY() + y * ball.getRadius()))
          return true;
      }
    }
    return false;
  }

  public Point2D getAppliedVelocity(BouncerBall ball, double yMultiplier) {
    if (!contains(ball))
      return null;
    double step = 5 / getWidth();
    double dif = (getX() - ball.getCenterX()) * step;
    return new Point2D(ball.getVelocity().getX() - dif, yMultiplier * (5 - Math.abs(dif) * 0.5));
  }
}
