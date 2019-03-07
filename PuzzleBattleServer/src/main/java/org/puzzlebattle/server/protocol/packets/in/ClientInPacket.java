package org.puzzlebattle.server.protocol.packets.in;


import org.puzzlebattle.core.protocol.BufReadable;

public abstract class ClientInPacket implements BufReadable {
  public abstract void handle(ClientInPacketHandler handler);
}
