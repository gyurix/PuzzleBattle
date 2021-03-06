package org.puzzlebattle.client.protocol.packets.in;

import io.netty.buffer.ByteBuf;
import org.puzzlebattle.core.utils.Callable;

public enum ServerInType {
  BEST_PLAYERS(ServerInBestPlayers::new),
  CHANGE_PROFILE(ServerInChangeProfile::new),
  ENCRYPTION(ServerInEncryption::new),
  END_GAME(ServerInEndGame::new),
  KEEP_ALIVE(ServerInKeepAlive::new),
  LOGIN_RESULT(ServerInLoginResult::new),
  START_GAME(ServerInStartGame::new),
  UPDATE_GAME(ServerInUpdateGame::new);

  Callable<? extends ServerInPacket> callable;

  ServerInType(Callable<? extends ServerInPacket> callable) {
    this.callable = callable;
  }

  public ServerInPacket of(ByteBuf buf) {
    ServerInPacket packet = callable.call();
    packet.read(buf);
    return packet;
  }
}
