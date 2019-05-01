package org.puzzlebattle.server.protocol.packets.out;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.puzzlebattle.server.db.entity.GameWinner;

@AllArgsConstructor
@Data
public class ClientOutEndGame extends ClientOutPacket {
  private GameWinner winner;

  public void write(ByteBuf buf) {
    buf.writeByte(winner.ordinal());
  }
}
