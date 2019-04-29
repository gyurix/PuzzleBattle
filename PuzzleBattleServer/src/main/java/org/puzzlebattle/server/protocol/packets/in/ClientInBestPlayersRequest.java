package org.puzzlebattle.server.protocol.packets.in;

import io.netty.buffer.ByteBuf;
import lombok.Data;
import org.puzzlebattle.core.protocol.ByteBufUtils;

@Data
public class ClientInBestPlayersRequest extends ClientInPacket {
  private int numberBestPlayers;
  private String username;
  private String password;

  public void handle(ClientInPacketHandler handler) {
    handler.handle(this);
  }

  public void read(ByteBuf buf) {
    username = ByteBufUtils.readString(buf);
    password = ByteBufUtils.readString(buf);
    numberBestPlayers = buf.readInt();
  }

}
