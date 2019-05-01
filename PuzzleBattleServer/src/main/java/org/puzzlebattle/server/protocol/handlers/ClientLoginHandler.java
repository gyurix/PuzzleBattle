package org.puzzlebattle.server.protocol.handlers;

import io.netty.channel.Channel;
import org.puzzlebattle.server.db.entity.PBUser;
import org.puzzlebattle.server.db.entity.UserManager;
import org.puzzlebattle.server.entity.Client;
import org.puzzlebattle.server.protocol.packets.in.ClientInLogin;
import org.puzzlebattle.server.protocol.packets.in.ClientInRegister;
import org.puzzlebattle.server.protocol.packets.out.ClientOutLoginResult;

public class ClientLoginHandler extends ClientHandler {
  public ClientLoginHandler(Channel channel, Client client) {
    super(channel, client);
  }

  public void finishLogin(PBUser user) {
    sendPacket(new ClientOutLoginResult(user != null));
    if (user == null)
      return;
    client.setUser(user);
    ClientConnectedHandler newHandler = new ClientConnectedHandler(channel, client);
    channel.pipeline().replace("handler", "handler", newHandler);
    client.setHandler(newHandler);
  }

  @Override
  public void handle(ClientInLogin packet) {
    UserManager.findUser(packet.getUsername(), (u) -> {
      boolean result = u != null && UserManager.verifyPassword(packet.getPassword(), u.getPassword());
      finishLogin(result ? u : null);
    });
  }

  @Override
  public void handle(ClientInRegister packet) {
    UserManager.registerUser(packet.getUser(), this::finishLogin);
  }
}
