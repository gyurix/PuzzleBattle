package org.puzzlebattle.client.games.fourinarow;


import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import org.puzzlebattle.client.games.Game;
import org.puzzlebattle.client.games.bouncer.BouncerGameSettings;

import java.util.ArrayList;


public class FourInARowGame extends Game {

  private FourInARowGameSettings settings;
  private FourInARowPlayer you, enemy;
  private ArrayList<FourInARowPlayer> players = new ArrayList<FourInARowPlayer>();
  private int fillingColumns[];
  public FourInARowGame(Object serverConnection, FourInARowGameSettings settings) {

    super(serverConnection);
    this.settings= settings;

    you = new FourInARowPlayer(this,true);
    enemy = new FourInARowPlayer(this,false);
    players.add(you);
    players.add(enemy);

    createFillingColumns();
  }


  private void createFillingColumns() {
      fillingColumns = new int[settings.getMaxColumns()+1];
      for(int i=0;i<settings.getMaxColumns()+1;i++)
        fillingColumns[i]=0;
  }


  public void onKeyEvent(KeyCode key, boolean pressed) {

    if (key.compareTo(settings.getDigit0())== 1) {
      if(conditionsToMove(1))
        applyMove(1);
    }
    else if (key.compareTo(settings.getDigit1())==1) {
      if(conditionsToMove(2))
        applyMove(2);
    }
    else if (key.compareTo(settings.getDigit2())==1) {
      if(conditionsToMove(3))
        applyMove(3);
    }
    else if (key.compareTo(settings.getDigit3())==1) {
      if(conditionsToMove(4))
        applyMove(4);
    }
    else if (key.compareTo(settings.getDigit4())==1) {
      if(conditionsToMove(5))
        applyMove(5);
    }
    else if (key.compareTo(settings.getDigit5())==1) {
      if(conditionsToMove(6))
        applyMove(6);
    }
    else if (key.compareTo(settings.getDigit6())==1) {
      if(conditionsToMove(7))
        if(settings.getMaxColumns() >= 7)
            applyMove(7);
    }
    else if (key.compareTo(settings.getDigit7())==1) {
      if(conditionsToMove(8))
        if(settings.getMaxColumns() >= 8)
            applyMove(8);
    }
    else if (key.compareTo(settings.getDigit8())==1) {
      if(conditionsToMove(9))
        if(settings.getMaxColumns() >= 9)
            applyMove(9);
    }

  }


  private boolean conditionsToMove(int column)
  {
    if(settings.getMaxColumns() >= column && fillingColumns[column]<=settings.getMaxRows())
        return true;
    else
      return false;
  }

  private void applyMove(int numberOfSelectedColumn) {

    System.out.println("applying move:");
    fillingColumns[numberOfSelectedColumn]++;
    FourInARowPlayer playerOnTheMove = getWhoIsOnTheMove();

    System.out.println(numberOfSelectedColumn);

    switchPlayer(playerOnTheMove);
  }

  private void switchPlayer(FourInARowPlayer playerOnTheMove)
  {
    int number;
    int numberOfPlayers;

    playerOnTheMove.takeMove();
    number= playerOnTheMove.getPlayingNumber();
    System.out.println("playing player: "+number);
    numberOfPlayers = FourInARowPlayer.getNumberOfPlayers();
    if(number==numberOfPlayers)
        number=1;
    else
        number++;

    players.get(number-1).setMove();

  }

  private FourInARowPlayer getWhoIsOnTheMove() {
    if(you.isOnTheMove())
        return you;
    else if(enemy.isOnTheMove())
        return enemy;
    else
        return enemy;
  }
}
