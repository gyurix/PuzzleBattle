package org.puzzlebattle.client.games.fourinarow;

import javafx.stage.Stage;
import org.puzzlebattle.client.games.fourinarow.FourInARowGame;
import org.puzzlebattle.client.screen.AbstractScreen;

import static org.puzzlebattle.client.ClientLauncher.lang;

public class FourInARowScreen extends AbstractScreen {

  private FourInARowGame game;

  public FourInARowScreen(Stage stage, FourInARowGame game) {
    super(stage);
    this.game= game;

  }

  @Override
  public void onClose() {
    lang.msg("end.finished", "name", "Puzzle Battle Client", "version", "1.0");
  }

}

