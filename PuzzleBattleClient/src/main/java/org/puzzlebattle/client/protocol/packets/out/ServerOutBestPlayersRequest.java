package org.puzzlebattle.client.protocol.packets.out;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ServerOutBestPlayersRequest extends ServerOutPacket {
  private int numberBestPlayers;

  @Override
  public void write(ByteBuf buf) {
    buf.writeInt(numberBestPlayers);
  }
}
