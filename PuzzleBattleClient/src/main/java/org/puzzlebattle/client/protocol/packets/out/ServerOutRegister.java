package org.puzzlebattle.client.protocol.packets.out;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Data;

import static org.puzzlebattle.core.protocol.ByteBufUtils.writeString;

@AllArgsConstructor
@Data
public class ServerOutRegister extends ServerOutPacket {
  private String email;
  private String password;
  private String username;

  @Override
  public void write(ByteBuf buf) {
    writeString(buf, email);
    writeString(buf, username);
    writeString(buf, password);
  }
}
