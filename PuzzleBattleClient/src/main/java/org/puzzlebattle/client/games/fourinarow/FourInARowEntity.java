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
    this.mapOfPlayers = new int[columns + 1][rows + 1];
    setNullToAMapValues(mapOfPlayers, this.rows, this.columns);
  }

  private boolean analyseOfWinningMove(int row, int column, int playerNumber) {
    row = row - 1;
    column = column - 1;

    int pointsObtainedInARow = 0;
    int pointsObtainedInAColumn = 0;
    int toLeft, toRight, toDown;

    System.out.println("HERE " + mapOfPlayers[column][row]);
    if (mapOfPlayers[column][row] == playerNumber) {
      pointsObtainedInAColumn = pointsObtainedInARow = 1;
    }

    toLeft = column - 1;
    toRight = column + 1;
    toDown = row - 1;


    while (toLeft >= 0 && mapOfPlayers[toLeft][row] == playerNumber) {
      pointsObtainedInARow = pointsObtainedInARow + 1;
      toLeft = toLeft - 1;
    }

    while (toRight < columns && toRight >= 0 && mapOfPlayers[toRight][row] == playerNumber) {
      pointsObtainedInARow = pointsObtainedInARow + 1;
      toRight = toRight + 1;
    }

    while (toDown >= 0 && mapOfPlayers[column][toDown] == playerNumber) {
      pointsObtainedInAColumn = pointsObtainedInAColumn + 1;
      toDown = toDown - 1;
    }

    if (pointsObtainedInARow >= numberInRowToWin) {
      return true;
    } else return pointsObtainedInAColumn >= numberInRowToWin;

  }

  public void setNullToAMapValues(int[][] mapOfPlayers, int rowsOfMap, int columnsOfMap) {
    for (int i = 0; i < columnsOfMap; i = i + 1) {
      for (int j = 0; j < rowsOfMap; j = j + 1) {
        mapOfPlayers[i][j] = 0;
      }
    }
  }

  public void setPlayerNumberToAMap(FourInARowPlayer playerOnTheMove, int row, int column) {
    row = row - 1;
    column = column - 1;
    int playerNumber = playerOnTheMove.getPlayingNumber();
    this.mapOfPlayers[column][row] = playerNumber;
  }
}
