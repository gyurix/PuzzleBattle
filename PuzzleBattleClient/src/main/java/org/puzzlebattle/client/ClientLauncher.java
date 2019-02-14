package org.puzzlebattle.client;

import javafx.application.Application;
import javafx.stage.Stage;
import org.puzzlebattle.client.games.ballbouncer.BallBouncerGame;
import org.puzzlebattle.client.screen.games.BallBouncerScreen;

public class ClientLauncher extends Application {
  public static void main(String[] args) {
    Application.launch();
  }

  public void start(Stage stage) {
    new BallBouncerScreen(stage, new BallBouncerGame()).show();
  }
}
