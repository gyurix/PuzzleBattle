package org.puzzlebattle.client.games.fourinarow;

import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import org.puzzlebattle.client.screen.AbstractScreen;

import java.util.ArrayList;

import static org.puzzlebattle.client.ClientLauncher.lang;

public class FourInARowScreen extends AbstractScreen {

  private FourInARowGame game;
  private Scene scene;
  private ArrayList<Coin> coins = new ArrayList<Coin>();


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

  public void registerEvents(Scene scene) {
    scene.setOnKeyPressed((e) -> this.onKeyEvent(e.getCode(), true));
  //  scene.setOnKeyReleased((e) -> this.onKeyEvent(e.getCode(), false));
  }

  public Coin createCoin(FourInARowPoint fourInARowPoint)
  {
        double initialPositionX = ((PanelGrid) pane).calculatePositionForCoin(fourInARowPoint.getNumberOfColumn());
        Coin newCoin = new Coin(initialPositionX,fourInARowPoint.getColorOfPlayerForCoin());
        coins.add(newCoin);
        pane.getChildren().add(newCoin);

        return newCoin;
  }

  public void onKeyEvent(KeyCode key, boolean pressed) {
      FourInARowPoint fourInARowPoint = null;
      fourInARowPoint= game.questionForMove(key,pressed);

      if(fourInARowPoint!=null) {
        Coin newCoin;
        newCoin = createCoin(fourInARowPoint);
        renderCoinFall(newCoin);
      }

  }

  public void renderCoinFall(Coin newCoin) {
    System.out.println("Render");
  }

  public void repaintCoins() {

    Coin coin;

    for(int i=0;i<coins.size();i++) {
      coin = coins.get(i);
      coin.relocate(coin.getCenterX(),coin.getCenterY());
    }
  }


  public void repaint(double X, double Y, Coin coin)
  {
    coin.relocate(X,Y);
    repaintCoins();
    ((PanelGrid) pane ).repaintGrid();

  }
}

