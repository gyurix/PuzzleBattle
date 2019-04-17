package org.puzzlebattle.client.protocol.packets.out;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ServerOutKeyboardAction extends ServerOutPacket {
  private int key;
  private boolean pressed;

  @Override
  public void write(ByteBuf buf) {
    buf.writeInt(key);
    buf.writeBoolean(pressed);
  }
}
