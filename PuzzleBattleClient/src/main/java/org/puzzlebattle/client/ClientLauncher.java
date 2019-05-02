package org.puzzlebattle.client;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.puzzlebattle.client.config.ConfigManager;
import org.puzzlebattle.client.protocol.Client;
import org.puzzlebattle.client.screen.LoginScreen;
import org.puzzlebattle.core.utils.LangFile;
import org.puzzlebattle.core.utils.Logging;

/**
 * Write a description of class SkladPonuka here.
 *
 * @author (Juraj Barath, Jakub Perdek)
 * @version (1.0)
 */
public class ClientLauncher extends Application {
  private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

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
   * This is server program launcher. Here server application strats.
   *
   * @param stage the first stage which will be displayed
   */
  public void start(Stage stage) {
    try {
      stage.getIcons().add(new Image("pictures/icon.png"));
      ConfigManager.getInstance().load();
      new LoginScreen(stage, new Client(ConfigManager.getInstance().getConfig().getServer())).show();
      LangFile.lang.msg("started", "name", "Puzzle Battle Client", "version", "1.0");
    } catch (Throwable e) {
      Logging.logSevere("Failed to start client", "error", e);
    }
  }
}
