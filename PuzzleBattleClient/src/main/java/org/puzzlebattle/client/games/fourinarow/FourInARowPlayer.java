package org.puzzlebattle.client.games.fourinarow;

import javafx.scene.paint.Color;


/**
 * Four in a row players are defined here. Not only two players can be added into game, but
 * game is designed specially for two players.
 *
 * @author (Jakub Perdek)
 * @version (1.0)
 */

public class FourInARowPlayer {

  private static int counterOfPlayers = 0;
  private Color colorForCoins;
  private FourInARowGame game;
  private boolean move;
  private int playingNumber;


  /**
   * Constructor for objects of class SkladPonuka
   */
  public FourInARowPlayer(FourInARowGame game, boolean firstStep, Color colorForCoins) {
    this.game = game;
    this.move = firstStep;
    this.colorForCoins = colorForCoins;
    counterOfPlayers++;
    playingNumber = counterOfPlayers;
  }

  /**
   * Method which returns specific number of players in the game.
   *
   * @return number of all players in the game
   */
  public static int getNumberOfPlayers() {
    return counterOfPlayers;
  }

  /**
   * Method which nulls number of players in the game.
   */
  public static void nullNumberOfPlayers() {
    counterOfPlayers = 0;
  }

  /**
   * Method which returns representation of player, color of player.
   *
   * @return color of player, representation of player, which should be used
   */
  public Color getColorOfPlayersCoin() {
    return colorForCoins;
  }

  /**
   * Method whic returns number, which represents when player is on the move,
   * how many players were on the move before him.
   *
   * @return playing number of the player, number of players who were on the move before him
   */
  public int getPlayingNumber() {
    return playingNumber;
  }

  /**
   * Method which specifies if concrete player is on the move, have rights to play at the moment
   *
   * @return true if player is on the move, otherwise false
   */
  public boolean isOnTheMove() {
    return move;
  }

  /**
   * Method which modifies state, that player is on the move. It should be applied only before
   * player should be on the move, otherwise never. Player is now on the move, have rights to play at the moment.
   */
  public void setMove() {
    this.move = true;
  }

  /**
   * Method which modifies state, that player is on the move. It should be applied only after
   * player applies his move, otherwise never. Player is now not on the move, have rights to play at the moment.
   */
  public void takeMove() {
    this.move = false;
  }
}
