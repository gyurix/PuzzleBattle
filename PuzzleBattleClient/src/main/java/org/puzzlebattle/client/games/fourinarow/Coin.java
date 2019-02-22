package org.puzzlebattle.client.games.fourinarow;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;


/**
 * Representation of a coin in the Four in a Row game.
 * Includes all the important attributes, which are required for rendering it.
 *
 * @author Jakub Perdek, Juraj Barath
 * @version 1.0
 */
public class Coin extends Circle {
  /**
   * Creates a new Coin instance from the given arguments
   *
   * @param x      - The coins x coordinate
   * @param y      - The coins y coordinate
   * @param radius - The coins radius
   * @param color  - The coins color
   */
  public Coin(double x, double y, double radius, Color color) {
    super(x, y, radius);
    this.setFill(color);
    this.setStroke(color);
  }
}
