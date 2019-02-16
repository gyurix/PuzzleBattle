package org.puzzlebattle.core.protocol;

import io.netty.buffer.ByteBuf;

public abstract class AbstractInPacket {
  public abstract void read(ByteBuf buf);
}
