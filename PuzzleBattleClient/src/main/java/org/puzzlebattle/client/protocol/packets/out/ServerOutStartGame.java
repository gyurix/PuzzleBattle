package org.puzzlebattle.client.protocol.packets.out;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.puzzlebattle.core.entity.GameType;

@AllArgsConstructor
@Data
public class ServerOutStartGame extends ServerOutPacket {
  private GameType gameType;

  @Override
  public void write(ByteBuf buf) {
    buf.writeByte(gameType.ordinal());
  }

}
