package org.puzzlebattle.client.games.fourinarow;

import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.stage.Stage;

import org.puzzlebattle.client.screen.AbstractScreen;

import java.util.ArrayList;
import java.util.Timer;

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
    pane.setBackground(new Background(new BackgroundFill(game.getFourInARowGameSettings().getBackgroundColor(), null, null)));
}

  @Override
  public void onClose() {
    lang.msg("end.finished", "name", "Puzzle Battle Client", "version", "1.0");
  }

  public void registerEvents(Scene scene) {
    scene.setOnKeyPressed((e) -> this.onKeyEvent(e.getCode()));
  //  scene.setOnKeyReleased((e) -> this.onKeyEvent(e.getCode(), false));
  }

  public Coin createCoin(FourInARowPoint fourInARowPoint)
  {
        double initialPositionX = ((PanelGrid) pane).calculatePositionForCoin(fourInARowPoint.getNumberOfColumn());
        double thicknessOfRows = ((PanelGrid) pane).getThicknessOfRows();
        double ballSize= ((PanelGrid) pane).getDistanceOfColumns();
        Coin newCoin = new Coin(initialPositionX,fourInARowPoint.getColorOfPlayerForCoin(),(ballSize+thicknessOfRows)*fourInARowPoint.getCoinsInColumnBelow());
        coins.add(newCoin);
        pane.getChildren().add(newCoin);

        return newCoin;
  }

  public void onKeyEvent(KeyCode key) {
      FourInARowPoint fourInARowPoint = null;
      fourInARowPoint= game.questionForMove(key);

      if(fourInARowPoint!=null) {
        Coin newCoin;
        newCoin = createCoin(fourInARowPoint);
        renderCoinFall(newCoin);
      }

  }

  public void renderCoinFall(Coin newCoin) {

    ((PanelGrid) pane ).repaintGrid();
    //running timer task as daemon thread
    Timer timer = new Timer(true);
    timer.scheduleAtFixedRate(new CoinFall(50,400-newCoin.getMaximumHeighForFall(),this,newCoin), 0, 100);

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
  }
}

