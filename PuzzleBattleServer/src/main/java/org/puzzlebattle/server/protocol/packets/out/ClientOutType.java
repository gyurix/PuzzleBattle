package org.puzzlebattle.server.protocol.packets.out;

import java.util.HashMap;

public enum ClientOutType {
  ENCRYPTION(ClientOutEncryption.class),
  KEEP_ALIVE(ClientOutKeepAlive.class),
  LOGIN_RESULT(ClientOutLoginResult.class);

  private static HashMap<Class<? extends ClientOutPacket>, ClientOutType> types = new HashMap<>();

  static {
    for (ClientOutType t : ClientOutType.values())
      types.put(t.type, t);
  }

  private Class<? extends ClientOutPacket> type;

  ClientOutType(Class<? extends ClientOutPacket> type) {
    this.type = type;
  }

  public static ClientOutType of(ClientOutPacket packet) {
    return types.get(packet.getClass());
  }
}
