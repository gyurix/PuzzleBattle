package org.puzzlebattle.server.protocol.packets.in;

public interface ClientInPacketHandler {

  default void handle(ClientInEncryption packet) {
    unexpected(packet);
  }

  default void handle(ClientInKeyboardAction packet) {
    unexpected(packet);
  }

  default void handle(ClientInKeepAlive packet) {
    unexpected(packet);
  }

  default void handle(ClientInMouseAction packet) {
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

  default void handle(ClientInGamePlayer packet) {
    unexpected(packet);
  }

  default void handle(ClientInBestPlayersRequest packet) { unexpected(packet); }

  void unexpected(ClientInPacket packet);
}
