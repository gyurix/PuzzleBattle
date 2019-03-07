package org.puzzlebattle.core.protocol;

import io.netty.buffer.ByteBuf;


/**
 * Abstract class where methods for reading are specified
 *
 * @author (Juraj Barath)
 * @version (1.0)
 */

public abstract class AbstractInPacket {


  /**
   * Method for reading from buffer
   *
   * @param buf byte buffer for reading
   */

  public abstract void read(ByteBuf buf);
}
