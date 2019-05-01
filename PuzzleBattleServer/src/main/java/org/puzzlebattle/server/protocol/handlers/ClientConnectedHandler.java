package org.puzzlebattle.server.protocol.handlers;

import io.netty.channel.Channel;
import org.puzzlebattle.server.MatchManager;
import org.puzzlebattle.server.db.UserGameAttributes;
import org.puzzlebattle.server.db.entity.UserManager;
import org.puzzlebattle.server.entity.Client;
import org.puzzlebattle.server.game.Game;
import org.puzzlebattle.server.protocol.packets.in.*;
import org.puzzlebattle.server.protocol.packets.out.ClientOutBestPlayers;
import org.puzzlebattle.server.protocol.packets.out.ClientOutKeepAlive;

import java.util.List;

public class ClientConnectedHandler extends ClientHandler {
  public ClientConnectedHandler(Channel channel, Client client) {
    super(channel, client);
  }

  @Override
  public void handle(ClientInKeepAlive packet) {
    sendPacket(new ClientOutKeepAlive(packet.getId()));
  }

  @Override
  public void handle(ClientInUpdateGame packet) {
    Game g = client.getGame();
    if (g != null)
      g.update(client, packet.getData());
  }

  @Override
  public void handle(ClientInLoadFriends packet) {
  }

  @Override
  public void handle(ClientInEndGame packet) {
    Game g = client.getGame();
    if (g != null)
      g.lose(client);
  }

  @Override
  public void handle(ClientInFindFriend packet) {

  }

  @Override
  public void handle(ClientInStartGame packet) {
    MatchManager.getInstance().start(client, packet.getType());
  }

  @Override
  public void handle(ClientInBestPlayersRequest packet) {
    List<UserGameAttributes> bestPlayers = UserManager.getBestPlayers(packet.getNumberBestPlayers());
    ClientOutBestPlayers bestPlayersPacket = new ClientOutBestPlayers(bestPlayers,bestPlayers.size());
    client.getHandler().sendPacket(bestPlayersPacket);
  }
}
