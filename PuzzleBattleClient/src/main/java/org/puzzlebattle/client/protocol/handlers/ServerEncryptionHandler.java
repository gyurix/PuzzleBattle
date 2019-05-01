package org.puzzlebattle.client.protocol.handlers;

import io.netty.channel.Channel;
import org.puzzlebattle.client.protocol.Server;
import org.puzzlebattle.client.protocol.packets.in.ServerInLoginResult;

public class ServerEncryptionHandler extends ServerHandler {
  public ServerEncryptionHandler(Channel channel, Server server) {
    super(channel, server);
  }

  @Override
  public void handle(ServerInLoginResult packet) {

  }
}
