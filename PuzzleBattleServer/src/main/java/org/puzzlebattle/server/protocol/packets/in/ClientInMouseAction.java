package org.puzzlebattle.server.protocol.packets.in;

import io.netty.buffer.ByteBuf;
import javafx.geometry.Point2D;
import lombok.Data;
import org.puzzlebattle.core.protocol.ByteBufUtils;

@Data
public class ClientInMouseAction extends ClientInPacket {
  private boolean leftPressed;
  private Point2D loc;
  private boolean rightPressed;

  public void handle(ClientInPacketHandler handler) {
    handler.handle(this);
  }

  public void read(ByteBuf buf) {
    loc = ByteBufUtils.readPoint(buf);
    byte flags = buf.readByte();
    leftPressed = (flags & 1) == 1;
    rightPressed = (flags & 2) == 2;
  }
}
