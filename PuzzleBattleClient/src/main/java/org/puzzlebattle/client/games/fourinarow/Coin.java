package org.puzzlebattle.client.games.fourinarow;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;


/**
 * Representation of coin. All attributes which represents coin which will be reapinted in Pane
 * are stored here. For example coin radius, color, initial position.
 *
 * @author (Jakub Perdek)
 * @version (1.0)
 */

public class Coin extends Circle {

  private double Ycoordinate = 50;
  private double coinRadius = 25;
  private double initialPozitionXForFall;
  private double initialPositionYForFall;
  private double maximumHeighForFall;


  /**
   * Typical coin, circle shaped, with radius 25 and 50 from the top. Necessary is specify X coordinate, color (depends on player)
   * and maximum height which will coin fall from height from the top.
   */

  public Coin(double X, Color coinColor,double maximumHeightForFall) {
    this(X,50,25,coinColor,maximumHeightForFall);
  }


  /**
   * Specific coin, circle shaped too, but Y coordinate and coin radous can be additionally specified.
   */

  public Coin(double X, double Y, double coinRadius, Color coinColor, double maximumHeightForFall) {
      super(X,Y,coinRadius);

      this.coinRadius= coinRadius;
      this.setFill(coinColor);
      this.setStroke(coinColor);
      this.initialPozitionXForFall= X-coinRadius;
      this.initialPositionYForFall= Y;
      this.maximumHeighForFall= maximumHeightForFall;
  }


  /**
   * Method which returns coin radius from created coin.
   *
   * @return   coin radius from specific coin
   */

  public double getCoinRadius() {
      return coinRadius;
  }


  /**
   * Method which returns initial position X for coin fall.
   * This specifies position of column where coin will fall
   *
   * @return    initial position X for this coin, where will fall
   */

  public double getInitialPozitionXForFall() { return initialPozitionXForFall; }


  /**
   * Method which returns initial position Y for coin fall.
   * This specifies position of hight, where coin starts falling.
   *
   * @return    initial position Y for this coin, where will fall
   */

  public double getInitialPositionYForFall() { return initialPositionYForFall; }


  /**
   * Method which returns height, distance from initial position Y on which coin will fall
   *
   * @return   maximum height for fall, distance from top on which coin will fall
   */

  public double getMaximumHeighForFall() { return maximumHeighForFall; }

}
