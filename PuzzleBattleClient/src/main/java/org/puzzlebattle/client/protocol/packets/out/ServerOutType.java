package org.puzzlebattle.client.protocol.packets.out;

import java.util.HashMap;

public enum ServerOutType {
  ENCRYPTION(ServerOutEncryption.class),
  KEEP_ALIVE(ServerOutKeepAlive.class),
  KEYBOARD_ACTION(ServerOutKeyboardAction.class),
  LOGIN_RESULT(ServerOutLogin.class);

  private static HashMap<Class<? extends ServerOutPacket>, ServerOutType> types = new HashMap<>();

  static {
    for (ServerOutType t : ServerOutType.values())
      types.put(t.type, t);
  }

  private Class<? extends ServerOutPacket> type;

  ServerOutType(Class<? extends ServerOutPacket> type) {
    this.type = type;
  }

  public static ServerOutType of(ServerOutPacket packet) {
    return types.get(packet.getClass());
  }
}
