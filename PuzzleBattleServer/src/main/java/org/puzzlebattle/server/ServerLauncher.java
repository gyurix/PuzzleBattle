package org.puzzlebattle.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.puzzlebattle.core.utils.IOUtils;
import org.puzzlebattle.core.utils.Logging;
import org.puzzlebattle.server.db.DB;
import org.puzzlebattle.server.entity.Server;

import java.io.FileReader;

public class ServerLauncher {
  private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

  public static void main(String[] args) throws Throwable {
    Logging.logInfo("Loading config...");
    IOUtils.saveResources("server-config.json");
    ServerConfig config = GSON.fromJson(new FileReader("server-config.json"), ServerConfig.class);
    Logging.logInfo("Connecting to database...");
    DB.INSTANCE.init();
    Server server = new Server(config.getAddress());
    Logging.logInfo("Launched server");
  }
}
