package org.puzzlebattle.server.db;

import org.puzzlebattle.server.db.entity.GameType;

public class GameManager {

  public GameManager() {
    GameType.addGamesToDBIfTheyAreNot();
  }

  /*private void addBallBouncerGameToDatabase(PBUser userPuzzleBattle, boolean test) {
    int gameType = GameType.getFourInARowGame().getId();
    long gameSettingId = GameSettings.insertGameSettingsToDBIfTheyAreNotExistAndGetId(new BallBouncerGameSettings());
    GameSettings bouncerGameSettings = new BallBouncerGameSettings();
    ((BallBouncerGameSettings) bouncerGameSettings).setGameType(gameType);
    GameTable gameTable = GameTable.prepareGameTable(userPuzzleBattle, test, gameType, bouncerGameSettings);
  }

  private GameTable addFourInARowGameToDatabase(PBUser userPuzzleBattle, boolean test) {
    int gameType = GameType.getFourInARowGame().getId();
    long gameSettingId = GameSettings.insertGameSettingsToDBIfTheyAreNotExistAndGetId(new FourInARowGameSettings());
    GameSettings fourInARowGameSetting = new FourInARowGameSettings();
    ((FourInARowGameSettings) fourInARowGameSetting).setGameType(gameType);
    GameTable gameTable = GameTable.prepareGameTable(userPuzzleBattle, test, gameType, fourInARowGameSetting);
    return gameTable;
  }*/
}
