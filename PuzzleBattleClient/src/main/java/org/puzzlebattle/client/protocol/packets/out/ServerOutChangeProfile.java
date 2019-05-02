package org.puzzlebattle.client.protocol.packets.out;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.puzzlebattle.core.protocol.ByteBufUtils;

import static org.puzzlebattle.core.protocol.ByteBufUtils.writeString;

@AllArgsConstructor
@Data
public class ServerOutChangeProfile extends ServerOutPacket {
  private byte[] avatar;
  private String dateOfBirth;
  private String name;
  private String surname;

  @Override
  public void write(ByteBuf buf) {
    writeString(buf, name);
    writeString(buf, surname);
    writeString(buf, dateOfBirth);
    ByteBufUtils.writeBytes4(buf,avatar);
  }
}
