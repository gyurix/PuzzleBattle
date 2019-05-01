package org.puzzlebattle.server.entity;

import lombok.Getter;
import lombok.Setter;
import org.puzzlebattle.core.utils.EncryptionUtils;
import org.puzzlebattle.server.db.entity.PBUser;
import org.puzzlebattle.server.protocol.handlers.ClientHandler;

@Getter
public class Client {
  @Setter
  private ClientHandler handler;
  private EncryptionUtils keyUtils = new EncryptionUtils();
  private Server server;
  @Setter
  private PBUser user;

  public Client(Server server) {
    this.server = server;
  }
}
