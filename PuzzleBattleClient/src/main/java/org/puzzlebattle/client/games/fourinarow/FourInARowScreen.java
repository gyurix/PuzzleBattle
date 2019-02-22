package org.puzzlebattle.client.games.fourinarow;

import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.puzzlebattle.client.screen.AbstractScreen;

import java.util.ArrayList;
import java.util.Timer;

import static org.puzzlebattle.client.ClientLauncher.lang;


/**
 * Screen for Four in a row game
 *
 * @author (Jakub Perdek)
 * @version (1.0)
 */

public class FourInARowScreen extends AbstractScreen {

  private ArrayList<Coin> coins = new ArrayList<Coin>();
  private FourInARowGame game;
  //private PanelGrid panGrid;
  private Point2D mapSize = new Point2D(400, 400);
  private Scene scene;


  /**
   * Constructor for objects of class SkladPonuka
   */

  public FourInARowScreen(Stage stage, FourInARowGame game) {
    super(stage);
    this.game = game;
    pane = new PanelGrid(0, 0, mapSize.getX(), mapSize.getY(), this);
    pane.setBackground(new Background(new BackgroundFill(game.getFourInARowGameSettings().getBackgroundColor(), null, null)));
  }

  /**
   * Coin is created here. First necessary values are obtained from fourInARowPoint to specify coin.
   * After creation of coin is added to pane.
   *
   * @param fourInARowPoint necessary information for creating coin
   * @return created coin
   */

  public Coin createCoin(FourInARowPoint fourInARowPoint) {
    double initialPositionX = ((PanelGrid) pane).calculatePositionForCoin(fourInARowPoint.getNumberOfColumn());
    double thicknessOfRows = ((PanelGrid) pane).getThicknessOfRows();
    double ballSize = ((PanelGrid) pane).getDistanceOfColumns();
    Coin newCoin = new Coin(initialPositionX, fourInARowPoint.getColorOfPlayerForCoin(), (ballSize + thicknessOfRows) * fourInARowPoint.getCoinsInColumnBelow());
    coins.add(newCoin);
    pane.getChildren().add(newCoin);

    return newCoin;
  }

  /**
   * Method  which is called on the close
   */

  @Override
  public void onClose() {
    lang.msg("end.finished", "name", "Puzzle Battle Client", "version", "1.0");
  }

  /**
   * Method for registering events. Key listeners can be applied after pressing certain keys, but in other methods.
   *
   * @param scene scene where events should be registered
   */

  public void registerEvents(Scene scene) {
    scene.setOnKeyPressed((e) -> this.onKeyEvent(e.getCode()));
  }

  /**
   * If instance of fourInARowPoint is created, is not null, then coin can be created.
   * After calling special method, which creates coin, coin fall can be rendered.
   *
   * @param key key which is pressed
   */

  public void onKeyEvent(KeyCode key) {
    FourInARowPoint fourInARowPoint = null;
    fourInARowPoint = game.questionForMove(key);

    if (fourInARowPoint != null) {
      Coin newCoin;
      newCoin = createCoin(fourInARowPoint);
      renderCoinFall(newCoin);
      fourInARowPoint.isWinner(this);
    }

  }


  /**
   * Rendering of coin fall is provided here. At first all components are repainted and set to a front.
   * Then in background coin fall is simulated.
   *
   * @param newCoin new coin, which will be falling from certain position
   */

  public void renderCoinFall(Coin newCoin) {

    ((PanelGrid) pane).repaintGrid();
    //running timer task as daemon thread
    Timer timer = new Timer(true);
    timer.scheduleAtFixedRate(new CoinFall(50, 400 - newCoin.getMaximumHeighForFall(), this, newCoin), 0, 85);

  }

  /**
   * Relocation of the coin. Used to render coin fall.
   *
   * @param X    new X coordinate for coin
   * @param Y    new Y coordinate for coin
   * @param coin falling coin, which must be relocated while falling
   */

  public void repaint(double X, double Y, Coin coin) {
    coin.relocate(X, Y);
  }

  /**
   * All created coins are relocated, if their position has been changed.
   */

  public void repaintCoins() {

    Coin coin;

    for (int i = 0; i < coins.size(); i++) {
      coin = coins.get(i);
      coin.relocate(coin.getCenterX(), coin.getCenterY());
    }
  }

  public void showDialogForNextMove() {
    Alert informationAlert = new Alert(Alert.AlertType.CONFIRMATION);
    informationAlert.setTitle("Next player on the move");
    informationAlert.setContentText("Next player is on the move");
    informationAlert.setX(super.getWidth() - 100);
    informationAlert.setY(super.getHeight() - 100);
    informationAlert.showAndWait();
  }

  public void showWinnerScreen(FourInARowPlayer playerOnTheMove) {
    WinningDialog winningDialog = new WinningDialog(this, playerOnTheMove);
    winningDialog.initModality(Modality.WINDOW_MODAL);
    winningDialog.initOwner(super.getStage());
    winningDialog.show();
  }
}

