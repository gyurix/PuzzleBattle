package org.puzzlebattle.client.games.bouncer;


import javafx.stage.Stage;
import org.puzzlebattle.client.games.AbstractEndDialog;
import org.puzzlebattle.client.protocol.Client;
import org.puzzlebattle.client.screen.AbstractScreen;

/**
 * Winning dialog for Four in a row game
 *
 * @author (Jakub Perdek)
 * @version (1.0)
 */
public class EndDialogBouncer extends AbstractEndDialog {

  private Client client;

  /**
   * Creates and shows winning dialog for lucky player
   *
   * @param ballBouncerScreen - four in a row screen
   * @param winningPlayer     - winner of the game
   * @param primaryStage      - primary stage
   * @param client            - client
   * @param type              - type of dialog
   */
  public EndDialogBouncer(BallBouncerScreen ballBouncerScreen, BouncerPlayer winningPlayer,
                          Stage primaryStage, Client client, String type) {
    super(ballBouncerScreen, 0, primaryStage, client, type);
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
    new BallBouncerScreen(fourInARowScreen.getStage(),
            new BouncerGame(new BouncerGameSettings()),
            client).show();
  }

}

