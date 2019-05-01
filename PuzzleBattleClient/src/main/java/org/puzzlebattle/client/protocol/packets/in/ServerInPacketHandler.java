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

  default void handle(ServerInEncryption packet) { unexpected(packet); }

  default void handle(ServerInKeepAlive packet) { unexpected(packet); }

  default void handle(ServerInEndGame packet) {
    unexpected(packet);
  }

  default void handle(ServerInStartGame packet) {
    unexpected(packet);
  }

  default void handle(ServerInUpdateGame packet) {
    unexpected(packet);
  }

  void unexpected(ServerInPacket packet);
}
