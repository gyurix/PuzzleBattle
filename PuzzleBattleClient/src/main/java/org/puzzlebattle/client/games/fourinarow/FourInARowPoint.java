package org.puzzlebattle.client.games.fourinarow;

import javafx.scene.paint.Color;

public class FourInARowPoint {

  private int numberOfColumn;
  private Color colorOfPlayerForCoin;
  private int coinsInColumnBelow;

  public FourInARowPoint(int numberOfColumn,Color colorOfPlayerForCoin, int coinsInColumnBelow) {

    this.numberOfColumn= numberOfColumn;
    this.colorOfPlayerForCoin= colorOfPlayerForCoin;
    this.coinsInColumnBelow = coinsInColumnBelow;
  }

  public int getNumberOfColumn() { return numberOfColumn; }

  public Color getColorOfPlayerForCoin() { return colorOfPlayerForCoin; }

  public int getCoinsInColumnBelow() { return coinsInColumnBelow; }

}
