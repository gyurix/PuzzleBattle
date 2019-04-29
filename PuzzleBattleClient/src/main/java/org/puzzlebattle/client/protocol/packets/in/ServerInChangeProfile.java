package org.puzzlebattle.client.protocol.packets.in;

import io.netty.buffer.ByteBuf;
import lombok.Data;
import org.puzzlebattle.client.games.UserPuzzleBattle;

import static org.puzzlebattle.core.protocol.ByteBufUtils.readBytes;
import static org.puzzlebattle.core.protocol.ByteBufUtils.readString;


@Data
public class ServerInChangeProfile extends ServerInPacket {
  private UserPuzzleBattle profile;

  @Override
  public void handle(ServerInPacketHandler handler) {
    handler.handle(this);
  }

  @Override
  public void read(ByteBuf buf) {
    profile = UserPuzzleBattle.builder().userName(readString(buf))
            .password(readString(buf))
            .name(readString(buf))
            .surname(readString(buf))
            .email(readString(buf))
            .dateOfBirth(readString(buf))
            .age(buf.readInt())
            .avatar(readBytes(buf)).build();
  }
}
