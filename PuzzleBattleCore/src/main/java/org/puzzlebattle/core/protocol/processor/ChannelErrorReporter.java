package org.puzzlebattle.core.protocol.processor;

import io.netty.channel.ChannelHandlerContext;

public interface ChannelErrorReporter {
  default void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    cause.printStackTrace();
    ctx.channel().close();
  }
}
