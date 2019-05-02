package org.puzzlebattle.client.protocol.handlers;

import io.netty.channel.Channel;
import org.puzzlebattle.client.protocol.Client;
import org.puzzlebattle.client.protocol.packets.in.ServerInLoginResult;
import org.puzzlebattle.client.screen.LoginScreen;
import org.puzzlebattle.client.screen.MainScreen;
import org.puzzlebattle.client.utils.ThreadUtils;

import static javafx.scene.control.Alert.AlertType.ERROR;

public class ServerLoginHandler extends ServerHandler {
  public ServerLoginHandler(Channel channel, Client client) {
    super(channel, client);
  }

  @Override
  public void handle(ServerInLoginResult packet) {
    if (packet.isResult()) {
      ThreadUtils.ui(() -> new MainScreen(client.getOpenScreen().getStage(), client).show());
      ServerConnectedHandler handler = new ServerConnectedHandler(channel, client);
      client.getConnection().setHandler(handler);
      channel.pipeline().replace("handler", "handler", handler);
      return;
    }
    ThreadUtils.ui(() -> {
      if (client.getOpenScreen() instanceof LoginScreen)
        client.getOpenScreen().showAlert(ERROR, "login.incorrect");
      else
        client.getOpenScreen().showAlert(ERROR, "register.registrationFailed");
    });
  }


}
