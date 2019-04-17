package org.puzzlebattle.client.protocol.packets.out;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Data;

import static org.puzzlebattle.core.protocol.ByteBufUtils.writeString;

@AllArgsConstructor
@Data
public class ServerOutInsertGameSettings extends ServerOutPacket{
  private int gameType;

  @Override
  public void write(ByteBuf buf) {
    buf.writeInt(gameType);
  }
}
