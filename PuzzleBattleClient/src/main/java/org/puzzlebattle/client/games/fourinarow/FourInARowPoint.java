package org.puzzlebattle.client.games.fourinarow;

import javafx.scene.paint.Color;

/**
 * Information about specific column, color of player and coins in the column below, where coin should fall.
 *
 * @author (Jakub Perdek)
 * @version (1.0)
 */
public class FourInARowPoint {

  private int coinsInColumnBelow;
  private Color colorOfPlayerForCoin;
  private FourInARowEntity fourInARowEntity;
  private int numberOfColumn;
  private FourInARowPlayer playerPoint;

  /**
   * Temporary stores information about future coin.
   * -number of column, place, column where coin should fall
   * -color of player, to distinguish player's move, player's coin
   * -coin in column below, which are useful to specific height, distance, which coin will fall
   */
  public FourInARowPoint(int numberOfColumn, Color colorOfPlayerForCoin, int coinsInColumnBelow, FourInARowEntity fourInARowEntity, FourInARowPlayer playerPoint) {

    this.numberOfColumn = numberOfColumn;
    this.colorOfPlayerForCoin = colorOfPlayerForCoin;
    this.coinsInColumnBelow = coinsInColumnBelow;
    this.fourInARowEntity = fourInARowEntity;
    this.playerPoint = playerPoint;
  }

  /**
   * Method which returns information about number of coins below. How many coins have been created in this column so far.
   *
   * @return coin in the column, under this coin
   */
  public int getCoinsInColumnBelow() {
    return coinsInColumnBelow;
  }

  /**
   * Returns information about color for coin, which represents player
   *
   * @return specific color, which represents player
   */
  public Color getColorOfPlayerForCoin() {
    return colorOfPlayerForCoin;
  }

  /**
   * Method which returns number of columns in the grid
   *
   * @return number of columns in grid
   */
  public int getNumberOfColumn() {
    return numberOfColumn;
  }

  /**
   * Try to find if player ont the move is winner
   *
   * @param fourInARowScreen screen of four in a row game
   */
  public void isWinner(FourInARowScreen fourInARowScreen) {
    fourInARowEntity.isWinningMove(playerPoint, coinsInColumnBelow + 1, numberOfColumn, fourInARowScreen);
  }

  /**
   * Apply draw ending game and showing dialog
   *
   * @param fourInARowScreen - screen of four in a row game
   */
  public void applyDraw(FourInARowScreen fourInARowScreen) {
    fourInARowEntity.applyDraw(playerPoint,fourInARowScreen);
  }

  /**
   * Apply ending game and showing dialog for loser
   *
   * @param fourInARowScreen - screen of four in a row game
   */
  public void applyLoser(FourInARowScreen fourInARowScreen) {
    fourInARowEntity.applyLose(playerPoint, fourInARowScreen);
  }
}
