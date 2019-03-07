package org.puzzlebattle.client.screen;

import javafx.scene.paint.Color;
import lombok.Data;

@Data
public class SettingsForScreens {

  private final int sizeOfTextBallBouncer = 45;
  private final int sizeOfTextFourInARow = 45;
  private int BallBouncerPictureHeight = 720 / 5;
  private int BallBouncerPictureWidth = 720 / 5;
  private int FourInARowPictureHeight = 474 / 3;
  private int FourInARowPictureWidth = 639 / 3;
  private Color colorBallBouncerLabel = Color.RED;
  private Color colorFourInARowLabel = Color.GREEN;
  private int spacingForVBox = 10;
  private String typeCharBallBouncer = "Arial";
  private String typeCharFourInARow = "Arial";

  public SettingsForScreens() {

  }
}
