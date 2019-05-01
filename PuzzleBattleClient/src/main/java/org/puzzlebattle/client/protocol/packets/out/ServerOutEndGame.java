package org.puzzlebattle.client.protocol.packets.out;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ServerOutEndGame extends ServerOutPacket {

  @Override
  public void write(ByteBuf buf) {
  }
}
