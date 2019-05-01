package org.puzzlebattle.client.games.fourinarow;

import javafx.stage.Stage;
import org.puzzlebattle.client.games.AbstractEndDialog;
import org.puzzlebattle.client.protocol.Client;
import org.puzzlebattle.client.protocol.packets.out.ServerOutStartGame;
import org.puzzlebattle.client.screen.AbstractScreen;
import org.puzzlebattle.core.entity.GameType;

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
    new ServerOutStartGame(GameType.FOUR_IN_A_ROW);
  }

}
