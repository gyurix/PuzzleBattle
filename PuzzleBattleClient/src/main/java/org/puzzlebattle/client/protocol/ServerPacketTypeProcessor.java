package org.puzzlebattle.client.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import org.puzzlebattle.client.protocol.packets.in.ServerInPacket;
import org.puzzlebattle.client.protocol.packets.in.ServerInType;
import org.puzzlebattle.client.protocol.packets.out.ServerOutPacket;
import org.puzzlebattle.client.protocol.packets.out.ServerOutType;
import org.puzzlebattle.core.utils.Logging;

import static io.netty.buffer.ByteBufAllocator.DEFAULT;

public class ServerPacketTypeProcessor extends ChannelDuplexHandler {
  private static final ServerInType[] inTypes = ServerInType.values();

  public void channelRead(ChannelHandlerContext ctx, Object packet) {
    ByteBuf buf = (ByteBuf) packet;
    int packetId = buf.readUnsignedByte();
    if (packetId >= inTypes.length) {
      buf.release();
      throw new RuntimeException("Invalid incoming client packet id " + packetId + ". Packet id must be between 0 and " + inTypes.length);
    }
    ServerInPacket p;
    try {
      p = inTypes[packetId].of(buf);
      Logging.logInfo("Received packet", "packet", p);
    } catch (Throwable e) {
      buf.release();
      throw e;
    }
    buf.release();
    ctx.fireChannelRead(p);
  }

  public void write(ChannelHandlerContext ctx, Object packet, ChannelPromise promise) {
    ByteBuf buf = DEFAULT.buffer();
    ServerOutPacket p = ((ServerOutPacket) packet);
    buf.writeInt(0);
    buf.writeByte(ServerOutType.of(p).ordinal());
    try {
      p.write(buf);
      Logging.logInfo("Sent packet", "packet", p);
    } catch (Throwable e) {
      buf.release();
      throw e;
    }
    int len = buf.writerIndex();
    buf.resetWriterIndex();
    buf.writeInt(len - 4);
    buf.writerIndex(len);
    ctx.write(buf, promise);
  }
}
