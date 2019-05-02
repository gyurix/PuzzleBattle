package org.puzzlebattle.client.protocol.handlers;

import io.netty.channel.Channel;
import javafx.stage.Stage;
import org.puzzlebattle.client.config.ClientConfig;
import org.puzzlebattle.client.config.ConfigManager;
import org.puzzlebattle.client.games.EndDialog;
import org.puzzlebattle.client.games.bouncer.BallBouncerScreen;
import org.puzzlebattle.client.games.bouncer.BouncerGame;
import org.puzzlebattle.client.games.fourinarow.FourInARowGame;
import org.puzzlebattle.client.games.fourinarow.FourInARowScreen;
import org.puzzlebattle.client.protocol.Client;
import org.puzzlebattle.client.protocol.packets.in.*;
import org.puzzlebattle.client.screen.BestPlayersScreen;
import org.puzzlebattle.client.screen.LoginScreen;
import org.puzzlebattle.client.screen.PlayerProfileScreen;
import org.puzzlebattle.client.utils.ThreadUtils;
import org.puzzlebattle.core.gamesettings.BallBouncerSettings;
import org.puzzlebattle.core.gamesettings.FourInARowSettings;

import static org.puzzlebattle.core.utils.IOUtils.GSON;

public class ServerConnectedHandler extends ServerHandler {
  public ServerConnectedHandler(Channel channel, Client client) {
    super(channel, client);
  }

  @Override
  public void handle(ServerInChangeProfile packet) {
    client.setUser(packet.getProfile());
    PlayerProfileScreen playerProfileScreen = new PlayerProfileScreen(new Stage(), client);
    playerProfileScreen.updateInformationPlayerProfileScreen(client.getUser());
    playerProfileScreen.show();
  }

  @Override
  public void handle(ServerInBestPlayers packet) {
    ThreadUtils.ui(() -> BestPlayersScreen.getInstance().getFourInARowGameTable()
            .setItems(packet.getUserGameAttributes()));
  }

  @Override
  public void handle(ServerInEndGame packet) {
    ThreadUtils.ui(() -> {
      new EndDialog(LoginScreen.getInstance().getStage(), client, client.getGame().getType(), packet.getWinner()).show();
      client.setGame(null);
    });
  }

  @Override
  public void handle(ServerInUpdateGame packet) {
    ThreadUtils.ui(() -> client.getGame().updateData(packet.getData()));
  }

  @Override
  public void handle(ServerInStartGame packet) {
    ThreadUtils.ui(() -> {
      ClientConfig config = ConfigManager.getInstance().getConfig();
      switch (packet.getType()) {
        case FOUR_IN_A_ROW: {
          FourInARowSettings settings = GSON.fromJson(packet.getSettings(), FourInARowSettings.class);
          new FourInARowScreen(LoginScreen.getInstance().getStage(),
                  new FourInARowGame(packet.isInitializer(),
                          config.getFourInARowTemplates().get(settings.getTemplate()),
                          settings, client), client).show();
          break;
        }
        case BOUNCER: {
          BallBouncerSettings settings = GSON.fromJson(packet.getSettings(),
                  BallBouncerSettings.class);
          new BallBouncerScreen(LoginScreen.getInstance().getStage(),
                  new BouncerGame(config.getBallBouncerTemplates().get(settings.getTemplate()),
                          settings, client), client).show();
          break;
        }
      }
    });
  }

}
