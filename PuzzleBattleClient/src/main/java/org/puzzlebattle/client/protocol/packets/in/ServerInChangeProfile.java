package org.puzzlebattle.client.protocol.packets.in;

import io.netty.buffer.ByteBuf;
import lombok.Data;
import org.puzzlebattle.client.games.User;

import static org.puzzlebattle.core.protocol.ByteBufUtils.*;


@Data
public class ServerInChangeProfile extends ServerInPacket {
  private User profile;

  @Override
  public void handle(ServerInPacketHandler handler) {
    handler.handle(this);
  }

  @Override
  public void read(ByteBuf buf) {
    profile = User.builder().userName(readString(buf))
            .password(readString(buf))
            .name(readString(buf))
            .surname(readString(buf))
            .email(readString(buf))
            .dateOfBirth(readString(buf))
            .age(buf.readInt())
            .avatar(readBytes4(buf)).build();
  }
}
