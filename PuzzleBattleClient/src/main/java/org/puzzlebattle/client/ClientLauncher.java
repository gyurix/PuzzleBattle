package org.puzzlebattle.client;

import javafx.application.Application;
import javafx.stage.Stage;

import org.puzzlebattle.client.games.fourinarow.FourInARowGame;
import org.puzzlebattle.client.games.fourinarow.FourInARowGameSettings;
import org.puzzlebattle.client.games.fourinarow.FourInARowScreen;
import org.puzzlebattle.core.utils.LangFile;
import org.puzzlebattle.client.games.bouncer.BallBouncerScreen;
import org.puzzlebattle.client.games.bouncer.BouncerGame;
import org.puzzlebattle.client.games.bouncer.BouncerGameSettings;

public class ClientLauncher extends Application {
  public static LangFile lang;

  public static void main(String[] args) {
    Application.launch();
  }

  public void start(Stage stage) {
    LangFile english = new LangFile("en");
    lang = new LangFile(english, "sk");
    lang.msg("started", "name", "Puzzle Battle Client", "version", "1.0");
    //new BallBouncerScreen(stage, new BouncerGame(null, new BouncerGameSettings())).show();
    new FourInARowScreen(stage, new FourInARowGame(null, new FourInARowGameSettings())).show();

  }
}
