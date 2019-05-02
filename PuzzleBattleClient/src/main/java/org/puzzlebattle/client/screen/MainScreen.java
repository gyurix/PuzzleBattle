package org.puzzlebattle.client.screen;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.puzzlebattle.client.config.ConfigManager;
import org.puzzlebattle.client.games.User;
import org.puzzlebattle.client.protocol.Client;
import org.puzzlebattle.client.protocol.packets.out.ServerOutStartGame;
import org.puzzlebattle.client.protocol.packets.out.ServerOutUserInfoRequest;
import org.puzzlebattle.core.entity.GameType;
import org.puzzlebattle.core.utils.LangFile;
import org.puzzlebattle.core.utils.Logging;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Main screen in the program. Probably games can be chosen here.
 *
 * @author (Jakub Perdek, Juraj Barath)
 * @version (1.0)
 */
public class MainScreen extends AbstractScreen {
  private Font fBallBouncer, fFourInARow;
  private FriendshipMenu friendshipMenu;
  private GridPane gridPane;
  private HBox hEscapeBox;
  private Image imageBallBouncer, imageFourInARow;
  private ImageView imageViewBallBouncer, imageViewFourInARow;
  private Label labelBallBouncer, labelFourInARow;
  private PlayerProfileScreen playerProfileScreen;
  private Button reLoginButton, friendshipMenuButton, closeMainScreen, startBallBouncerGame, startFourInARowGame;
  private Region regionFourInARow, regionBallBouncer;
  private Separator separator;
  private SettingsForScreens settingsForScreens;
  private VBox vBoxBallBouncerGame, vBoxFourInARowGame;
  private Button viewBestPlayersBallBouncer, viewBestPlayersFourInARow, viewProfileButton, editProfileButton;

  /**
   * Constructor which creates main screen in the program.
   */
  public MainScreen(Stage stage, SettingsForScreens settingsForScreens, Client client) {
    super(stage, client);
    this.settingsForScreens = settingsForScreens;

    prepareBallBouncerGameMenu();
    prepareFourInARowGameMenu();

    prepareEscapeButtons();
    prepareEscapeLayout();
    prepareSceneAndGridPane();
    Logging.logInfo("Main scene has been created.");
  }

  private Button createButton(String text) {
    Button button = new Button(text);
    button.setMaxWidth(Double.MAX_VALUE);
    return button;
  }

  private VBox createVBox(Node... elements) {
    VBox vBox = new VBox(settingsForScreens.getSpacingForVBox());
    vBox.setMaxWidth(Double.MAX_VALUE);
    vBox.setMaxHeight(Double.MAX_VALUE);
    vBox.getChildren().addAll(elements);
    return vBox;
  }

  /**
   * Prepare buttons for ball bouncer
   */
  private void prepareButtonsBallBouncer() {
    startBallBouncerGame = createButton(LangFile.lang.get("mainScreen.ballBouncer.launchBallBouncerGame"));
    startBallBouncerGame.setOnAction(e -> startBallBouncer());

    viewBestPlayersBallBouncer = createButton(LangFile.lang.get("mainScreen.ballBouncer.viewBestBallBouncerPlayers"));
    viewBestPlayersBallBouncer.setOnAction(e -> viewBestPlayers());
  }

  /**
   * Prepare buttons for Four in a row
   */
  private void prepareButtonsFourInARow() {
    startFourInARowGame = createButton(LangFile.lang.get("mainScreen.fourInARow.launchFourInARowGame"));
    startFourInARowGame.setOnAction(e -> startFourInARow());

    viewBestPlayersFourInARow = createButton(LangFile.lang.get("mainScreen.fourInARow.viewBestFourInARowPlayers"));
    viewBestPlayersFourInARow.setOnAction(e -> viewBestPlayers());
  }

  /**
   * Prepare ball bouncer game menu, with all components which are made of
   */
  private void prepareBallBouncerGameMenu() {
    prepareBallBouncerRegion();
    prepareLabelForBallBouncer();
    prepareButtonsBallBouncer();
    vBoxBallBouncerGame = createVBox(labelBallBouncer, regionBallBouncer, startBallBouncerGame, viewBestPlayersBallBouncer);
    prepareImageBallBouncer();
  }

