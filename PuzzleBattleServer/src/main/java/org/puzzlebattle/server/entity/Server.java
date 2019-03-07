package org.puzzlebattle.server.entity;

import lombok.Getter;

@Getter
public class Server {
  private AddressInfo address;

  public Server(AddressInfo address) {
    this.address = address;
  }
}
