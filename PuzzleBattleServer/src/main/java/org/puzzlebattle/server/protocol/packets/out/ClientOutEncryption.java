package org.puzzlebattle.server.protocol.packets.out;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ClientOutEncryption extends ClientOutPacket {
  private byte[] aesKey;
  private byte[] decryptIv;
  private byte[] encryptIv;

  public void write(ByteBuf buf) {
    buf.writeBytes(aesKey);
    buf.writeBytes(decryptIv);
    buf.writeBytes(encryptIv);
  }
}
