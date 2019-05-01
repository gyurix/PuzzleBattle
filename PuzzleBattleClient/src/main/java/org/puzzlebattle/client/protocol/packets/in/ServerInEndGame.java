package org.puzzlebattle.client.protocol.packets.in;

import io.netty.buffer.ByteBuf;
import lombok.Data;
import org.puzzlebattle.core.entity.GameWinner;

@Data
public class ServerInEndGame extends ServerInPacket {
  private GameWinner winner;

  @Override
  public void handle(ServerInPacketHandler handler) {
    handler.handle(this);
  }

  @Override
  public void read(ByteBuf buf) {
    winner = GameWinner.values()[buf.readUnsignedByte()];
  }
}
