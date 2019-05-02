package org.puzzlebattle.client.config;

import lombok.Data;

/**
 * Class with settings for screens
 *
 * @author Jakub Perdek
 * @version 1.0
 */
@Data
public class SettingsForScreens {

  private int ballBouncerPictureHeight;
  private int ballBouncerPictureWidth;
  private String colorBallBouncerLabel;
  private String colorFourInARowLabel;
  private int fourInARowPictureHeight;
  private int fourInARowPictureWidth;
  private int sizeOfTextBallBouncer;
  private int sizeOfTextFourInARow;
  private int spacingForVBox;
  private String typeCharBallBouncer;
  private String typeCharFourInARow;

  /**
   * Should only be instantiated during deserialization
   */
  private SettingsForScreens() {
  }
}
