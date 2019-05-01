package org.puzzlebattle.server.protocol.packets.out;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.puzzlebattle.core.protocol.ByteBufUtils;

@AllArgsConstructor
@Data
public class ClientOutEncryption extends ClientOutPacket {
  private byte[] aesKey;
  private byte[] decryptIv;
  private byte[] encryptIv;

  public void write(ByteBuf buf) {
    ByteBufUtils.writeBytes(buf, aesKey);
    ByteBufUtils.writeBytes(buf, decryptIv);
    ByteBufUtils.writeBytes(buf, encryptIv);
  }
}
