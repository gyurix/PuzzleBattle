package org.puzzlebattle.client.protocol;

import lombok.Data;
import org.puzzlebattle.client.protocol.handlers.ServerHandler;
import org.puzzlebattle.client.protocol.packets.out.ServerOutPacket;

@Data
public class Server {
  private ServerHandler handler;

  public void sendPacket(ServerOutPacket packet) {
    handler.sendPacket(packet);
  }
}
