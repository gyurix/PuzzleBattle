package org.puzzlebattle.client;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.application.Application;
import javafx.stage.Stage;
import lombok.Getter;
import org.puzzlebattle.client.config.ClientConfig;
import org.puzzlebattle.client.protocol.Client;
import org.puzzlebattle.client.screen.LanguageSelector;
import org.puzzlebattle.client.screen.LoginScreen;
import org.puzzlebattle.core.utils.IOUtils;
import org.puzzlebattle.core.utils.LangFile;
import org.puzzlebattle.core.utils.Logging;

import java.io.FileReader;

/**
 * Write a description of class SkladPonuka here.
 *
 * @author (Juraj Barath, Jakub Perdek)
 * @version (1.0)
 */
public class ClientLauncher extends Application {
  private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
  @Getter
  private static ClientConfig config;

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
      // stage.getIcons().add(new Image("pictures/icon.png"));
      IOUtils.saveResources("config.json");
      config = gson.fromJson(new FileReader("config.json"), ClientConfig.class);
      LanguageSelector languageSelector = new LanguageSelector(stage);
      LangFile.lang.msg("started", "name", "Puzzle Battle Client", "version", "1.0");
      new LoginScreen(stage, languageSelector, new Client(config.getServer())).show();
    } catch (Throwable e) {
      Logging.logSevere("Failed to start client", "error", e);
    }
  }
}
