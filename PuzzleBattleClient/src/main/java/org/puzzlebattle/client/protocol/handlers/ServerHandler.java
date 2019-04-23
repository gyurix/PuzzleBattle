package org.puzzlebattle.client.protocol.handlers;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import lombok.Getter;
import org.puzzlebattle.client.protocol.Server;
import org.puzzlebattle.client.protocol.packets.in.ServerInPacket;
import org.puzzlebattle.client.protocol.packets.in.ServerInPacketHandler;
import org.puzzlebattle.client.protocol.packets.out.ServerOutPacket;
import org.puzzlebattle.core.protocol.processor.PacketHandler;
import org.puzzlebattle.core.utils.Logging;

@Getter
public abstract class ServerHandler extends PacketHandler implements ServerInPacketHandler {
  protected final Server server;

  public ServerHandler(Channel channel, Server server) {
    super(channel);
    this.server = server;
  }

  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    ServerInPacket p = (ServerInPacket) msg;
    p.handle(this);
  }

  public void sendPacket(ServerOutPacket packet) {
    channel.writeAndFlush(packet);
  }

  public void unexpected(ServerInPacket packet) {
    Logging.logSevere("Unexpected packet", "server", server, "packet", packet);
  }
}
