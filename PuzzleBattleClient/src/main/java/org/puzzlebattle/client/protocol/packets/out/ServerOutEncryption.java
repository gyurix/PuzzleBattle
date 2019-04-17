package org.puzzlebattle.client.protocol.packets.out;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ServerOutEncryption extends ServerOutPacket {
  private byte[] key;

  public void write(ByteBuf buf) {
    buf.writeShort(key.length);
    buf.writeBytes(key);
  }
}
