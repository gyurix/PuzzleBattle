package org.puzzlebattle.server.protocol.packets.out;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ClientOutKeepAlive extends ClientOutPacket {
  private int id;

  public void write(ByteBuf buf) {
    buf.writeInt(id);
  }
}
