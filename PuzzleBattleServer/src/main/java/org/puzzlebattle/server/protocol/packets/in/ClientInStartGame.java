package org.puzzlebattle.server.protocol.packets.in;

import io.netty.buffer.ByteBuf;
import lombok.Data;
import org.puzzlebattle.core.entity.GameType;

@Data
public class ClientInStartGame extends ClientInPacket {
  private GameType type;

  public void handle(ClientInPacketHandler handler) {
    handler.handle(this);
  }

  public void read(ByteBuf buf) {
    type = GameType.values()[buf.readByte()];
  }
}
