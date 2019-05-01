package org.puzzlebattle.client.protocol.packets.out;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ServerOutUpdateGame extends ServerOutPacket {
  private int[] data;

  public void write(ByteBuf buf) {
    buf.writeByte(data.length);
    for (int i : data)
      buf.writeInt(i);
  }
}
