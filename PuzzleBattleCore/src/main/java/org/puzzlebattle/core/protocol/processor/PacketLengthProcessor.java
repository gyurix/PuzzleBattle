package org.puzzlebattle.core.protocol.processor;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

import static org.puzzlebattle.core.utils.Logging.logInfo;

public class PacketLengthProcessor extends ByteToMessageDecoder implements ChannelErrorReporter {
  int len = -1;

  @Override
  protected void decode(ChannelHandlerContext ctx, ByteBuf buf, List<Object> out) {
    while (true) {
      logInfo("Decode", "readableBytes", buf.readableBytes());
      if (len == -1) {
        if (buf.readableBytes() < 4) {
          logInfo("Don't have 4 bytes", "readableBytes", buf.readableBytes());
          return;
        }
        len = buf.readInt();
      }
      if (len > 1_024_000) {
        buf.release();
        throw new RuntimeException("Packet size limit (1 MB) exceed, received packet with size " + len + " bytes");
      }
      if (buf.readableBytes() < len)
        return;
      out.add(buf.readBytes(len));
      len = -1;
    }
  }
}
