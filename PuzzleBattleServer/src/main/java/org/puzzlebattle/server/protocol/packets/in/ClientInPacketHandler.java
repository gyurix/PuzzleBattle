package org.puzzlebattle.server.protocol.packets.in;

public interface ClientInPacketHandler {

  default void handle(ClientInEncryption packet) {
    unexpected(packet);
  }

  default void handle(ClientInUpdateGame packet) {
    unexpected(packet);
  }

  default void handle(ClientInKeepAlive packet) {
    unexpected(packet);
  }

  default void handle(ClientInLogin packet) {
    unexpected(packet);
  }

  default void handle(ClientInRegister packet) {
    unexpected(packet);
  }

  default void handle(ClientInChangeProfile packet) {
    unexpected(packet);
  }

  default void handle(ClientInLoadFriends packet) {
    unexpected(packet);
  }

  default void handle(ClientInEndGame packet) {
    unexpected(packet);
  }

  default void handle(ClientInFindFriend packet) {
    unexpected(packet);
  }

  default void handle(ClientInBestPlayersRequest packet) {
    unexpected(packet);
  }

  default void handle(ClientInStartGame packet) {
    unexpected(packet);
  }

  default void handle(ClientInUserInfRequest packet) { unexpected(packet); }

  void unexpected(ClientInPacket packet);
}
