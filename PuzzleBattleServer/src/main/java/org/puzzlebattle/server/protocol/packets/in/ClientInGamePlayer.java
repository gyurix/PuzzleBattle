package org.puzzlebattle.server.protocol.packets.in;

import io.netty.buffer.ByteBuf;
import lombok.Data;
import org.puzzlebattle.core.protocol.ByteBufUtils;
import org.puzzlebattle.server.db.entity.GamePlayer;

@Data
public class ClientInGamePlayer extends ClientInPacket {
  private GamePlayer gamePlayer;
  private int gameTypeForNewUser;
  private String password;
  private String username;

  public void handle(ClientInPacketHandler handler) {
    handler.handle(this);
  }

  public void read(ByteBuf buf) {
    gamePlayer = new GamePlayer();
    username = ByteBufUtils.readString(buf);
    password = ByteBufUtils.readString(buf);
    gameTypeForNewUser = buf.readInt();
    //find requested users and add them to gamePlayer entity with duration
  }
}
