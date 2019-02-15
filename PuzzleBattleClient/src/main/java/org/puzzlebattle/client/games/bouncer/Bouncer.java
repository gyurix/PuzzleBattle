package org.puzzlebattle.client.games.bouncer;

import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;
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

  public void addRectangleOnCanvas(Pane canvas, double X, double Y) {
    canvas.getChildren().add(this);
  }

  public double getCenterX() {
    return getX() + getWidth() / 2;
  }

  public double getCenterY() {
    return getY() + getHeight() / 2;
  }

  private Point2D getVelocity(BouncerBall ball) {
    if (!contains(ball.getCenterX(), ball.getCenterY()))
      return null;
    double step = 5 / getWidth();
    double dif = (getX() - ball.getCenterX()) * step;
    return new Point2D(ball.getVelocity().getX(), -(5 - Math.abs(dif)));
  }
}
