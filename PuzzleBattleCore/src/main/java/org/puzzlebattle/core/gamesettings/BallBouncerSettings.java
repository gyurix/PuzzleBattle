package org.puzzlebattle.core.gamesettings;

import lombok.Getter;

@Getter
public class BallBouncerSettings extends MainSettings {
  private double bouncerHeight;
  private double bouncerWidth;
  private int mapMaxx;
  private int mapMaxy;
  private double maxBallRadius;
  private int maxGoal;
  private double minBallRadius;
  private double movementIntensity;
}
