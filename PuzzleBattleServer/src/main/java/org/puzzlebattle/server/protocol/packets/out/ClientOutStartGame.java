package org.puzzlebattle.server.protocol.packets.out;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.puzzlebattle.core.entity.GameType;
import org.puzzlebattle.core.protocol.ByteBufUtils;

@AllArgsConstructor
@Data
public class ClientOutStartGame extends ClientOutPacket {
  private boolean initializer;
  private String settings;
  private GameType type;

  public void write(ByteBuf buf) {
    buf.writeBoolean(initializer);
    buf.writeByte(type.ordinal());
    ByteBufUtils.writeString(buf, settings);
  }
}
