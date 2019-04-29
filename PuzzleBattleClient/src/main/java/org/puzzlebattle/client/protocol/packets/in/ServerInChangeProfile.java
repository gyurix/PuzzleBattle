package org.puzzlebattle.client.protocol.packets.in;

import io.netty.buffer.ByteBuf;
import lombok.Data;
import org.puzzlebattle.client.games.UserPuzzleBattle;
import org.puzzlebattle.core.protocol.ByteBufUtils;


@Data
public class ServerInChangeProfile extends ServerInPacket {
  private UserPuzzleBattle userPuzzleBattle;

  @Override
    public void handle(ServerInPacketHandler handler) {
      handler.handle(this);
    }

    @Override
    public void read(ByteBuf buf) {
    String nickName = ByteBufUtils.readString(buf);
    String password = ByteBufUtils.readString(buf);
    String name = ByteBufUtils.readString(buf);
    String surname = ByteBufUtils.readString(buf);
    String email = ByteBufUtils.readString(buf);
    String dateOfBirth = ByteBufUtils.readString(buf);
    int age = buf.readInt();
    byte[] bytes = ByteBufUtils.readBytes(buf);

    userPuzzleBattle = new UserPuzzleBattle(nickName,password,age,bytes,dateOfBirth,email,name,surname);

  }
}
