package org.puzzlebattle.client.protocol.packets.in;

import io.netty.buffer.ByteBuf;
import lombok.Data;
import org.puzzlebattle.core.entity.GameType;
import org.puzzlebattle.core.protocol.ByteBufUtils;

@Data
public class ServerInStartGame extends ServerInPacket {
  private String settings;
  private GameType type;

  @Override
  public void handle(ServerInPacketHandler handler) {
    handler.handle(this);
  }

  @Override
  public void read(ByteBuf buf) {
    settings = ByteBufUtils.readString(buf);
    type = GameType.values()[buf.readUnsignedByte()];
  }
}
