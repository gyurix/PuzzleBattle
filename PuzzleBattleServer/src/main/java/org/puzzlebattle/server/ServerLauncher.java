package org.puzzlebattle.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.puzzlebattle.server.db.DB;
import org.puzzlebattle.server.entity.Server;
import org.puzzlebattle.server.manager.ConfigManager;

public class ServerLauncher {
  private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

  public static void main(String[] args) throws Throwable {
    DB.INSTANCE.init();
    ConfigManager.getInstance().load();
    Server server = new Server(ConfigManager.getInstance().getConfig().getAddress());
  }
}
