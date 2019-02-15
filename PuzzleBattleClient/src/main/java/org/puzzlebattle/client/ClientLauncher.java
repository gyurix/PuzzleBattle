package org.puzzlebattle.client;

import javafx.application.Application;
import javafx.stage.Stage;
import org.puzzlebattle.client.games.bouncer.BallBouncerScreen;
import org.puzzlebattle.client.games.bouncer.BouncerGame;
import org.puzzlebattle.client.games.bouncer.BouncerGameSettings;
import org.puzzlebattle.core.utils.LangFile;

public class ClientLauncher extends Application {
  public static LangFile lang;

  public static void main(String[] args) {
    Application.launch();
  }

  public void start(Stage stage) {
    LangFile english = new LangFile("en");
    lang = new LangFile(english, "sk");
    lang.msg("started", "name", "Puzzle Battle Client", "version", "1.0");
    new BallBouncerScreen(stage, new BouncerGame(null, new BouncerGameSettings())).show();
  }
}
