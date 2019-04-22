package org.puzzlebattle.server.protocol.packets.in;

import io.netty.buffer.ByteBuf;
import lombok.Data;
import org.puzzlebattle.core.protocol.ByteBufUtils;

@Data
public class ClientInLoadFriends extends ClientInPacket {
  private String password;
  private String username;

  public void handle(ClientInPacketHandler handler) {
    handler.handle(this);
  }

  public void read(ByteBuf buf) {
    username = ByteBufUtils.readString(buf);
    password = ByteBufUtils.readString(buf);
  }
}
