package org.puzzlebattle.client.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;
import org.puzzlebattle.core.utils.IOUtils;
import org.puzzlebattle.core.utils.Logging;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ConfigManager {
  private static final String FILENAME = "client-config.json";
  private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
  @Getter
  private static final ConfigManager instance = new ConfigManager();
  @Getter
  private ClientConfig config;

  public void load() {
    Logging.logInfo("Loading config...");
    IOUtils.saveResources(FILENAME);
    try (FileReader fr = new FileReader(FILENAME)) {
      config = GSON.fromJson(fr, ClientConfig.class);
    } catch (IOException e) {
      Logging.logSevere("Error on loading config", "error", e);
    }
  }

  public void save() {
    Logging.logInfo("Saving config...");
    try (FileWriter fw = new FileWriter(FILENAME)) {
      GSON.toJson(config, fw);
    } catch (IOException e) {
      Logging.logSevere("Error on saving config", "error", e);
    }
  }
}
