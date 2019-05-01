package org.puzzlebattle.server.protocol.packets.out;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ClientOutUpdateGame extends ClientOutPacket {
  private int[] data;

  public void write(ByteBuf buf) {
    buf.writeByte(data.length);
    for (int i : data)
      buf.writeInt(i);
  }
}
