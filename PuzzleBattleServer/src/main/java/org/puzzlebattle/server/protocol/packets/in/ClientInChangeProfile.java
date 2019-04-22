package org.puzzlebattle.server.protocol.packets.in;


import io.netty.buffer.ByteBuf;
import lombok.Data;
import org.puzzlebattle.core.protocol.ByteBufUtils;

@Data
public class ClientInChangeProfile extends ClientInPacket {
  private byte[] avatar;
  private String name, surname, dateOfBirth;

  public void handle(ClientInPacketHandler handler) {
    handler.handle(this);
  }

  public void read(ByteBuf buf) {
    name = ByteBufUtils.readString(buf);
    surname = ByteBufUtils.readString(buf);
    dateOfBirth = ByteBufUtils.readString(buf);
    avatar = ByteBufUtils.readBytes4(buf);
  }
}
