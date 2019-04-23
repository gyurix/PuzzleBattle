package org.puzzlebattle.server.db.managingScore;

import org.puzzlebattle.server.db.entity.GameTable;

import java.sql.Timestamp;

public class FourInARowScore {

  private void saveWinnerToDatabase(int winningPlayersNumber, GameTable gameTable, boolean draw) {

    System.out.println("WINING NUMBER " + winningPlayersNumber);
    gameTable.getDuration().setEndDate(new Timestamp(System.currentTimeMillis()));
    if (draw) {
      gameTable.setWinner(GameTable.Winner.DRAW);
      gameTable.getPlayer1().setScore(10);
      gameTable.getPlayer2().setScore(10);
    } else if (winningPlayersNumber == 1) {
      gameTable.setWinner(GameTable.Winner.P1);
      gameTable.getPlayer1().setScore(100);
      gameTable.getPlayer2().setScore(0);
    } else {
      gameTable.setWinner(GameTable.Winner.P2);
      gameTable.getPlayer1().setScore(0);
      gameTable.getPlayer2().setScore(100);
    }
    GameTable.updateGameTableInDB(gameTable);
  }
}
