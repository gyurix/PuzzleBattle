package org.puzzlebattle.client.games.fourinarow;

import javafx.scene.input.KeyCode;
import lombok.AllArgsConstructor;
import lombok.Data;

import static javafx.scene.input.KeyCode.*;


/**
 * Settings for four in a row game are stored here.
 * Class contains KeyCodes for digits and numpads, preffered background color and number of columns and rows in the game.
 *
 * @author (Jakub Perdek)
 * @version (1.0)
 */
@AllArgsConstructor
@Data
public class FourInARowClientSettings {
  private static KeyCode digit[] = new KeyCode[]{DIGIT0, DIGIT1, DIGIT2, DIGIT3, DIGIT4, DIGIT5, DIGIT6, DIGIT7, DIGIT8, DIGIT9};
  private static KeyCode numpad[] = new KeyCode[]{NUMPAD0, NUMPAD1, NUMPAD2, NUMPAD3, NUMPAD4, NUMPAD5, NUMPAD6, NUMPAD7, NUMPAD8, NUMPAD9};
  private String background;
  private String colColor;
  private String enemy;
  private int fontSize;
  private int initialSpace;
  private String rowColor;
  private int spaceFromTop;
  private int thicknessOfColumns;
  private int thicknessOfRows;
  private String your;

  /**
   * Empty constructor for applying Four in a row game settings
   */
  public FourInARowClientSettings() {
  }

  /**
   * Method which stores KeyCode representation of digits, which can be pressed on keyboard.
   *
   * @param x number in sequence of selected digit
   * @return KeyCode of selected digit, representation of digit key which is pressed
   */
  public static KeyCode getDigit(int x) {
    return (x < 10) ? digit[x] : null;
  }

  /**
   * Method which stores KeyCode representation of numpads, which can be pressed on keyboard.
   *
   * @param x number in sequence of selected numpad
   * @return KeyCode of selected numpad, representation of numpad key which is pressed
   */
  public static KeyCode getNumpad(int x) {
    return (x < 10) ? numpad[x] : null;
  }

}
