package org.puzzlebattle.client.protocol.packets.out;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Data;

import static org.puzzlebattle.core.protocol.ByteBufUtils.writeString;

@AllArgsConstructor
@Data
public class ServerOutStartGame extends ServerOutPacket {
    private String password;
    private String username;
    private int playerNumber;

    @Override
    public void write(ByteBuf buf) {
      writeString(buf, username);
      writeString(buf, password);
      buf.writeInt(playerNumber);
    }

}
