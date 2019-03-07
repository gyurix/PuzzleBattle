package org.puzzlebattle.core.protocol;

import io.netty.buffer.ByteBuf;


/**
 * Interface where methods for writing into buffer are specified
 *
 * @author (Juraj Barath)
 * @version (1.0)
 */

public interface BufWritable {


  /**
   * Method for writing into buffer
   *
   * @param buf buffer where data will be written
   */

  void write(ByteBuf buf);
}
