package org.puzzlebattle.client;

import javafx.application.Application;
import javafx.stage.Stage;
import org.puzzlebattle.client.games.fourinarow.FourInARowGame;
import org.puzzlebattle.client.games.fourinarow.FourInARowGameSettings;
import org.puzzlebattle.client.games.fourinarow.FourInARowScreen;
import org.puzzlebattle.client.screen.LoginScreen;
import org.puzzlebattle.client.screen.MainScreen;
import org.puzzlebattle.client.screen.SettingsForScreens;
import org.puzzlebattle.core.utils.LangFile;

/**
 * Write a description of class SkladPonuka here.
 *
 * @author (Juraj Barath, Jakub Perdek)
 * @version (1.0)
 */

public class ClientLauncher extends Application {
  public static LangFile lang;
  private LoginScreen loginScreen;

  /**
   * Method where application starts
   *
   * @param args arguments, which can be set before start of program
   */

  public static void main(String[] args) {
    Application.launch();
  }


  /**
   * Basic settings as language, name of the game, version will be applied.
   * This is client program launcher. Here client application strats.
   *
   * @param stage the first stage which will be displayed
   */

  public void start(Stage stage) {
    LangFile english = new LangFile("en");
    lang = new LangFile(english, "sk");
    lang.msg("started", "name", "Puzzle Battle Client", "version", "1.0");

    new MainScreen(stage,new SettingsForScreens()).show();
    //loginScreen= new LoginScreen(stage);
    //new BallBouncerScreen(stage, new BouncerGame(null, new BouncerGameSettings())).show();
    //new FourInARowScreen(stage, new FourInARowGame(null, new FourInARowGameSettings())).show();

  }
}
