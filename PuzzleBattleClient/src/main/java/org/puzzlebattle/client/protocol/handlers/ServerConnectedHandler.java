package org.puzzlebattle.client.protocol.handlers;

import io.netty.channel.Channel;
import org.puzzlebattle.client.protocol.Client;

public class ServerConnectedHandler extends ServerHandler {
  public ServerConnectedHandler(Channel channel, Client client) {
    super(channel, client);
  }
}
