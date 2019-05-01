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
import org.puzzlebattle.client.games.UserPuzzleBattle;
import org.puzzlebattle.client.protocol.Client;
import org.puzzlebattle.client.protocol.packets.out.endGame.ServerOutEndFourInARow;
import org.puzzlebattle.client.screen.MainScreen;
import org.puzzlebattle.client.screen.SettingsForScreens;

/**
 * Winning dialof for Four in a row game
 *
 * @author (Jakub Perdek)
 * @version (1.0)
 */
public class WinningDialog extends Stage {

  private BorderPane border;
  private Button closeButton, returnToMenu, newGame;
  private Font f;
  private Image image;
  private ImageView img1;
  private Stage primaryStage;
  private Scene scene;
  private UserPuzzleBattle user;
  private VBox verticalBox;
  private Label winner;
  private Client client;

  /**
   * Creates and shows winning dialog for lucky player
   *
   * @param fourInARowScreen -four in a row screen
   * @param winningPlayer    -winner of the game
   * @param primaryStage     - primary stage
   * @param user             - user of Puzzle Battle
   */
  public WinningDialog(FourInARowScreen fourInARowScreen, FourInARowPlayer winningPlayer,
                       Stage primaryStage, UserPuzzleBattle user, Client client) {
    this.client = client;
    this.primaryStage = primaryStage;
    this.user = user;
    ServerOutEndFourInARow endFourInARow = new ServerOutEndFourInARow(winningPlayer.getPlayingNumber(),
            user.getUserName(), user.getPassword(), null);
    fourInARowScreen.getClient().sendPacket(endFourInARow);
    prepareLayouts(fourInARowScreen);
    scene = new Scene(border, fourInARowScreen.getWidth(), fourInARowScreen.getHeight());
    applySettingsToStage(winningPlayer);
  }

  /**
   * Applies settings on stage as close request, player name is shown in title
   *
   * @param winningPlayer - winner of Four in a row game
   */
  private void applySettingsToStage(FourInARowPlayer winningPlayer) {
    int playerNumber = winningPlayer.getPlayingNumber();
    this.setTitle("Player number " + playerNumber + " is winner.");
    this.setScene(scene);
    this.setOnCloseRequest(e -> onClose());
    this.sizeToScene();
  }

  /**
   * Creates and prepares buttons for Four in a row screen
   *
   * @param fourInARowScreen - screen of Four in a row game
   */
  private void createAndPrepareButtons(FourInARowScreen fourInARowScreen) {
    newGame = new Button("New game");
    returnToMenu = new Button("Return to menu");
    closeButton = new Button("Close the game");
    newGame.setMaxWidth(Double.MAX_VALUE);
    returnToMenu.setMaxWidth(Double.MAX_VALUE);
    closeButton.setMaxWidth(Double.MAX_VALUE);

    closeButton.setOnAction(e -> onClose());
    returnToMenu.setOnAction(e -> createMainMenu());
    newGame.setOnAction(e -> startNewGame(fourInARowScreen));
  }

  /**
   * Creates and prepares label winner
   */
  private void createAndPrepareLabelWinner() {
    winner = new Label("Winner");
    winner.setMaxWidth(Double.MAX_VALUE);
    f = new Font("Arial", 55);
    winner.setFont(f);
  }

  /**
   * Creates main menu
   */
  private void createMainMenu() {
    this.close();
    primaryStage.close();
    new MainScreen(new Stage(), new SettingsForScreens(), user, client).show();
  }

  /**
   * Returns height of winning dialog
   *
   * @return winning dialog height
   */
  public double getWinningDialogHeight() {
    return 400;
  }

  /**
   * Returns width of winning dialog
   *
   * @return winning dialog width
   */
  public double getWinningDialogWidth() {
    return 250;
  }

  /**
   * Action on close, whole application will be closed
   */
  private void onClose() {
    this.close();
    Platform.exit();
  }

  /**
   * Prepare layouts of Four in a row screen
   *
   * @param fourInARowScreen screen of Four in a row game
   */
  private void prepareLayouts(FourInARowScreen fourInARowScreen) {
    border = new BorderPane();
    border.setMaxSize(fourInARowScreen.getWidth(), fourInARowScreen.getHeight());
    verticalBox = new VBox(10);
    createAndPrepareButtons(fourInARowScreen);
    verticalBox.getChildren().addAll(newGame, returnToMenu, closeButton);
    border.setRight(verticalBox);
    tryToAddImage();
    createAndPrepareLabelWinner();
    border.setTop(winner);
  }

  /**
   * Starts a new game
   *
   * @param fourInARowScreen - screen of Four in a row game
   */
  private void startNewGame(FourInARowScreen fourInARowScreen) {
    this.close();
    fourInARowScreen.getStage().close();
    //GET FOUR IN A ROW GAME SETTINGS
    //GameTable newGameTable = GameTable.createTheSameGameFromOlderGame(gameTable, new FourInARowGameSettings());
    new FourInARowScreen(fourInARowScreen.getStage(),
            new FourInARowGame(null, new FourInARowGameSettings()),
            user, client).show();
  }

  /**
   * Adds a picture of winner in winning dialog screen
   */
  private void tryToAddImage() {
    String imageURL = "pictures/oldChap.png";
    image = new Image(imageURL);
    img1 = new ImageView();
    img1.setImage(image);
    border.setLeft(img1);
  }
}
