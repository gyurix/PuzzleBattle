package org.puzzlebattle.client.games.ballbouncer;

import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import lombok.Getter;

@Getter
public class Ball extends BallBouncerEntity {
  private double acceleration = 0.01;
  private Point2D loc;
  private double radius = 10;
  private Point2D velocity;

  public Ball(BallBouncerGame game) {
    super(game);
    loc = game.getMapSize().multiply(0.5);
    velocity = new Point2D(-3, 5);
  }

  public void render(Canvas canvas) {

  }

  public void tick() {
    loc = loc.add(velocity);
    velocity = velocity.multiply(1 - acceleration);
  }
}
