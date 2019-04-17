package org.puzzlebattle.client.protocol.packets.out;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ServerOutKeepAlive extends ServerOutPacket {
  private int id;

  @Override
  public void write(ByteBuf buf) {
    buf.writeInt(id);
  }
}
