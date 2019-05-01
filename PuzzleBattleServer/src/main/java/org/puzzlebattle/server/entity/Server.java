package org.puzzlebattle.server.entity;

import lombok.Getter;
import org.puzzlebattle.core.entity.AddressInfo;
import org.puzzlebattle.server.protocol.ServerConnection;

@Getter
public class Server {
  private AddressInfo address;
  private ServerConnection connection;

  public Server(AddressInfo address) {
    this.address = address;
    this.connection = new ServerConnection(this);
  }
}
