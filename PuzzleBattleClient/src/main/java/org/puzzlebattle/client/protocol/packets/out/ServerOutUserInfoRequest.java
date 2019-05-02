package org.puzzlebattle.client.protocol.packets.out;

import io.netty.buffer.ByteBuf;

import static org.puzzlebattle.core.protocol.ByteBufUtils.writeString;

public class ServerOutUserInfoRequest extends ServerOutPacket {

  @Override
  public void write(ByteBuf buf) {
  }
}
