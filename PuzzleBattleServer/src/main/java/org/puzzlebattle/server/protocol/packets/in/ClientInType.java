package org.puzzlebattle.server.protocol.packets.in;

import io.netty.buffer.ByteBuf;
import org.puzzlebattle.core.utils.Callable;

public enum ClientInType {
  ENCRYPTION(ClientInEncryption::new),
  KEYBOARD_ACTION(ClientInKeyboardAction::new),
  KEEP_ALIVE(ClientInKeepAlive::new),
  MOUSE_ACTION(ClientInMouseAction::new),
  LOGIN(ClientInLogin::new);

  Callable<? extends ClientInPacket> callable;

  ClientInType(Callable<? extends ClientInPacket> callable) {
    this.callable = callable;
  }

  public ClientInPacket of(ByteBuf buf) {
    ClientInPacket packet = callable.call();
    packet.read(buf);
    return packet;
  }
}
