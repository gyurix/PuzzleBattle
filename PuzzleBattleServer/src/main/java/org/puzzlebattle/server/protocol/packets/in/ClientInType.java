package org.puzzlebattle.server.protocol.packets.in;

import io.netty.buffer.ByteBuf;
import org.puzzlebattle.core.utils.Callable;
import org.puzzlebattle.server.protocol.packets.out.ClientOutBestPlayers;

public enum ClientInType {
  ENCRYPTION(ClientInEncryption::new),
  KEYBOARD_ACTION(ClientInKeyboardAction::new),
  KEEP_ALIVE(ClientInKeepAlive::new),
  MOUSE_ACTION(ClientInMouseAction::new),
  LOGIN(ClientInLogin::new),
  ADDITIONAL_INFORMATION(ClientInChangeProfile::new),
  END_GAME_FROM_USER(ClientInEndGame::new),
  FIND_FRIEND(ClientInFindFriend::new),
  GAME_PLAYER(ClientInGamePlayer::new),
  LOAD_FRIENDS(ClientInFindFriend::new),
  BEST_PLAYERS(ClientInBestPlayersRequest::new);

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
