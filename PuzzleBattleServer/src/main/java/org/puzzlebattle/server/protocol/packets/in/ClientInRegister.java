package org.puzzlebattle.server.protocol.packets.in;

import io.netty.buffer.ByteBuf;
import lombok.Data;
import org.puzzlebattle.server.db.entity.UserPuzzleBattle;

@Data
public class ClientInRegister extends ClientInPacket {
  private UserPuzzleBattle user;

  public void handle(ClientInPacketHandler handler) {
    handler.handle(this);
  }

  public void read(ByteBuf buf) {
    user = new UserPuzzleBattle();
    user.read(buf);
  }
}
