package org.puzzlebattle.client.protocol.packets.in;

import io.netty.buffer.ByteBuf;
import lombok.Data;

@Data
public class ServerInRegisterSuccessful extends ServerInPacket {
  private boolean successfulRegistration;

  @Override
  public void handle(ServerInPacketHandler handler) {
    handler.handle(this);
  }

  @Override
  public void read(ByteBuf buf) {
    successfulRegistration = buf.readBoolean();
  }
}
