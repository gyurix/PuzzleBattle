package org.puzzlebattle.core.protocol.processor;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.Getter;

@Getter
public abstract class PacketHandler extends ChannelInboundHandlerAdapter {
  protected final Channel channel;

  public PacketHandler(Channel channel) {
    this.channel = channel;
  }
}
