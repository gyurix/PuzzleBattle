package org.puzzlebattle.server.protocol.handlers;

import io.netty.channel.Channel;
import org.puzzlebattle.server.entity.Client;
import org.puzzlebattle.server.protocol.packets.in.ClientInKeepAlive;
import org.puzzlebattle.server.protocol.packets.out.ClientOutKeepAlive;

public class ClientConnectedHandler extends ClientHandler {
  public ClientConnectedHandler(Channel channel, Client client) {
    super(channel, client);
  }

  @Override
  public void handle(ClientInKeepAlive packet) {
    sendPacket(new ClientOutKeepAlive(packet.getId()));
  }
}
