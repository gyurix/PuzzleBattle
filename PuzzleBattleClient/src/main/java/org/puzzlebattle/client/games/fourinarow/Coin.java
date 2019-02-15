package org.puzzlebattle.client.games.fourinarow;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Coin extends Circle {

  private double Ycoordinate = 50;
  private double coinRadius = 50;


  public Coin(double X, Color coinColor) {
    this(X,50,50,coinColor);
  }


  public Coin(double X, double Y, double coinRadius, Color coinColor) {
      super(X,Y,coinRadius);

      this.coinRadius= coinRadius;
      this.setFill(coinColor);
      this.setStroke(coinColor);
  }


  public double getCoinRadius() {
      return coinRadius;
  }



}
