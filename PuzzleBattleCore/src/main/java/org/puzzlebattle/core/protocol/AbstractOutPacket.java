package org.puzzlebattle.core.protocol;

import io.netty.buffer.ByteBuf;


/**
 * Abstract class where methods for writing are specified
 *
 * @author (Juraj Barath)
 * @version (1.0)
 */

public abstract class AbstractOutPacket {


  /**
   * Method for writing into buffer
   *
   * @param buf buffer where data will be written
   */

  public abstract void write(ByteBuf buf);
}
