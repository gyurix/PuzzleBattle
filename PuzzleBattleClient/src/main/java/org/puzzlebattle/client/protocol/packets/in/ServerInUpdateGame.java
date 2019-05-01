package org.puzzlebattle.client.protocol.packets.in;

import io.netty.buffer.ByteBuf;
import lombok.Data;

@Data
public class ServerInUpdateGame extends ServerInPacket {
  private int[] data;

  @Override
  public void handle(ServerInPacketHandler handler) {
    handler.handle(this);
  }

  @Override
  public void read(ByteBuf buf) {
    int length  = buf.readByte();
    data = new int[length];
    for(int i= 0; i< length; i=i + 1) {
      data[i] = buf.readInt();
    }
  }
}
