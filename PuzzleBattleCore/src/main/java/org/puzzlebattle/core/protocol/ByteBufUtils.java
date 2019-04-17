package org.puzzlebattle.core.protocol;

import io.netty.buffer.ByteBuf;
import javafx.geometry.Point2D;

import java.util.UUID;

import static java.nio.charset.StandardCharsets.UTF_8;


/**
 * Utilitites for reading and writing data to ByteBufs used by Netty for client-server communication
 *
 * @author (Juraj Barath)
 * @version (1.0)
 */

public class ByteBufUtils {
  public static String readLongString(ByteBuf buf) {
    int len = buf.readUnsignedShort();
    byte[] str = new byte[len];
    buf.readBytes(str);
    return new String(str, UTF_8);
  }

  public static Point2D readPoint(ByteBuf buf) {
    return new Point2D(buf.readInt(), buf.readInt());
  }


  /**
   * Method where bytes will be read into string, chars are in UTF_8.
   * From the buffer will be every byte read as one byte -string.
   * *
   *
   * @param buf buffer with bytes which should be read
   * @return string obtained from buffer
   */
  public static String readString(ByteBuf buf) {
    short len = buf.readUnsignedByte();
    byte[] str = new byte[len];
    buf.readBytes(str);
    return new String(str, UTF_8);
  }


  /**
   * Reads an unique id from the given ByteBuf, which is stored in a format of two 8 byte long numbers.
   * The first one is the unique ids most significant bits.
   * The second one is the unique ids least significant bits.
   *
   * @param buf a sample parameter for a method
   * @return the sum of x and y
   */
  public static UUID readUniqueId(ByteBuf buf) {
    return new UUID(buf.readLong(), buf.readLong());
  }


  /**
   * Writes the given long String (up to 65535 characters) to the given ByteBuf in a format of
   * length (unsigned short), bytes of the given String encoded with UTF-8 charset.
   *
   * @param buf - The buffer to which the data will be written
   * @param str - The writable string
   */
  public static void writeLongString(ByteBuf buf, String str) {
    byte[] bytes = str.getBytes(UTF_8);
    buf.writeShort(bytes.length);
    buf.writeBytes(bytes);
  }

  public static void writePoint(ByteBuf buf, Point2D point) {
    buf.writeInt((int) point.getX());
    buf.writeInt((int) point.getY());
  }


  /**
   * Writes the given short String (up to 255 characters) to the given ByteBuf in a format of
   * length (unsigned byte), bytes of the given String encoded with UTF-8 charset.
   *
   * @param buf - The buffer to which the data will be written
   * @param str - The writable string
   */
  public static void writeString(ByteBuf buf, String str) {
    byte[] bytes = str.getBytes(UTF_8);
    buf.writeByte(bytes.length);
    buf.writeBytes(bytes);
  }


  /**
   * Writes the given unique id to the given ByteBuf in a format of two 8 byte long numbers.
   * The first one is the unique ids most significant bits.
   * The second one is the unique ids least significant bits.
   *
   * @param buf  - The ByteBuf to which the data will be written
   * @param uuid - The writable unique id
   */
  public static void writeUniqueId(ByteBuf buf, UUID uuid) {
    buf.writeLong(uuid.getMostSignificantBits());
    buf.writeLong(uuid.getLeastSignificantBits());
  }
}
