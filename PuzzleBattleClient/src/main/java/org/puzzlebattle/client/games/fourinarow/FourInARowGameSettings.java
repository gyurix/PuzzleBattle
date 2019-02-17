package org.puzzlebattle.client.games.fourinarow;

import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import lombok.AllArgsConstructor;
import lombok.Data;

import static javafx.scene.input.KeyCode.*;

@AllArgsConstructor
@Data
public class FourInARowGameSettings {

  private static KeyCode digit[] = new KeyCode[]{DIGIT0,DIGIT1,DIGIT2,DIGIT3,DIGIT4,DIGIT5,DIGIT6,DIGIT7,DIGIT8,DIGIT9};
  private static KeyCode numpad[] = new KeyCode[]{NUMPAD0,NUMPAD1,NUMPAD2,NUMPAD3,NUMPAD4,NUMPAD5,NUMPAD6,NUMPAD7,NUMPAD8,NUMPAD9};

  private Color backgroundColor = Color.GAINSBORO;

  private int maxColumns =7;
  private int maxRows = 5;

  public FourInARowGameSettings(){
  }

  public static KeyCode getDigit(int x) {
    return (x<10)? digit[x] : null;
  }

  public static KeyCode getNumpad(int x) {
    return (x<10)? numpad[x] : null;
  }
}
