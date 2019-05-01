package org.puzzlebattle.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.puzzlebattle.core.utils.IOUtils;
import org.puzzlebattle.server.entity.Server;

import java.io.FileReader;

public class ServerLauncher {
  private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

  public static void main(String[] args) throws Throwable {
    System.out.println("Saving resources...");
    IOUtils.saveResources("server-config.json");
    System.out.println("Loading configuration...");
    ServerConfig config = GSON.fromJson(new FileReader("server-config.json"), ServerConfig.class);
    Server server = new Server(config.getAddress());
    System.out.println("Launched server");
  }
}
