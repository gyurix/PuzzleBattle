package org.puzzlebattle.client.protocol.handlers;

import io.netty.channel.Channel;
import org.puzzlebattle.client.protocol.Client;
import org.puzzlebattle.client.protocol.packets.in.ServerInBestPlayers;
import org.puzzlebattle.client.protocol.packets.in.ServerInChangeProfile;
import org.puzzlebattle.client.screen.BestPlayersScreen;
import org.puzzlebattle.client.utils.ThreadUtils;

public class ServerConnectedHandler extends ServerHandler {
  public ServerConnectedHandler(Channel channel, Client client) {
    super(channel, client);
  }

  @Override
  public void handle(ServerInChangeProfile packet) {
    client.setUser(packet.getProfile());
  }

  @Override
  public void handle(ServerInBestPlayers packet) {
    ThreadUtils.ui(() -> {
      BestPlayersScreen.getInstance().getFourInARowGameTable().setItems(packet.getUserGameAttributes());
    });
  }
}
