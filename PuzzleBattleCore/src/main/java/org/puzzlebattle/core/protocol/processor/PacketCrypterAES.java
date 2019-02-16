package org.puzzlebattle.core.protocol.processor;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import org.puzzlebattle.core.utils.EncryptionUtils;

public class PacketCrypterAES extends ChannelDuplexHandler implements ChannelErrorReporter {
  private EncryptionUtils utils;

  public PacketCrypterAES(EncryptionUtils utils) {
    this.utils = utils;
  }

  @Override
  public void channelRead(ChannelHandlerContext ctx, Object packet) {
    ByteBuf buf = (ByteBuf) packet;
    byte[] encryptedData = new byte[buf.readableBytes()];
    buf.readBytes(encryptedData);
    byte[] decryptedData = utils.decryptAES(encryptedData);
    buf.clear();
    buf.writeBytes(decryptedData);
    ctx.fireChannelRead(buf);
  }

  @Override
  public void write(ChannelHandlerContext ctx, Object packet, ChannelPromise promise) {
    ByteBuf buf = (ByteBuf) packet;
    buf.resetReaderIndex();
    int len = buf.readInt();
    byte[] data = new byte[len];
    buf.readBytes(data);
    try {
      byte[] encrypted = utils.encryptAES(data);
      buf.clear();
      buf.writeInt(encrypted.length);
      buf.writeBytes(encrypted);
      ctx.write(buf, promise);
    } catch (Throwable e) {
      e.printStackTrace();
    }
  }
}
