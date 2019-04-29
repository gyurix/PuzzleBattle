package org.puzzlebattle.server.db;

import org.puzzlebattle.server.db.entity.GameTable;
import org.puzzlebattle.server.db.entity.GameType;
import org.puzzlebattle.server.db.entity.User;
import org.puzzlebattle.server.db.entity.gameSettings.BallBouncerGameSettings;
import org.puzzlebattle.server.db.entity.gameSettings.FourInARowGameSettings;
import org.puzzlebattle.server.db.entity.gameSettings.GameSettings;

public class GameManager {

  public GameManager() {
    GameType.addGamesToDBIfTheyAreNot();
  }

  private void addBallBouncerGameToDatabase(User userPuzzleBattle, boolean test) {
    int gameType = GameType.getFourInARowGame().getId();
    long gameSettingId = GameSettings.insertGameSettingsToDBIfTheyAreNotExistAndGetId(new BallBouncerGameSettings());
    GameSettings bouncerGameSettings = new BallBouncerGameSettings();
    ((BallBouncerGameSettings) bouncerGameSettings).setGameType(gameType);
    GameTable gameTable = GameTable.prepareGameTable(userPuzzleBattle, test, gameType, bouncerGameSettings);
  }

  private GameTable addFourInARowGameToDatabase(User userPuzzleBattle, boolean test) {
    int gameType = GameType.getFourInARowGame().getId();
    long gameSettingId = GameSettings.insertGameSettingsToDBIfTheyAreNotExistAndGetId(new FourInARowGameSettings());
    GameSettings fourInARowGameSetting = new FourInARowGameSettings();
    ((FourInARowGameSettings) fourInARowGameSetting).setGameType(gameType);
    GameTable gameTable = GameTable.prepareGameTable(userPuzzleBattle, test, gameType, fourInARowGameSetting);
    return gameTable;
  }
}
