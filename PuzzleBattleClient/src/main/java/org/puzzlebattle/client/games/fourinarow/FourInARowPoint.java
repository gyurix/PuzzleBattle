package org.puzzlebattle.client.games.fourinarow;

import javafx.scene.paint.Color;

public class FourInARowPoint {

  private int numberOfColumn;
  private Color colorOfPlayerForCoin;

  public FourInARowPoint(int numberOfColumn,Color colorOfPlayerForCoin) {

    this.numberOfColumn= numberOfColumn;
    this.colorOfPlayerForCoin= colorOfPlayerForCoin;
  }

  public int getNumberOfColumn() { return numberOfColumn; }

  public Color getColorOfPlayerForCoin() { return colorOfPlayerForCoin; }
}
