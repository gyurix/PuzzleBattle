package org.puzzlebattle.client.games.fourinarow;

public class FourInARowPlayer {

  private FourInARowGame game;
  private boolean move;
  private static int counterOfPlayers = 0;
  private int playingNumber;

  public FourInARowPlayer(FourInARowGame game,boolean firstStep ) {
    this.game =game;
    this.move =firstStep;
    counterOfPlayers++;
    playingNumber=counterOfPlayers;
  }

  public boolean isOnTheMove() { return move; }

  public void setMove() { this.move= true; }

  public void takeMove() {this.move= false; }

  public int getPlayingNumber() { return playingNumber; }

  public static int getNumberOfPlayers() {return counterOfPlayers;}

}
