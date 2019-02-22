package org.puzzlebattle.core.protocol;

import io.netty.buffer.ByteBuf;

import java.nio.charset.Charset;
import java.util.UUID;


/**
 * Utils for byte buffering
 *
 * @author (Juraj Barath)
 * @version (1.0)
 */

public class ByteBufUtils {
  public static final Charset utf8 = Charset.forName("UTF-8");


  /**
   * Method where bytes will be read into string, chars are in utf8.
   * From the buffer will be every 2 bytes read as one byte -long string.
   *
   * @param  buf  buffer with bytes which should be read
   * @return    string obtained from buffer
   */

  public static String readLongString(ByteBuf buf) {
    int len = buf.readUnsignedShort();
    byte[] str = new byte[len];
    buf.readBytes(str);
    return new String(str, utf8);
  }


  /**
   * Method where bytes will be read into string, chars are in utf8.
   * From the buffer will be every byte read as one byte -string.
   *    *
   * @param  buf  buffer with bytes which should be read
   * @return    string obtained from buffer
   */

  public static String readString(ByteBuf buf) {
    short len = buf.readUnsignedByte();
    byte[] str = new byte[len];
    buf.readBytes(str);
    return new String(str, utf8);
  }


  /**
   * Method which returns new UIID, crated from long values rode from buffer
   *
   * @param  buf  a sample parameter for a method
   * @return    the sum of x and y
   */

  public static UUID readUniqueId(ByteBuf buf) {
    return new UUID(buf.readLong(), buf.readLong());
  }


  /**
   * An example of a method - replace this comment with your ownString will be written into buffer, utf8 is used
   * Bytes are written as short, one as two bytes.
   *
   * @param  buf  buffer where data will be written
   * @param  str  string which will be written into buffer
   */

  public static void writeLongString(ByteBuf buf, String str) {
    byte[] bytes = str.getBytes(utf8);
    buf.writeShort(bytes.length);
    buf.writeBytes(bytes);
  }


  /**
   * String will be written into buffer, utf8 is used
   * Bytes are written as bytes.
   *
   * @param  buf  buffer where data will be written
   * @param  str  string which will be written into buffer
   */

  public static void writeString(ByteBuf buf, String str) {
    byte[] bytes = str.getBytes(utf8);
    buf.writeByte(bytes.length);
    buf.writeBytes(bytes);
  }


  /**
   * Writes most significant bits and least significant bits as long in buffer
   *
   * @param  buf  buffer where data will be written
   * @param  uuid
   */

  public static void writeUniqueId(ByteBuf buf, UUID uuid) {
    buf.writeLong(uuid.getMostSignificantBits());
    buf.writeLong(uuid.getLeastSignificantBits());
  }
}
