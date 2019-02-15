package org.puzzlebattle.core.protocol;

import io.netty.buffer.ByteBuf;

public abstract class AbstractOutPacket {
  public abstract void write(ByteBuf buf);
}
