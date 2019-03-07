package org.puzzlebattle.server.protocol.packets.out;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ClientOutLoginResult extends ClientOutPacket {
  private boolean result;

  public void write(ByteBuf buf) {
    buf.writeBoolean(result);
  }
}
