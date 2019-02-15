package org.puzzlebattle.core.protocol;

import io.netty.buffer.ByteBuf;

public interface BufReadable {
  void read(ByteBuf buf);
}
