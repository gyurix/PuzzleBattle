package org.puzzlebattle.client.protocol.packets.out;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.puzzlebattle.core.protocol.ByteBufUtils;

@AllArgsConstructor
@Data
public class ServerOutEncryption extends ServerOutPacket {
  private byte[] key;

  public void write(ByteBuf buf) {
    ByteBufUtils.writeBytes2(buf, key);
  }
}
