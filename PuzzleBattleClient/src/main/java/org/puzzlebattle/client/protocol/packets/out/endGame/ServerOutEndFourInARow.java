package org.puzzlebattle.client.protocol.packets.out.endGame;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.puzzlebattle.client.protocol.packets.out.ServerOutPacket;

import static org.puzzlebattle.core.protocol.ByteBufUtils.writeString;

@AllArgsConstructor
@Data
public class ServerOutEndFourInARow extends ServerOutPacket {
  private int gameTypeForNewUser;
  private String userName;
  private String userPassword;
  private Winner winnerOrNot;

  @Override
  public void write(ByteBuf buf) {
    writeString(buf, userName);
    writeString(buf, userPassword);
    buf.writeInt(gameTypeForNewUser);
    buf.writeInt(winnerOrNot.ordinal());
  }

  public enum Winner {
    P1, P2, DRAW
  }
}
