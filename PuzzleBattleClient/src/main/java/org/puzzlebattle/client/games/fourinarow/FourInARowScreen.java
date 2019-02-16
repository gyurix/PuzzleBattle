package org.puzzlebattle.client.games.fourinarow;

import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.puzzlebattle.client.games.fourinarow.FourInARowGame;
import org.puzzlebattle.client.screen.AbstractScreen;

import static org.puzzlebattle.client.ClientLauncher.lang;

public class FourInARowScreen extends AbstractScreen {

  private FourInARowGame game;
  private Scene scene;
  //private PanelGrid panGrid;
  private Point2D mapSize = new Point2D(400, 400);

  public FourInARowScreen(Stage stage, FourInARowGame game) {
    super(stage);
    this.game= game;
    pane = new PanelGrid(0,0,mapSize.getX(),mapSize.getY());
}

  @Override
  public void onClose() {
    lang.msg("end.finished", "name", "Puzzle Battle Client", "version", "1.0");
  }


}

