package org.puzzlebattle.client.protocol.packets.in;


import org.puzzlebattle.core.protocol.BufReadable;

public abstract class ServerInPacket implements BufReadable {
  public abstract void handle(ServerInPacketHandler handler);
}
