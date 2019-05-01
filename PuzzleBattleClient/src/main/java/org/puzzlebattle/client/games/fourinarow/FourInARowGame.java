package org.puzzlebattle.client.games.fourinarow;


import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import lombok.Getter;
import org.puzzlebattle.client.games.Game;
import org.puzzlebattle.client.protocol.Client;
import org.puzzlebattle.client.protocol.packets.out.ServerOutUpdateGame;
import org.puzzlebattle.core.entity.GameType;
import org.puzzlebattle.core.utils.Logging;

import java.util.ArrayList;


/**
 * @author (Jakub Perdek)
 * @version (1.0)
 */
public class FourInARowGame extends Game {
  private int[] fillingColumns;
  private FourInARowEntity fourInARowEntity;
  private ArrayList<FourInARowPlayer> players = new ArrayList<FourInARowPlayer>();
  @Getter
  private FourInARowGameSettings settings;
  @Getter
  private FourInARowPlayer you, enemy;


  /**
   * Four in a row game is created, players with specific colors are added, setting are stored.
   */
  public FourInARowGame(boolean shouldStart, FourInARowGameSettings settings, Client client) {
    super(client);
    this.settings = settings;
    FourInARowPlayer.nullNumberOfPlayers();
    you = new FourInARowPlayer(this, shouldStart, Color.RED);
    enemy = new FourInARowPlayer(this, !shouldStart, Color.BLUE);
    players.add(you);
    players.add(enemy);

    fourInARowEntity = new FourInARowEntity(settings.getMaxRows(), settings.getMaxColumns(), settings.getMaxInTheRow());
    createFillingColumns();
  }

  /**
   * Steps which are necessary if move was accepted. Counter of coins in specific column must be increased.
   * Player is found, his move is taken and set to next player. Information about falling coin are stored into FourInARowPoint object.
   *
   * @param col - The selected column by player
   * @return information about column and coin, which will be added into it
   */
  private void applyMove(int col) {
    Logging.logInfo("Apply move", "column", col);
    if (!conditionsToMove(col)) {
      Logging.logWarning("Conditions to move are not met");
      return;
    }
    fillingColumns[col]++;
    FourInARowPlayer playerOnTheMove = getWhoIsOnTheMove();
    if (playerOnTheMove == you)
      client.getConnection().getHandler().sendPacket(new ServerOutUpdateGame(new int[]{col}));

    switchPlayer(playerOnTheMove);
    fourInARowEntity.setPlayerNumberToAMap(playerOnTheMove, fillingColumns[col], col);
    FourInARowPoint point = new FourInARowPoint(col, playerOnTheMove.getColorOfPlayersCoin(),
            fillingColumns[col] - 1, fourInARowEntity, playerOnTheMove);
    FourInARowScreen screen = FourInARowScreen.getInstance();
    Coin coin = createCoin(point, screen);
    startCoinFall(coin, screen.mapSize.getY() - (point.getCoinsInColumnBelow()) *
            (screen.getPane().getThicknessOfRows() +
                    screen.getPane().getDistanceOfColumns()) +
            screen.getPane().getThicknessOfRows(), screen);
  }

  /**
   * Analyse conditions to move. Maximum number of columns can't be higher then number of available columns and
   * counter of coins shouldn't be higher then capacity of every column.
   *
   * @param column selected column by player, where coin will fall
   * @return true if move can be accepted, or false if not
   */
  private boolean conditionsToMove(int column) {
    if (settings.getMaxColumns() >= column && fillingColumns[column] < settings.getMaxRows()) {
      Logging.logInfo(fillingColumns[column] + " <> " + settings.getMaxRows());
      return true;
    } else
      return false;
  }

  /**
   * Coin is created here. First necessary values are obtained from fourInARowPoint to specify coin.
   * After creation of coin is added to pane.
   *
   * @param point            necessary information for creating coin
   * @param fourInARowScreen
   * @return created coin
   */
  public Coin createCoin(FourInARowPoint point, FourInARowScreen fourInARowScreen) {
    int column = point.getNumberOfColumn();
    double x = fourInARowScreen.getPane().getColumnX(column);
    Coin newCoin = new Coin(x, fourInARowScreen.coinRadius, fourInARowScreen.coinRadius, point.getColorOfPlayerForCoin());
    fourInARowScreen.coins.add(newCoin);
    fourInARowScreen.getPane().getChildren().add(newCoin);
    return newCoin;
  }

  /**
   * For every column, counter of coins inserted is set on null - zero
   * Counter is created here.
   */
  private void createFillingColumns() {
    fillingColumns = new int[settings.getMaxColumns() + 1];
    for (int i = 0; i < settings.getMaxColumns() + 1; i++)
      fillingColumns[i] = 0;
  }

  /**
   * Method where person who is on the move is selected and returned.
   *
   * @return player on the move
   */
  private FourInARowPlayer getWhoIsOnTheMove() {
    if (you.isOnTheMove())
      return you;
    else if (enemy.isOnTheMove())
      return enemy;
    else
      return enemy;
  }

  /**
   * Analyse if correct key was pressed on the keyboard.
   *
   * @param key key which was pressed
   * @return information about selected column, color of player on the move and other necessary information
   */
  public void questionForMove(KeyCode key) {
    if (getWhoIsOnTheMove() != you) {
      Logging.logWarning("You are not on the move!");
      return;
    }
    for (int i = 1; i < 10; i++)
      if (key == FourInARowGameSettings.getDigit(i) || key == FourInARowGameSettings.getNumpad(i)) {
        applyMove(i);
        return;
      }
    return;
  }

  @Override
  public GameType getType() {
    return GameType.FOUR_IN_A_ROW;
  }

  /**
   * Rendering of coin fall is provided here. At first all components are repainted and set to a front.
   * Then in background coin fall is simulated.
   *
   * @param coin             - The coin which should be new coin, which will be falling from certain position
   * @param to
   * @param fourInARowScreen
   */
  public void startCoinFall(Coin coin, double to, FourInARowScreen fourInARowScreen) {
    new CoinFall(fourInARowScreen, coin, to, 5);
  }

  /**
   * Method where player on the move lost his move and another player in sequence obtains this move
   *
   * @param playerOnTheMove player who is on the move
   */
  private void switchPlayer(FourInARowPlayer playerOnTheMove) {
    int number;
    int numberOfPlayers;

    playerOnTheMove.takeMove();
    number = playerOnTheMove.getPlayingNumber();
    Logging.logInfo("player on the move: " + number);
    numberOfPlayers = FourInARowPlayer.getNumberOfPlayers();
    if (number == numberOfPlayers)
      number = 1;
    else
      number++;

    players.get(number - 1).setMove();
  }

  /**
   * Finds if is draw, users can't do any move
   *
   * @return true if it si draw, otherwise false
   */
  public boolean isDraw() {
    for (int column = 1; column <= settings.getMaxColumns(); column = column + 1) {
      if (fillingColumns[column] < settings.getMaxRows()) {
        return false;
      }
    }
    return true;
  }

  @Override
  public void updateData(int[] data) {
    applyMove(data[0]);
  }
}
