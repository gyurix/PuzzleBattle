package org.puzzlebattle.client.protocol;

import lombok.Data;
import org.puzzlebattle.client.games.User;
import org.puzzlebattle.client.protocol.packets.out.ServerOutPacket;
import org.puzzlebattle.core.entity.AddressInfo;
import org.puzzlebattle.core.utils.EncryptionUtils;

@Data
public class Client {
  private AddressInfo address;
  private ServerConnection connection;
  private User user;
  private EncryptionUtils encryptionUtils = new EncryptionUtils();

  public Client(AddressInfo serverAddress) {
    this.address = serverAddress;
    connection = new ServerConnection(this);
  }

  public void sendPacket(ServerOutPacket packet) {
    connection.getHandler().sendPacket(packet);
  }
}
