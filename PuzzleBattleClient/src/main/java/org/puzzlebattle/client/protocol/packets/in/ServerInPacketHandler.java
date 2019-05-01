package org.puzzlebattle.client.protocol.packets.in;

public interface ServerInPacketHandler {

  default void handle(ServerInLoginResult packet) {
    unexpected(packet);
  }

  default void handle(ServerInChangeProfile packet) {
    unexpected(packet);
  }

  default void handle(ServerInBestPlayers packet) {
    unexpected(packet);
  }

  default void handle(ServerInRegisterSuccessful packet) {
    unexpected(packet);
  }

  default void handle(ServerInEncryption packet) {
    unexpected(packet);
  }

  void unexpected(ServerInPacket packet);
}
