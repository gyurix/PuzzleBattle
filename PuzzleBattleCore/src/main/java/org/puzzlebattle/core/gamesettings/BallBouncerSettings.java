package org.puzzlebattle.core.gamesettings;

import lombok.Getter;

@Getter
public class BallBouncerSettings extends MainSettings {
  private double bouncerHeight = 15;
  private double bouncerWidth = 100;
  private int mapMaxx = 720;
  private int mapMaxy = 720;
  private double maxBallRadius = 35;
  private int maxGoal = 5;
  private double minBallRadius = 10;
  private double movementIntensity = 4.5;
}
