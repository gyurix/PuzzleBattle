package org.puzzlebattle.client.games.fourinarow;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.puzzlebattle.client.games.AbstractEndDialog;
import org.puzzlebattle.client.games.User;
import org.puzzlebattle.client.protocol.Client;
import org.puzzlebattle.client.protocol.packets.out.ServerOutEndGame;
import org.puzzlebattle.client.screen.AbstractScreen;
import org.puzzlebattle.client.screen.MainScreen;
import org.puzzlebattle.client.screen.SettingsForScreens;

/**
 * Winning dialog for Four in a row game
 *
 * @author (Jakub Perdek)
 * @version (1.0)
 */
public class EndDialog extends AbstractEndDialog {

  private Client client;

  /**
   * Creates and shows winning dialog for lucky player
   *
   * @param fourInARowScreen - four in a row screen
   * @param winningPlayer    - winner of the game
   * @param primaryStage     - primary stage
   * @param client            - client
   * @param type              - type of dialog
   */
  public EndDialog(FourInARowScreen fourInARowScreen, FourInARowPlayer winningPlayer,
                       Stage primaryStage, Client client,String type) {
    super(fourInARowScreen,0,primaryStage,client,type);
    this.client = client;
  }

  /**
   * Starts a new game
   *
   * @param fourInARowScreen - screen of Four in a row game
   */
  protected void startNewGame(AbstractScreen fourInARowScreen) {
    this.close();
    fourInARowScreen.getStage().close();
    //GET FOUR IN A ROW GAME SETTINGS
    //GameTable newGameTable = GameTable.createTheSameGameFromOlderGame(gameTable, new FourInARowGameSettings());
    new FourInARowScreen(fourInARowScreen.getStage(),
            new FourInARowGame(null, new FourInARowGameSettings()), client).show();
  }

}
