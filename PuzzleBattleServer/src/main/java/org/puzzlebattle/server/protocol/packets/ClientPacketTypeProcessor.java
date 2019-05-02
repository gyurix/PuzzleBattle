package org.puzzlebattle.server.protocol.packets;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import org.puzzlebattle.core.utils.Logging;
import org.puzzlebattle.server.protocol.packets.in.ClientInPacket;
import org.puzzlebattle.server.protocol.packets.in.ClientInType;
import org.puzzlebattle.server.protocol.packets.out.ClientOutPacket;
import org.puzzlebattle.server.protocol.packets.out.ClientOutType;

import static io.netty.buffer.ByteBufAllocator.DEFAULT;
import static org.puzzlebattle.core.utils.Logging.logInfo;
import static org.puzzlebattle.core.utils.Logging.logSevere;

public class ClientPacketTypeProcessor extends ChannelDuplexHandler {
  private static final ClientInType[] inTypes = ClientInType.values();

  public void channelRead(ChannelHandlerContext ctx, Object packet) {
    ByteBuf buf = (ByteBuf) packet;
    int packetId = buf.readUnsignedByte();
    if (packetId >= inTypes.length) {
      buf.release();
      throw new RuntimeException("Invalid incoming client packet id " + packetId + ". Packet id must be between 0 and " + inTypes.length);
    }
    ClientInPacket p;
    p = inTypes[packetId].of(buf);
    buf.release();
    ctx.fireChannelRead(p);
    logInfo("Received packet", "client", getAddress(ctx), "packet", p);
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable e) {
    logSevere("Packet processing error", "client", ctx.channel().remoteAddress().toString().substring(1), "error", e);
    ctx.close();
  }

  private String getAddress(ChannelHandlerContext ctx) {
    return ctx.channel().remoteAddress().toString().substring(1);
  }

  public void write(ChannelHandlerContext ctx, Object packet, ChannelPromise promise) {
    ByteBuf buf = DEFAULT.buffer();
    try {
      ClientOutPacket p = ((ClientOutPacket) packet);
      buf.writeInt(0);
      buf.writeByte(ClientOutType.of(p).ordinal());
      p.write(buf);
      int len = buf.writerIndex();
      buf.resetWriterIndex();
      buf.writeInt(len - 4);
      buf.writerIndex(len);
      ctx.write(buf, promise);
      logInfo("Sent packet", "client", getAddress(ctx), "packet", p);
    }
    catch (Exception e) {
      Logging.logSevere("Error while writing found! Packet can't be send!",e);
    }
  }
}
