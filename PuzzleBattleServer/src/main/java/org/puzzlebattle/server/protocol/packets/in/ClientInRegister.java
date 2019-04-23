package org.puzzlebattle.server.protocol.packets.in;

import io.netty.buffer.ByteBuf;
import lombok.Data;
import org.puzzlebattle.server.db.entity.User;

@Data
public class ClientInRegister extends ClientInPacket {
  private User user;

  public void handle(ClientInPacketHandler handler) {
    handler.handle(this);
  }

  public void read(ByteBuf buf) {
    user = new User();
    user.read(buf);
  }
}
