package org.puzzlebattle.client.games;


import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.puzzlebattle.client.protocol.Client;
import org.puzzlebattle.client.protocol.packets.out.ServerOutStartGame;
import org.puzzlebattle.client.screen.AbstractScreen;
import org.puzzlebattle.client.screen.MainScreen;
import org.puzzlebattle.client.screen.SettingsForScreens;
import org.puzzlebattle.core.entity.GameType;
import org.puzzlebattle.core.entity.GameWinner;

/**
 * Winning dialog for Four in a row game
 *
 * @author (Jakub Perdek)
 * @version (1.0)
 */
public class EndDialog extends AbstractScreen {
  private Button closeButton, returnToMenu, newGame;
  private Font f;
  private GameType gameType;
  private Image image;
  private ImageView img1;
  private VBox verticalBox;
  private Label winner;

  /**
   * Creates and shows winning dialog for lucky player
   *
   * @param stage - primary stage
   */
  public EndDialog(Stage stage, Client client, GameType gameType, GameWinner winner) {
    super(stage, client);
    this.gameType = gameType;
    prepareLayouts(winner);
    applySettingsToStage(winner);
  }

  /**
   * Applies settings on stage as close request, player name is shown in title
   */
  private void applySettingsToStage(GameWinner type) {
    switch (type) {
      case P1:
        stage.setTitle("Winner");
        break;
      case P2:
        stage.setTitle("Draw");
        break;
      case DRAW:
        stage.setTitle("Loser");
        break;
    }
    stage.setOnCloseRequest(e -> onClose());
    stage.sizeToScene();
  }

  /**
   * Creates and prepares buttons for Four in a row screen
   */
  private void createAndPrepareButtons() {
    newGame = new Button("New game");
    returnToMenu = new Button("Return to menu");
    closeButton = new Button("Close the game");
    newGame.setMaxWidth(Double.MAX_VALUE);
    returnToMenu.setMaxWidth(Double.MAX_VALUE);
    closeButton.setMaxWidth(Double.MAX_VALUE);

    closeButton.setOnAction(e -> onClose());
    returnToMenu.setOnAction(e -> createMainMenu());
    newGame.setOnAction(e -> startNewGame());
  }

  /**
   * Creates and prepares label
   */
  private void createAndPrepareLabel(String text) {
    winner = new Label(text);
    winner.setMaxWidth(Double.MAX_VALUE);
    f = new Font("Arial", 55);
    winner.setFont(f);
  }

  /**
   * Creates main menu
   */
  private void createMainMenu() {
    stage.close();
    new MainScreen(stage, new SettingsForScreens(), client).show();
  }

  /**
   * Returns height of winning dialog
   *
   * @return winning dialog height
   */
  public double getHeight() {
    return 520;
  }

  /**
   * Returns width of winning dialog
   *
   * @return winning dialog width
   */
  public double getWidth() {
    return 620;
  }

  /**
   * Action on close, whole application will be closed
   */
  public void onClose() {
    Platform.exit();
  }

  /**
   * Prepare layouts of Four in a row screen
   *
   * @param type - Type of the end screen
   */
  private void prepareLayouts(GameWinner type) {
    pane = new BorderPane();
    pane.setMaxSize(getWidth(), getHeight());
    verticalBox = new VBox(10);
    createAndPrepareButtons();
    verticalBox.getChildren().addAll(newGame, returnToMenu, closeButton);
    ((BorderPane) pane).setRight(verticalBox);
    switch (type) {
      case P1:
        tryToAddImage("pictures/oldChap.png");
        createAndPrepareLabel("Winner");
        break;
      case P2:
        tryToAddImage("pictures/lose.jpg");
        createAndPrepareLabel("Loser");
        break;
      case DRAW:
        tryToAddImage("pictures/draw.jpg");
        createAndPrepareLabel("Draw");
        break;
    }
    ((BorderPane) pane).setTop(winner);
  }

  /**
   * Starts a new game
   */
  protected void startNewGame() {
    new ServerOutStartGame(gameType);
  }

  /**
   * Adds a picture of winner in winning dialog screen
   */
  private void tryToAddImage(String imgUrl) {
    image = new Image(imgUrl);
    img1 = new ImageView();
    img1.setImage(image);
    ((BorderPane) pane).setLeft(img1);
  }
}
