package org.puzzlebattle.client.protocol.packets.out;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Data;

import static org.puzzlebattle.core.protocol.ByteBufUtils.writeString;

@AllArgsConstructor
@Data
public class ServerOutLogin extends ServerOutPacket {
  private String password;
  private String username;

  @Override
  public void write(ByteBuf buf) {
    writeString(buf, username);
    writeString(buf, password);
  }
}
