package org.puzzlebattle.client;


import javafx.application.Application;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.puzzlebattle.client.databaseTables.UserPuzzleBattle;
import org.puzzlebattle.client.screen.LoginScreen;
import org.puzzlebattle.core.utils.LangFile;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


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
    //new MainScreen(stage,new SettingsForScreens()).show();
    //loginScreen= new LoginScreen(stage);
    //new BallBouncerScreen(stage, new BouncerGame(null, new BouncerGameSettings())).show();
    //new FourInARowScreen(stage, new FourInARowGame(null, new FourInARowGameSettings())).show();

  }
}
