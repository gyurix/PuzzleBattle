package org.puzzlebattle.client.games.bouncer;

import javafx.geometry.Point2D;
import javafx.scene.shape.Circle;
import lombok.Getter;
import lombok.Setter;


/**
 * Write a description of class SkladPonuka here.
 *
 * @author (Juraj Barath)
 * @version (1.0)
 */

@Getter
public class BouncerBall extends Circle {
  private BouncerGame game;
  @Setter
  private Point2D velocity;


  /**
   * Constructor for objects of class SkladPonuka
   */

  public BouncerBall(BouncerGame game, double radius) {
    super(game.getMapSize().multiply(0.5).getX(), game.getMapSize().multiply(0.5).getY(), radius, game.getSettings().getBouncerBallColor());
    this.game = game;
  }


  /**
   * Thick of the ball
   *
   */

  public void tick() {
    double newX = getCenterX() + velocity.getX();
    double newY = getCenterY() + velocity.getY();
    if (newX - getRadius() < 0) {
      newX = getRadius();
      velocity = new Point2D(-velocity.getX(), velocity.getY());
    } else if (newX + getRadius() > game.getMapSize().getX()) {
      newX = game.getMapSize().getX() - getRadius();
      velocity = new Point2D(-velocity.getX(), velocity.getY());
    }
    setCenterX(newX);
    setCenterY(newY);
  }
}