  /**
   * Prepare ball bounce
   */
  private void prepareBallBouncerRegion() {
    regionBallBouncer = new Region();
    regionBallBouncer.setMinHeight(10);
  }

  /**
   * Launching Ball bouncer game
   */
  private void startBallBouncer() {
    ServerOutStartGame launchBallBouncer = new ServerOutStartGame(GameType.BOUNCER);
    client.sendPacket(launchBallBouncer);
  }

  /**
   * Launching Four in a row game
   */
  private void startFourInARow() {
    ServerOutStartGame launchPacket = new ServerOutStartGame(GameType.FOUR_IN_A_ROW);
    client.sendPacket(launchPacket);
  }

  /**
   * Preparing escape buttons, for example reLoginButton, closeMainScreen and viewProfileButton
   */
  private void prepareEscapeButtons() {
    reLoginButton = createButton(LangFile.lang.get("mainScreen.menu.reLogin"));
    reLoginButton.setOnAction(e -> reLogin());
    closeMainScreen = createButton(LangFile.lang.get("mainScreen.menu.close"));
    closeMainScreen.setOnAction(e -> stage.close());
    viewProfileButton = createButton(LangFile.lang.get("mainScreen.menu.viewProfile"));
    viewProfileButton.setOnAction(e -> prepareProfileScreen());
    friendshipMenuButton = createButton(LangFile.lang.get("mainScreen.menu.friendshipMenu"));
    friendshipMenuButton.setOnAction(e -> prepareFriendshipMenu());
    editProfileButton = createButton(LangFile.lang.get("mainScreen.menu.editProfile"));
    editProfileButton.setOnAction(e -> new AdditionalInformationScreen(new Stage(), client).show());
  }

  /**
   * Prepare escape layout using horizontal layout
   */
  private void prepareEscapeLayout() {
    hEscapeBox = new HBox(settingsForScreens.getSpacingForVBox());
    hEscapeBox.setMinWidth(super.getWidth());
    hEscapeBox.getChildren().addAll(reLoginButton, closeMainScreen, viewProfileButton, friendshipMenuButton, editProfileButton);
  }

  /**
   * Preparing all components of Four in a row menu
   */
  private void prepareFourInARowGameMenu() {
    prepareFourInARowRegion();
    prepareLabelForFourInARow();
    prepareButtonsFourInARow();
    vBoxFourInARowGame = createVBox(labelFourInARow, regionFourInARow, startFourInARowGame, viewBestPlayersFourInARow);
    prepareImageFourInARow();
  }

  /**
   * Prepare four in a row region
   */
  private void prepareFourInARowRegion() {
    regionFourInARow = new Region();
    regionFourInARow.setMinHeight(10);
  }

  /**
   * Prepares friendship menu
   */
  private void prepareFriendshipMenu() {
    friendshipMenu = new FriendshipMenu(new Stage(), client);
    friendshipMenu.show();
  }

  /**
   * Prepare image for ball bouncer
   */
  private void prepareImageBallBouncer() {
    String imageURL = "pictures/BallBouncer.png";
    imageBallBouncer = new Image(imageURL);
    imageViewBallBouncer = new ImageView();
    imageViewBallBouncer.setFitHeight(settingsForScreens.getBallBouncerPictureHeight());
    imageViewBallBouncer.setFitWidth(settingsForScreens.getBallBouncerPictureWidth());
    imageViewBallBouncer.setImage(imageBallBouncer);
    vBoxBallBouncerGame.getChildren().add(imageViewBallBouncer);
  }

  /**
   * Prepare image for four in a row game
   */
  private void prepareImageFourInARow() {
    String imageURL = "pictures/FourInARow1.png";
    imageFourInARow = new Image(imageURL);
    imageViewFourInARow = new ImageView();
    imageViewFourInARow.setFitHeight(settingsForScreens.getFourInARowPictureHeight());
    imageViewFourInARow.setFitWidth(settingsForScreens.getFourInARowPictureWidth());
    imageViewFourInARow.setImage(imageFourInARow);
    vBoxFourInARowGame.getChildren().add(imageViewFourInARow);
  }

