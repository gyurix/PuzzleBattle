package org.puzzlebattle.client.games.fourinarow;

import javafx.scene.paint.Color;

public class FourInARowPlayer {

  private FourInARowGame game;
  private boolean move;
  private static int counterOfPlayers = 0;
  private int playingNumber;
  private Color colorForCoins;


  public FourInARowPlayer(FourInARowGame game,boolean firstStep,Color colorForCoins ) {
    this.game =game;
    this.move =firstStep;
    this.colorForCoins = colorForCoins;
    counterOfPlayers++;
    playingNumber=counterOfPlayers;
  }

  public boolean isOnTheMove() { return move; }

  public void setMove() { this.move= true; }

  public void takeMove() {this.move= false; }

  public int getPlayingNumber() { return playingNumber; }

  public static int getNumberOfPlayers() {return counterOfPlayers;}

  public Color getColorOfPlayersCoin(){ return colorForCoins; }
}
