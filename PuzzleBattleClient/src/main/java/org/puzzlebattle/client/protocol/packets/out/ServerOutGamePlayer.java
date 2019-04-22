package org.puzzlebattle.client.protocol.packets.out;


import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Data;

import static org.puzzlebattle.core.protocol.ByteBufUtils.writeString;

@AllArgsConstructor
@Data
public class ServerOutGamePlayer extends ServerOutPacket {
  private int gameTypeForNewUser;
  private String userName;
  private String userPassword;

  @Override
  public void write(ByteBuf buf) {
    writeString(buf, userName);
    writeString(buf, userPassword);
    writeString(buf, Integer.toString(gameTypeForNewUser));
  }
}
