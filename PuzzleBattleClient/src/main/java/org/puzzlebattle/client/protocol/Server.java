package org.puzzlebattle.client.protocol;

import lombok.Data;
import org.puzzlebattle.client.protocol.handlers.ServerHandler;
import org.puzzlebattle.client.protocol.packets.out.ServerOutPacket;
import org.puzzlebattle.core.entity.AddressInfo;

@Data
public class Server {
  private ServerHandler handler;
  private AddressInfo address;
  private ServerConnection connection;

  public Server(AddressInfo serverAddress) {

  }

  public void sendPacket(ServerOutPacket packet) {
    handler.sendPacket(packet);
  }
}
