package org.puzzlebattle.server.protocol.packets.in;

import io.netty.buffer.ByteBuf;
import org.puzzlebattle.core.utils.Callable;

public enum ClientInType {
  BEST_PLAYERS_REQUEST(ClientInBestPlayersRequest::new),
  CHANGE_PROFILE(ClientInChangeProfile::new),
  ENCRYPTION(ClientInEncryption::new),
  END_GAME(ClientInEndGame::new),
  FIND_FRIEND(ClientInFindFriend::new),
  KEEP_ALIVE(ClientInKeepAlive::new),
  LOAD_FRIENDS(ClientInLoadFriends::new),
  LOGIN(ClientInLogin::new),
  REGISTER(ClientInRegister::new),
  START_GAME(ClientInStartGame::new),
  UPDATE_GAME(ClientInUpdateGame::new);

  Callable<? extends ClientInPacket> callable;

  ClientInType(Callable<? extends ClientInPacket> callable) {
    this.callable = callable;
  }

  public ClientInPacket of(ByteBuf buf) {
    ClientInPacket packet = callable.call();
    packet.read(buf);
    return packet;
  }
}
