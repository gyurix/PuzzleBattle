package org.puzzlebattle.server;

import lombok.Getter;
import org.puzzlebattle.core.entity.AddressInfo;
import org.puzzlebattle.core.gamesettings.GameProfiles;

@Getter
public class ServerConfig {
  private AddressInfo address;
  private GameProfiles gameProfiles;
}
