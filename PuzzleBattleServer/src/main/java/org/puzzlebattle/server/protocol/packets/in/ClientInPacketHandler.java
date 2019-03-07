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

  void unexpected(ClientInPacket packet);
}
