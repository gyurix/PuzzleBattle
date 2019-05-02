package org.puzzlebattle.server.protocol.handlers;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import org.puzzlebattle.core.utils.Logging;
import org.puzzlebattle.server.db.UserGameAttributes;
import org.puzzlebattle.server.db.entity.PBUser;
import org.puzzlebattle.server.db.entity.UserManager;
import org.puzzlebattle.server.entity.Client;
import org.puzzlebattle.server.game.Game;
import org.puzzlebattle.server.manager.MatchManager;
import org.puzzlebattle.server.protocol.packets.in.*;
import org.puzzlebattle.server.protocol.packets.out.ClientOutBestPlayers;
import org.puzzlebattle.server.protocol.packets.out.ClientOutChangeProfile;
import org.puzzlebattle.server.protocol.packets.out.ClientOutKeepAlive;

import java.util.List;

public class ClientConnectedHandler extends ClientHandler {
  public ClientConnectedHandler(Channel channel, Client client) {
    super(channel, client);
  }

  @Override
  public void handle(ClientInChangeProfile packet) {
    PBUser user = client.getUser();
    PBUser puser = packet.getUser();
    user.setName(puser.getName());
    user.setSurname(puser.getSurname());
    user.setDateOfBirth(puser.getDateOfBirth());
    user.setAvatar(puser.getAvatar());
    user.update();
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
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
    Logging.logInfo("Client disconnected", "client", client);
  }

  @Override
  public void handle(ClientInBestPlayersRequest packet) {
    List<UserGameAttributes> bestPlayers = UserManager.getBestPlayers(packet.getNumberBestPlayers());
    ClientOutBestPlayers bestPlayersPacket = new ClientOutBestPlayers(bestPlayers, bestPlayers.size());
    client.getHandler().sendPacket(bestPlayersPacket);
  }

  @Override
  public void handle(ClientInUserInfRequest packet) {
    try {

      ClientOutChangeProfile clientOutChangeProfile = new ClientOutChangeProfile(client.getUser());
      if(client.getUser().getDateOfBirth()!=null && client.getUser().getName()!=null
              && client.getUser().getSurname()!=null) {
        client.getHandler().sendPacket(clientOutChangeProfile);
      }
      else {
        Logging.logInfo("Change profile packet not send, no specific information available!");
      }
    }
    catch(Throwable t){
      t.printStackTrace();
    }
  }
}
