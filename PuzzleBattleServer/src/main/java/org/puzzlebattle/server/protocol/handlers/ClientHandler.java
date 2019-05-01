package org.puzzlebattle.server.protocol.handlers;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import lombok.Getter;
import org.puzzlebattle.core.protocol.processor.PacketHandler;
import org.puzzlebattle.core.utils.Logging;
import org.puzzlebattle.server.entity.Client;
import org.puzzlebattle.server.protocol.packets.in.ClientInPacket;
import org.puzzlebattle.server.protocol.packets.in.ClientInPacketHandler;
import org.puzzlebattle.server.protocol.packets.out.ClientOutPacket;

@Getter
public abstract class ClientHandler extends PacketHandler implements ClientInPacketHandler {
  protected final Client client;

  public ClientHandler(Channel channel, Client client) {
    super(channel);
    this.client = client;
  }

  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    ClientInPacket p = (ClientInPacket) msg;
    try {
      p.handle(this);
    } catch (Throwable e) {
      Logging.logSevere("Error on handling packet", "packet", p, "error", e);
    }
  }

  public void sendPacket(ClientOutPacket packet) {
    channel.writeAndFlush(packet);
  }

  public void unexpected(ClientInPacket packet) {
    Logging.logSevere("Unexpected packet", "client", client, "packet", packet);
  }
}
