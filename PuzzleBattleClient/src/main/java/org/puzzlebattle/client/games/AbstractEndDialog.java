package org.puzzlebattle.client.games;


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
import org.puzzlebattle.client.games.fourinarow.FourInARowGame;
import org.puzzlebattle.client.games.fourinarow.FourInARowGameSettings;
import org.puzzlebattle.client.games.fourinarow.FourInARowPlayer;
import org.puzzlebattle.client.games.fourinarow.FourInARowScreen;
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
public abstract class AbstractEndDialog extends Stage {

  private BorderPane border;
  private Client client;
  private Button closeButton, returnToMenu, newGame;
  private Font f;
  private Image image;
  private ImageView img1;
  private Stage primaryStage;
  private Scene scene;
  private User user;
  private VBox verticalBox;
  private Label winner;

  /**
   * Creates and shows winning dialog for lucky player
   *
   * @param screen - four in a row screen
   * @param primaryStage     - primary stage
   */
  public AbstractEndDialog(AbstractScreen screen,int winnerNumber,
                           Stage primaryStage, Client client,String type) {
    this.client = client;
    this.primaryStage = primaryStage;
    this.user = user;
    ServerOutEndGame endGame = new ServerOutEndGame();
    screen.getClient().sendPacket(endGame);

    prepareLayouts(screen,type);
    scene = new Scene(border, screen.getWidth(), screen.getHeight());
    applySettingsToStage(winnerNumber,type);
  }

  /**
   * Applies settings on stage as close request, player name is shown in title
   *
   */
  private void applySettingsToStage(int playerNumber, String type) {
    if(type.equals("winner")) {
      this.setTitle("Player number " + playerNumber + " is winner.");
    }
    else if(type.equals("draw")) {
      this.setTitle("Draw");
    }
    else if(type.equals("loser")){
      this.setTitle("Loser");
    }
    this.setScene(scene);
    this.setOnCloseRequest(e -> onClose());
    this.sizeToScene();
  }

  /**
   * Creates and prepares buttons for Four in a row screen
   *
   * @param abstractScreen - screen of Four in a row game
   */
  private void createAndPrepareButtons(AbstractScreen abstractScreen) {
    newGame = new Button("New game");
    returnToMenu = new Button("Return to menu");
    closeButton = new Button("Close the game");
    newGame.setMaxWidth(Double.MAX_VALUE);
    returnToMenu.setMaxWidth(Double.MAX_VALUE);
    closeButton.setMaxWidth(Double.MAX_VALUE);

    closeButton.setOnAction(e -> onClose());
    returnToMenu.setOnAction(e -> createMainMenu());
    newGame.setOnAction(e -> startNewGame(abstractScreen));
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
    this.close();
    primaryStage.close();
    new MainScreen(new Stage(), new SettingsForScreens(), client).show();
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
   * @parama abstractScreen screen of Four in a row game
   */
  private void prepareLayouts(AbstractScreen abstractScreen,String type) {
    border = new BorderPane();
    border.setMaxSize(abstractScreen.getWidth(), abstractScreen.getHeight());
    verticalBox = new VBox(10);
    createAndPrepareButtons(abstractScreen);
    verticalBox.getChildren().addAll(newGame, returnToMenu, closeButton);
    border.setRight(verticalBox);
    if(type.equals("winner")) {
      tryToAddImage("pictures/oldChap.png");
      createAndPrepareLabel("Winner");
    }
    else if(type.equals("loser")){
      tryToAddImage("pictures/lose.jpg");
      createAndPrepareLabel("Loser");
    }
    else if(type.equals("draw")){
      tryToAddImage("pictures/draw.jpg");
      createAndPrepareLabel("Draw");
    }
    border.setTop(winner);
  }

  /**
   * Starts a new game
   *
   * @param abstractScreen - screen of Four in a row game
   */
  protected abstract void startNewGame(AbstractScreen abstractScreen);

  /**
   * Adds a picture of winner in winning dialog screen
   */
  private void tryToAddImage(String cesta) {
    String imageURL = cesta;
    image = new Image(imageURL);
    img1 = new ImageView();
    img1.setImage(image);
    border.setLeft(img1);
  }
}
