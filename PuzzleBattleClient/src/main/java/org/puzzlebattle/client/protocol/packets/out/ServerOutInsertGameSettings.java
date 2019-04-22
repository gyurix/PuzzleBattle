package org.puzzlebattle.client.protocol.packets.out;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ServerOutInsertGameSettings extends ServerOutPacket {
  private int gameType;

  @Override
  public void write(ByteBuf buf) {
    buf.writeInt(gameType);
  }
}
