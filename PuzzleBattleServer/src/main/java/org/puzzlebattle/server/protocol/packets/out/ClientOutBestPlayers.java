package org.puzzlebattle.server.protocol.packets.out;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.puzzlebattle.server.db.UserGameAttributes;

import java.util.List;

import static org.puzzlebattle.core.protocol.ByteBufUtils.writeString;

@AllArgsConstructor
@Data
public class ClientOutBestPlayers  extends ClientOutPacket {
  private List<UserGameAttributes> listOfUserGameAttributes;
  private int numberBestPlayersSend;

  public void write(ByteBuf buf){
    numberBestPlayersSend = listOfUserGameAttributes.size();
    buf.writeInt(numberBestPlayersSend);
    for(int i=0;i<numberBestPlayersSend;i=i+1) {
      writeString(buf, listOfUserGameAttributes.get(i).getNickName());
      buf.writeInt(listOfUserGameAttributes.get(i).getScore());
    }
  }
}
