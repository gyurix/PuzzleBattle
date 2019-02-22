package org.puzzlebattle.client.games.fourinarow;

import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.puzzlebattle.client.screen.AbstractScreen;

import java.util.ArrayList;

import static org.puzzlebattle.client.ClientLauncher.lang;


/**
 * Screen for Four in a row game
 *
 * @author (Jakub Perdek)
 * @version (1.0)
 */

public class FourInARowScreen extends AbstractScreen {

  private double coinRadius;
  private ArrayList<Coin> coins = new ArrayList<Coin>();
  private FourInARowGame game;
  private Point2D mapSize = new Point2D(400, 400);


  /**
   * Constructor for objects of class SkladPonuka
   */

  public FourInARowScreen(Stage stage, FourInARowGame game) {
    super(stage);
    this.game = game;
    pane = new PanelGrid(0, 0, mapSize.getX(), mapSize.getY(), this);
    pane.setBackground(new Background(new BackgroundFill(game.getSettings().getBackgroundColor(), null, null)));
    coinRadius = getPane().getDistanceOfColumns() * 0.4f;
  }


  /**
   * Coin is created here. First necessary values are obtained from fourInARowPoint to specify coin.
   * After creation of coin is added to pane.
   *
   * @param point necessary information for creating coin
   * @return created coin
   */
  public Coin createCoin(FourInARowPoint point) {
    int column = point.getNumberOfColumn();
    double x = getPane().getColumnX(column);
    Coin newCoin = new Coin(x, coinRadius, coinRadius, point.getColorOfPlayerForCoin());
    coins.add(newCoin);
    pane.getChildren().add(newCoin);
    return newCoin;
  }

  @Override
  public PanelGrid getPane() {
    return (PanelGrid) pane;
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
    FourInARowPoint point = game.questionForMove(key);
    if (point != null) {
      Coin coin = createCoin(point);
      startCoinFall(coin, mapSize.getY() - (point.getCoinsInColumnBelow()) * (getPane().getThicknessOfRows() + getPane().getDistanceOfColumns()) + getPane().getThicknessOfRows());
      point.isWinner(this);
    }
  }

  public void showDialogForNextMove() {
    System.out.println("Next player on the move");
  }

  public void showWinnerScreen(FourInARowPlayer playerOnTheMove) {
    WinningDialog winningDialog = new WinningDialog(this, playerOnTheMove);
    winningDialog.initModality(Modality.WINDOW_MODAL);
    winningDialog.initOwner(super.getStage());
    winningDialog.show();
  }

  /**
   * Rendering of coin fall is provided here. At first all components are repainted and set to a front.
   * Then in background coin fall is simulated.
   *
   * @param coin - The coin which should be new coin, which will be falling from certain position
   */

  public void startCoinFall(Coin coin, double to) {
    new CoinFall(this, coin, to, 5);
  }
}

