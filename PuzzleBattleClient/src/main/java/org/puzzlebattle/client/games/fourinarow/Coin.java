package org.puzzlebattle.client.games.fourinarow;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;


public class Coin extends Circle {

  private double Ycoordinate = 50;
  private double coinRadius = 25;
  private double initialPozitionXForFall;
  private double initialPositionYForFall;
  private double maximumHeighForFall;

  public Coin(double X, Color coinColor,double maximumHeightForFall) {
    this(X,50,25,coinColor,maximumHeightForFall);
  }


  public Coin(double X, double Y, double coinRadius, Color coinColor, double maximumHeightForFall) {
      super(X,Y,coinRadius);

      this.coinRadius= coinRadius;
      this.setFill(coinColor);
      this.setStroke(coinColor);
      this.initialPozitionXForFall= X-coinRadius;
      this.initialPositionYForFall= Y;
      this.maximumHeighForFall= maximumHeightForFall;
  }


  public double getCoinRadius() {
      return coinRadius;
  }

  public double getInitialPozitionXForFall() { return initialPozitionXForFall; }

  public double getInitialPositionYForFall() { return initialPositionYForFall; }

  public double getMaximumHeighForFall() { return maximumHeighForFall; }

}
