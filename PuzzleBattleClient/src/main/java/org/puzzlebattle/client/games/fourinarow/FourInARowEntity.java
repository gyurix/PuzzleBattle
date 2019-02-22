package org.puzzlebattle.client.games.fourinarow;

/**
 * Four in a row entity
 *
 * @author Jakub Perdek, Juraj Barath
 * @version 1.0
 */
public class FourInARowEntity {

  private int[][] mapOfPlayers;
  private int numberInRowToWin;
  private int rows, columns;

  public FourInARowEntity(int rows, int columns, int numberInRowToWin) {
    this.rows = rows;
    this.columns = columns;
    this.numberInRowToWin = numberInRowToWin;
    mapOfPlayers = new int[columns + 1][rows + 1];
    setNullToAMapValues(mapOfPlayers, this.rows, this.columns);
  }

  private boolean analyseOfWinningMove(int row, int column, int playerNumber) {
    int pointsObtainedInARow = 0;
    int pointsObtainedInAColumn = 0;
    int toLeft, toRight, toDown;

    if (mapOfPlayers[column][row] == playerNumber) {
      pointsObtainedInAColumn = pointsObtainedInARow = 1;
    }

    toLeft = row - 1;
    toRight = row + 1;
    toDown = column - 1;

    while (toLeft >= 0 && mapOfPlayers[column][toLeft] == playerNumber) {
      pointsObtainedInARow = pointsObtainedInARow + 1;
      toLeft = toLeft - 1;
    }

    while (toRight < columns && mapOfPlayers[column][toRight] == playerNumber) {
      pointsObtainedInARow = pointsObtainedInARow + 1;
      toRight = toRight + 1;
    }

    while (toDown >= 0 && mapOfPlayers[toDown][row] == playerNumber) {
      pointsObtainedInAColumn = pointsObtainedInAColumn + 1;
      toDown = toDown - 1;
    }

    if (pointsObtainedInARow >= numberInRowToWin) {
      return true;
    } else return pointsObtainedInAColumn >= numberInRowToWin;

  }

  public void isWinningMove(FourInARowPlayer playerOnTheMove, int row, int column, FourInARowScreen gameScreen) {
    int playerNumber = playerOnTheMove.getPlayingNumber();

    if (analyseOfWinningMove(row, column, playerNumber)) {
      gameScreen.showWinnerScreen(playerOnTheMove);
    } else {
      gameScreen.showDialogForNextMove();
    }
  }

  public void setNullToAMapValues(int[][] mapOfPlayers, int rowsOfMap, int columnsOfMap) {
    for (int i = 0; i < columnsOfMap; i = i + 1) {
      for (int j = 0; j < rowsOfMap; j = j + 1) {
        mapOfPlayers[i][j] = 0;
      }
    }
  }

  public void setPlayerNumberToAMap(FourInARowPlayer playerOnTheMove, int row, int column) {
    int playerNumber = playerOnTheMove.getPlayingNumber();
    mapOfPlayers[column][row] = playerNumber;
  }
}
