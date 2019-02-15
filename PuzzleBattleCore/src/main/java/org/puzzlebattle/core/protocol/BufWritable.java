package org.puzzlebattle.core.protocol;

import io.netty.buffer.ByteBuf;

public interface BufWritable {
  void write(ByteBuf buf);
}
