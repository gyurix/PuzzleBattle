package org.puzzlebattle.client.protocol.handlers;

import io.netty.channel.Channel;
import org.puzzlebattle.client.protocol.Server;

public class ServerEncryptionHandler extends ServerHandler {
  public ServerEncryptionHandler(Channel channel, Server server) {
    super(channel, server);
  }
}
