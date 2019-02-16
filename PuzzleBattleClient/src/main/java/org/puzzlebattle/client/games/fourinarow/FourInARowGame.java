package org.puzzlebattle.client.games.fourinarow;


import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
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

    you = new FourInARowPlayer(this,true,Color.RED);
    enemy = new FourInARowPlayer(this,false, Color.BLUE);
    players.add(you);
    players.add(enemy);

    createFillingColumns();
  }

  private void createFillingColumns() {
      fillingColumns = new int[settings.getMaxColumns()+1];
      for(int i=0;i<settings.getMaxColumns()+1;i++)
        fillingColumns[i]=0;
  }

  public FourInARowPoint questionForMove(KeyCode key, boolean pressed) {

    if (key.compareTo(settings.getDigit0())== 1) {
      if(conditionsToMove(1))
        return applyMove(1);
    }
    else if (key.compareTo(settings.getDigit1())==1) {
      if(conditionsToMove(2))
        return applyMove(2);
    }
    else if (key.compareTo(settings.getDigit2())==1) {
      if(conditionsToMove(3))
        return applyMove(3);
    }
    else if (key.compareTo(settings.getDigit3())==1) {
      if(conditionsToMove(4))
        return applyMove(4);
    }
    else if (key.compareTo(settings.getDigit4())==1) {
      if(conditionsToMove(5))
        return applyMove(5);
    }
    else if (key.compareTo(settings.getDigit5())==1) {
      if(conditionsToMove(6))
        return applyMove(6);
    }
    else if (key.compareTo(settings.getDigit6())==1) {
      if(conditionsToMove(7))
        if(settings.getMaxColumns() >= 7)
          return applyMove(7);
    }
    else if (key.compareTo(settings.getDigit7())==1) {
      if(conditionsToMove(8))
        if(settings.getMaxColumns() >= 8)
          return applyMove(8);
    }
    else if (key.compareTo(settings.getDigit8())==1) {
      if(conditionsToMove(9))
        if(settings.getMaxColumns() >= 9)
          return applyMove(9);
    }
    return null;
  }

  private boolean conditionsToMove(int column)
  {
    if(settings.getMaxColumns() >= column && fillingColumns[column]<=settings.getMaxRows())
        return true;
    else
      return false;
  }

  private FourInARowPoint applyMove(int numberOfSelectedColumn) {

    fillingColumns[numberOfSelectedColumn]++;
    FourInARowPlayer playerOnTheMove = getWhoIsOnTheMove();

    switchPlayer(playerOnTheMove);
    return new FourInARowPoint(numberOfSelectedColumn, playerOnTheMove.getColorOfPlayersCoin());
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
