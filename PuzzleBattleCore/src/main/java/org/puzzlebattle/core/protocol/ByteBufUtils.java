package org.puzzlebattle.core.protocol;

import io.netty.buffer.ByteBuf;

import java.nio.charset.Charset;
import java.util.UUID;

public class ByteBufUtils {
  public static final Charset utf8 = Charset.forName("UTF-8");

  public static String readLongString(ByteBuf buf) {
    int len = buf.readUnsignedShort();
    byte[] str = new byte[len];
    buf.readBytes(str);
    return new String(str, utf8);
  }

  public static String readString(ByteBuf buf) {
    short len = buf.readUnsignedByte();
    byte[] str = new byte[len];
    buf.readBytes(str);
    return new String(str, utf8);
  }

  public static UUID readUniqueId(ByteBuf buf) {
    return new UUID(buf.readLong(), buf.readLong());
  }

  public static void writeLongString(ByteBuf buf, String str) {
    byte[] bytes = str.getBytes(utf8);
    buf.writeShort(bytes.length);
    buf.writeBytes(bytes);
  }

  public static void writeString(ByteBuf buf, String str) {
    byte[] bytes = str.getBytes(utf8);
    buf.writeByte(bytes.length);
    buf.writeBytes(bytes);
  }

  public static void writeUniqueId(ByteBuf buf, UUID uuid) {
    buf.writeLong(uuid.getMostSignificantBits());
    buf.writeLong(uuid.getLeastSignificantBits());
  }
}
