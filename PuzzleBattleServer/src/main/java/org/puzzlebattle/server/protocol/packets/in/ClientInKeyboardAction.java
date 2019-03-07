package org.puzzlebattle.server.protocol.packets.in;

import io.netty.buffer.ByteBuf;
import lombok.Data;

@Data
public class ClientInKeyboardAction extends ClientInPacket {
  private int key;
  private boolean pressed;

  public void handle(ClientInPacketHandler handler) {
    handler.handle(this);
  }

  public void read(ByteBuf buf) {
    key = buf.readInt();
    pressed = buf.readBoolean();
  }
}
