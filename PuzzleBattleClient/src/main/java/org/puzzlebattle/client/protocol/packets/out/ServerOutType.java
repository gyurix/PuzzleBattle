package org.puzzlebattle.client.protocol.packets.out;

import java.util.HashMap;

public enum ServerOutType {
  BEST_PLAYERS_REQUEST(ServerOutBestPlayersRequest.class),
  CHANGE_PROFILE(ServerOutChangeProfile.class),
  ENCRYPTION(ServerOutEncryption.class),
  END_GAME(ServerOutEndGame.class),
  FIND_FRIEND(ServerOutFindFriend.class),
  KEEP_ALIVE(ServerOutKeepAlive.class),
  LOAD_FRIENDS(ServerOutLoadFriends.class),
  LOGIN(ServerOutLogin.class),
  REGISTER(ServerOutRegister.class),
  START_GAME(ServerOutStartGame.class),
  UPDATE_GAME(ServerOutUpdateGame.class);

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
