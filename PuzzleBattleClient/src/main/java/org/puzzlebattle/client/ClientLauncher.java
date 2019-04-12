package org.puzzlebattle.client;


import javafx.application.Application;
import javafx.stage.Stage;
import org.puzzlebattle.client.screen.LoginScreen;
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
   * The applications main entry point
   *
   * @param args - Starting arguments of the program, currently unused
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

    new LoginScreen(stage).show();
  }
}
