package org.puzzlebattle.client.games.fourinarow;

import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.stage.Stage;
import lombok.Getter;
import org.puzzlebattle.client.protocol.Client;
import org.puzzlebattle.client.protocol.packets.out.ServerOutEndGame;
import org.puzzlebattle.client.screen.AbstractScreen;

import java.util.ArrayList;

import static org.puzzlebattle.core.utils.LangFile.lang;


/**
 * Screen for Four in a row game
 *
 * @author (Jakub Perdek)
 * @version (1.0)
 */
public class FourInARowScreen extends AbstractScreen {

  @Getter
  private static FourInARowScreen instance;
  double coinRadius;
  ArrayList<Coin> coins = new ArrayList<Coin>();
  Point2D mapSize = new Point2D(400, 400);
  @Getter
  private FourInARowGame game;

  /**
   * Constructor for objects of class Four In A Row screen
   */
  public FourInARowScreen(Stage stage, FourInARowGame game, Client client) {
    super(stage, client);
    this.game = game;
    instance = this;
    pane = new PanelGrid(0, 0, mapSize.getX(), mapSize.getY(), this);
    pane.setBackground(new Background(new BackgroundFill(game.getClientSettings().getBackgroundColor(), null, null)));
    coinRadius = getPane().getDistanceOfColumns() * 0.4f;
  }

  /**
   * Method  which returs used pane
   *
   * @return pane
   */
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
    if (key == KeyCode.ESCAPE) {
      ServerOutEndGame endGame = new ServerOutEndGame();
      client.sendPacket(endGame);
      return;
    }
    game.questionForMove(key);
  }
}

