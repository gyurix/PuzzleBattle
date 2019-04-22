package org.puzzlebattle.server.protocol.packets.in;

import io.netty.buffer.ByteBuf;
import lombok.Data;
import org.puzzlebattle.core.protocol.ByteBufUtils;

@Data
public class ClientInFindFriend extends ClientInPacket {
  private int age;
  private String name;
  private String nickName;
  private int score;
  private String surname;

  public void handle(ClientInPacketHandler handler) {
    handler.handle(this);
  }

  public void read(ByteBuf buf) {
    nickName = ByteBufUtils.readString(buf);
    score = buf.readInt();
    name = ByteBufUtils.readString(buf);
    surname = ByteBufUtils.readString(buf);
    age = buf.readInt();
  }
}
