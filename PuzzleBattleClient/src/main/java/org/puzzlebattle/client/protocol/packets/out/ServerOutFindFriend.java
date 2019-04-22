package org.puzzlebattle.client.protocol.packets.out;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Data;

import static org.puzzlebattle.core.protocol.ByteBufUtils.writeString;

@AllArgsConstructor
@Data
public class ServerOutFindFriend extends ServerOutPacket {
  private int age;
  private String name;
  private String nickName;
  private int score;
  private String surname;

  @Override
  public void write(ByteBuf buf) {
    writeString(buf, nickName);
    buf.writeInt(score);
    writeString(buf, name);
    writeString(buf, surname);
    buf.writeInt(age);
  }

}
