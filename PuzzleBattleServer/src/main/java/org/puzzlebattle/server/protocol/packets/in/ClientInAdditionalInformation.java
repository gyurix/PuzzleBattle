package org.puzzlebattle.server.protocol.packets.in;


import io.netty.buffer.ByteBuf;
import lombok.Data;
import org.puzzlebattle.core.protocol.ByteBufUtils;

@Data
public class ClientInAdditionalInformation extends ClientInPacket {
  private String name, surname, dateOfBirth;
  private byte[] avatar;


  public void handle(ClientInPacketHandler handler) {
    handler.handle(this);
  }

  public void read(ByteBuf buf) {
    name = ByteBufUtils.readString(buf);
    surname = ByteBufUtils.readString(buf);
    dateOfBirth = ByteBufUtils.readString(buf);
    avatar = buf.array();
  }
}