  /**
   * Prepare label for ball bouncer
   */
  private void prepareLabelForBallBouncer() {
    labelBallBouncer = new Label(LangFile.lang.get("mainScreen.ballBouncer.ballBouncerLabel"));
    fBallBouncer = new Font(settingsForScreens.getTypeCharBallBouncer(), settingsForScreens.getSizeOfTextBallBouncer());
    labelBallBouncer.setTextFill(settingsForScreens.getColorBallBouncerLabel());
    labelBallBouncer.setWrapText(true);
    labelBallBouncer.setFont(fBallBouncer);
  }

  /**
   * Prepare label for four in a row
   */
  private void prepareLabelForFourInARow() {
    labelFourInARow = new Label(LangFile.lang.get("mainScreen.fourInARow.fourInARowLabel"));
    fFourInARow = new Font(settingsForScreens.getTypeCharFourInARow(), settingsForScreens.getSizeOfTextFourInARow());
    labelFourInARow.setTextFill(settingsForScreens.getColorFourInARowLabel());
    labelFourInARow.setWrapText(true);
    labelFourInARow.setFont(fFourInARow);
  }

  /**
   * Preparation of profile screen, additional information about user will be loaded from database
   */
  private void prepareProfileScreen() {
    client.sendPacket(new ServerOutUserInfoRequest());
  }

  /**
   * Prepare scene and grid panel for
   */
  private void prepareSceneAndGridPane() {
    gridPane = new GridPane();
    separator = new Separator();
    gridPane.setHgap(20);
    gridPane.setVgap(20);
    ColumnConstraints column1 = new ColumnConstraints();
    column1.setPercentWidth(50);
    gridPane.getColumnConstraints().addAll(column1);
    separator.setOrientation(Orientation.VERTICAL);
    gridPane.setPadding(new Insets(10, 20, 10, 20));
    gridPane.add(vBoxBallBouncerGame, 0, 1);
    gridPane.add(separator, 1, 1);
    gridPane.add(vBoxFourInARowGame, 2, 1);
    gridPane.setValignment(hEscapeBox, VPos.BOTTOM);
    gridPane.add(hEscapeBox, 0, 3);
    this.pane = gridPane;
  }

  /**
   * Re login for user, user will log off and main menu will be closed
   */
  private void reLogin() {
    getStage().close();
    new LoginScreen(getStage(), new LanguageSelector(getStage(), 100, 25),
            new Client(ConfigManager.getInstance().getConfig().getServer())).show();
  }

  /**
   * Update information for player profile screen
   *
   * @param playerProfile    player profile screen
   * @param userFromDatabase user loaded width additional information from database using his nickname and password
   */
  private void updateInformationPlayerProfileScreen(PlayerProfileScreen playerProfile, User userFromDatabase) {

    File imageFile = new File("PuzzleBattleClient/src/main/resources/pictures/avatar.bmp");
    try {
      FileOutputStream fos = new FileOutputStream(imageFile);
      if (userFromDatabase.getAvatar() != null)
        fos.write(userFromDatabase.getAvatar());
      fos.close();
    } catch (IOException i) {
      Logging.logWarning("Error while writing image", i);
    }

    playerProfile.setNickName(userFromDatabase.getUserName());
    playerProfile.setEmail(userFromDatabase.getEmail());
    if (userFromDatabase.getAvatar() != null) {
      playerProfile.setLoadedImage("pictures/avatar.bmp");
    } else {
      Logging.logFiner("no image is set, default will be applied");
      playerProfile.setLoadedImage("faces/face1.png");
    }

    if (userFromDatabase.getName() != null) {
      playerProfile.setLoadedName(userFromDatabase.getName());
    }

    if (userFromDatabase.getSurname() != null) {
      playerProfile.setLoadedSurname(userFromDatabase.getSurname());
    }

    if (userFromDatabase.getDateOfBirth() != null) {
      playerProfile.setLoadedDateOfBirth(userFromDatabase.getDateOfBirth());
    }

    if (userFromDatabase.getAge() != 0) {
      playerProfile.setLoadedAge("" + userFromDatabase.getAge());
    }
    Logging.logInfo("all available data have been added to profile from database");
  }

  /**
   * Method creates screen for best players and shows it for user
   */
  private void viewBestPlayers() {
    BestPlayersScreen bestPlayersScreen = new BestPlayersScreen(new Stage(), settingsForScreens, client);
    bestPlayersScreen.show();
  }
}
