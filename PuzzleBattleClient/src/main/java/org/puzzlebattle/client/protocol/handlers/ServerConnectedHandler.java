package org.puzzlebattle.client.protocol.handlers;

import io.netty.channel.Channel;
import org.puzzlebattle.client.protocol.Server;

public class ServerConnectedHandler extends ServerHandler {
  public ServerConnectedHandler(Channel channel, Server server) {
    super(channel, server);
  }

}
