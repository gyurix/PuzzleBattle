package org.puzzlebattle.client.protocol.packets.in;

import io.netty.buffer.ByteBuf;
import lombok.Getter;
import org.puzzlebattle.core.protocol.ByteBufUtils;

@Getter
public class ServerInEncryption extends ServerInPacket {
  private byte[] aesKey;
  private byte[] decryptIv;
  private byte[] encryptIv;

  @Override
  public void handle(ServerInPacketHandler handler) {
    handler.handle(this);
  }

  public void read(ByteBuf buf) {
    aesKey = ByteBufUtils.readBytes(buf);
    decryptIv = ByteBufUtils.readBytes(buf);
    encryptIv = ByteBufUtils.readBytes(buf);
  }
}
