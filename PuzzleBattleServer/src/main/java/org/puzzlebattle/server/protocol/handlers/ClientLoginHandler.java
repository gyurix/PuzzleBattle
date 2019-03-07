package org.puzzlebattle.server.protocol.handlers;

import io.netty.channel.Channel;
import org.puzzlebattle.server.entity.Client;
import org.puzzlebattle.server.protocol.packets.in.ClientInLogin;
import org.puzzlebattle.server.protocol.packets.out.ClientOutLoginResult;

public class ClientLoginHandler extends ClientHandler {
  public ClientLoginHandler(Channel channel, Client client) {
    super(channel, client);
  }

  @Override
  public void handle(ClientInLogin packet) {
    boolean result = true; //TODO: Authentication
    sendPacket(new ClientOutLoginResult(result));
    if (!result) {
      channel.close();
      return;
    }
    /*client.setProfile(profile);
    client.getServer().addClient(client);*/

    ClientConnectedHandler newHandler = new ClientConnectedHandler(channel, client);
    channel.pipeline().replace("handler", "handler", newHandler);
    client.setHandler(newHandler);
  }
}
