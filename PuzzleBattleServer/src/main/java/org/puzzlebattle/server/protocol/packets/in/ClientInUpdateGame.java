package org.puzzlebattle.server.protocol.packets.in;

import io.netty.buffer.ByteBuf;
import lombok.Data;

@Data
public class ClientInUpdateGame extends ClientInPacket {
  private int[] data;

  public void handle(ClientInPacketHandler handler) {
    handler.handle(this);
  }

  public void read(ByteBuf buf) {
    int len = buf.readUnsignedByte();
    data = new int[len];
    for (int i = 0; i < len; ++i)
      data[i] = buf.readInt();
  }
}
