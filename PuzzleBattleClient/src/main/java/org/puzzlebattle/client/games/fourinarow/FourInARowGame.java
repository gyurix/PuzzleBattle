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

  public FourInARowGameSettings getFourInARowGameSettings() {
    return settings;
  }

  private void createFillingColumns() {
      fillingColumns = new int[settings.getMaxColumns()+1];
      for(int i=0;i<settings.getMaxColumns()+1;i++)
        fillingColumns[i]=0;
  }

  public FourInARowPoint questionForMove(KeyCode key) {

    for(int i=1;i<10;i++)
      if (key == settings.getDigit(i) || key == settings.getNumpad(i)) {
        if(conditionsToMove(i))
            return applyMove(i);
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
