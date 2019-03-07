package org.puzzlebattle.core.protocol;

import io.netty.buffer.ByteBuf;


/**
 * Interface where methods for reading from the buffer are specified
 *
 * @author (Juraj Barath)
 * @version (1.0)
 */

public interface BufReadable {


  /**
   * Method for reading from buffer
   *
   * @param buf byte buffer for reading
   */

  void read(ByteBuf buf);
}
