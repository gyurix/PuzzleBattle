package org.puzzlebattle.client.games.fourinarow;

import javafx.scene.input.KeyCode;
import lombok.AllArgsConstructor;
import lombok.Data;

import static javafx.scene.input.KeyCode.*;

@AllArgsConstructor
@Data
public class FourInARowGameSettings {

  private KeyCode digit0 = DIGIT0;
  private KeyCode digit1 = DIGIT1;
  private KeyCode digit2 = DIGIT2;
  private KeyCode digit3 = DIGIT3;
  private KeyCode digit4 = DIGIT4;
  private KeyCode digit5 = DIGIT5;
  private KeyCode digit6 = DIGIT6;
  private KeyCode digit7 = DIGIT7;
  private KeyCode digit8 = DIGIT8;
  private KeyCode digit9 = DIGIT9;
  private int maxColumns =7;
  private int maxRows = 5;

  public FourInARowGameSettings(){


  }
}
