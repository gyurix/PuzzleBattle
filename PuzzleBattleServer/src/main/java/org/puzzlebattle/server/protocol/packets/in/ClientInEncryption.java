package org.puzzlebattle.server.protocol.packets.in;

import io.netty.buffer.ByteBuf;
import lombok.Data;

@Data
public class ClientInEncryption extends ClientInPacket {
  private byte[] key;

  public void handle(ClientInPacketHandler handler) {
    handler.handle(this);
  }

  public void read(ByteBuf buf) {
    int len = buf.readUnsignedShort();
    key = new byte[len];
    buf.readBytes(key);
  }
}
