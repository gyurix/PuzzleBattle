package org.puzzlebattle.client.protocol.packets.out;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Data;

import static org.puzzlebattle.core.protocol.ByteBufUtils.writeString;

@AllArgsConstructor
@Data
public class ServerOutChangeProfile extends ServerOutPacket {
  private String name;
  private String surname;
  private String dateOfBirth;
  private byte[] avatar;

  @Override
  public void write(ByteBuf buf) {
    writeString(buf, name);
    writeString(buf, surname);
    writeString(buf, dateOfBirth);
    buf.writeBytes(avatar);
  }
}
