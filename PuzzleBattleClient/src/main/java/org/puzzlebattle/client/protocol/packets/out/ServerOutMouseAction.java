package org.puzzlebattle.client.protocol.packets.out;

import io.netty.buffer.ByteBuf;
import javafx.geometry.Point2D;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.puzzlebattle.core.protocol.ByteBufUtils;

@AllArgsConstructor
@Data
public class ServerOutMouseAction extends ServerOutPacket {
  private boolean leftPressed;
  private Point2D loc;
  private boolean rightPressed;

  @Override
  public void write(ByteBuf buf) {
    ByteBufUtils.writePoint(buf, loc);
    buf.writeByte((leftPressed ? 1 : 0) | (leftPressed ? 2 : 0));
  }
}
