package org.puzzlebattle.server.protocol.packets.in;

import io.netty.buffer.ByteBuf;
import lombok.Data;
import org.puzzlebattle.core.protocol.ByteBufUtils;

@Data
public class ClientInEncryption extends ClientInPacket {
  private byte[] key;

  public void handle(ClientInPacketHandler handler) {
    handler.handle(this);
  }

  public void read(ByteBuf buf) {
    key = ByteBufUtils.readBytes2(buf);
  }
}
