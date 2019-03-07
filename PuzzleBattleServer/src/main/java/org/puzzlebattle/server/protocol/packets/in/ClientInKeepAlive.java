package org.puzzlebattle.server.protocol.packets.in;

import io.netty.buffer.ByteBuf;
import lombok.Data;

@Data
public class ClientInKeepAlive extends ClientInPacket {
  int id;

  public void handle(ClientInPacketHandler handler) {
    handler.handle(this);
  }

  public void read(ByteBuf buf) {
    id = buf.readInt();
  }
}
