package org.puzzlebattle.client.protocol.handlers;

import io.netty.channel.Channel;
import org.puzzlebattle.client.protocol.Client;
import org.puzzlebattle.client.protocol.packets.in.ServerInBestPlayers;
import org.puzzlebattle.client.protocol.packets.in.ServerInLoginResult;
import org.puzzlebattle.client.screen.BestPlayersScreen;
import org.puzzlebattle.client.screen.LoginScreen;
import org.puzzlebattle.client.screen.MainScreen;
import org.puzzlebattle.client.screen.SettingsForScreens;
import org.puzzlebattle.client.utils.ThreadUtils;

import static javafx.scene.control.Alert.AlertType.ERROR;

public class ServerLoginHandler extends ServerHandler {
  public ServerLoginHandler(Channel channel, Client client) {
    super(channel, client);
  }

  @Override
  public void handle(ServerInLoginResult packet) {
    if (packet.isResult()) {
      ThreadUtils.ui(() -> LoginScreen.getInstance().showAlert(ERROR, "login.incorrect"));
    } else {
      ThreadUtils.ui(() -> {
        new MainScreen(LoginScreen.getInstance().getStage(), new SettingsForScreens(), client).show();
      });
      ServerConnectedHandler handler = new ServerConnectedHandler(channel, client);
      client.getConnection().setHandler(handler);
      client.setUser(LoginScreen.getInstance().getUser());
      channel.pipeline().replace("handler", "handler", handler);
    }
  }

  public void handle(ServerInBestPlayers packet) {
    BestPlayersScreen.getInstance().getFourInARowGameTable().setItems(packet.getUserGameAttributes());
  }

  //public void handle(ServerIn)
}
