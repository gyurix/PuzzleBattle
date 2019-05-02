package org.puzzlebattle.core.gamesettings;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class GameProfiles {
  private Map<String, BallBouncerSettings> ballBouncer = new HashMap<>();
  private Map<String, FourInARowSettings> fourInARow = new HashMap<>();
}
