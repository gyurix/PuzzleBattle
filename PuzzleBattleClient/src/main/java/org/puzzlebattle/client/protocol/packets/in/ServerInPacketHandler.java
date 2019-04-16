package org.puzzlebattle.client.protocol.packets.in;

public interface ServerInPacketHandler {

  default void handle(ServerInLoginResult packet) {
    unexpected(packet);
  }

  void unexpected(ServerInPacket packet);
}
