package org.puzzlebattle.client.protocol.packets.in;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ServerInLoginResult extends ServerInPacket {
  private boolean result;

  @Override
  public void handle(ServerInPacketHandler handler) {
    handler.handle(this);
  }

  @Override
  public void read(ByteBuf buf) {
    result = buf.readBoolean();
  }
}
