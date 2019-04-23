package org.puzzlebattle.client.protocol.handlers;

import io.netty.channel.Channel;
import org.puzzlebattle.client.protocol.Server;

public class ServerLoginHandler extends ServerHandler {
  public ServerLoginHandler(Channel channel, Server server) {
    super(channel, server);
  }

}
