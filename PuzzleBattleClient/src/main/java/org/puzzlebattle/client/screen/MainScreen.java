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
import org.puzzlebattle.client.games.UserPuzzleBattle;
import org.puzzlebattle.client.games.bouncer.BallBouncerScreen;
import org.puzzlebattle.client.games.bouncer.BouncerGame;
import org.puzzlebattle.client.games.bouncer.BouncerGameSettings;
import org.puzzlebattle.client.games.fourinarow.FourInARowGame;
import org.puzzlebattle.client.games.fourinarow.FourInARowGameSettings;
import org.puzzlebattle.client.games.fourinarow.FourInARowScreen;
import org.puzzlebattle.client.protocol.Server;
import org.puzzlebattle.client.protocol.packets.in.ServerInChangeProfile;
import org.puzzlebattle.client.protocol.packets.out.ServerOutLogin;
import org.puzzlebattle.client.protocol.packets.out.startGame.ServerOutLaunchBallBouncer;
import org.puzzlebattle.client.protocol.packets.out.startGame.ServerOutLaunchFourInARow;
import org.puzzlebattle.core.utils.Logging;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static org.puzzlebattle.core.utils.LangFile.lang;

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
  private UserPuzzleBattle user;
  private VBox vBoxBallBouncerGame, vBoxFourInARowGame;
  private Button viewBestPlayersBallBouncer, viewBestPlayersFourInARow, viewProfileButton;

  /**
   * Constructor which creates main screen in the program.
   */
  public MainScreen(Stage stage, SettingsForScreens settingsForScreens, UserPuzzleBattle user) {
    super(stage);
    this.settingsForScreens = settingsForScreens;
    this.user = user;

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
   * Launching Ball bouncer game
   */
  private void launchBallBouncer() {

    ServerOutLaunchBallBouncer launchBallBouncer = new ServerOutLaunchBallBouncer(user.getUserName(),user.getPassword());
    new Server().sendPacket(launchBallBouncer);
    //addBallBouncerGameToDatabase(user, true);
    super.getStage().close();
    new BallBouncerScreen(new Stage(), new BouncerGame(null, new BouncerGameSettings())).show();
  }

  /**
   * Launching Four in a row game
   */
  private void launchFourInARow() {
    ServerOutLaunchFourInARow launchBallBouncer = new ServerOutLaunchFourInARow(user.getUserName(),user.getPassword());
    new Server().sendPacket(launchBallBouncer);
    //GameTable gameTable = addFourInARowGameToDatabase(user, true);
    super.getStage().close();
    new FourInARowScreen(new Stage(), new FourInARowGame(null, new FourInARowGameSettings()), user).show();
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
   * Prepare buttons for ball bouncer
   */
  private void prepareButtonsBallBouncer() {
    startBallBouncerGame = createButton(lang.get("mainScreen.ballBouncer.launchBallBouncerGame"));
    startBallBouncerGame.setOnAction(e -> launchBallBouncer());

    viewBestPlayersBallBouncer = createButton(lang.get("mainScreen.ballBouncer.viewBestBallBouncerPlayers"));
    viewBestPlayersBallBouncer.setOnAction(e -> viewBestPlayers());
  }

  /**
   * Prepare buttons for Four in a row
   */
  private void prepareButtonsFourInARow() {
    startFourInARowGame = createButton(lang.get("mainScreen.fourInARow.launchFourInARowGame"));
    startFourInARowGame.setOnAction(e -> launchFourInARow());

    viewBestPlayersFourInARow = createButton(lang.get("mainScreen.fourInARow.viewBestFourInARowPlayers"));
    viewBestPlayersFourInARow.setOnAction(e -> viewBestPlayers());
  }

  /**
   * Preparing escape buttons, for example reLoginButton, closeMainScreen and viewProfileButton
   */
  private void prepareEscapeButtons() {
    reLoginButton = createButton(lang.get("mainScreen.menu.reLogin"));
    reLoginButton.setOnAction(e -> reLogin());
    closeMainScreen = createButton(lang.get("mainScreen.menu.close"));
    closeMainScreen.setOnAction(e -> stage.close());
    viewProfileButton = createButton(lang.get("mainScreen.menu.viewProfile"));
    viewProfileButton.setOnAction(e -> prepareProfileScreen());
    friendshipMenuButton = createButton(lang.get("mainScreen.menu.friendshipMenu"));
    friendshipMenuButton.setOnAction(e -> prepareFriendshipMenu());
  }

  /**
   * Prepare escape layout using horizontal layout
   */
  private void prepareEscapeLayout() {
    hEscapeBox = new HBox(settingsForScreens.getSpacingForVBox());
    hEscapeBox.setMinWidth(super.getWidth());
    hEscapeBox.getChildren().addAll(reLoginButton, closeMainScreen, viewProfileButton, friendshipMenuButton);
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
    friendshipMenu = new FriendshipMenu(new Stage(), user);
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
    labelBallBouncer = new Label(lang.get("mainScreen.ballBouncer.ballBouncerLabel"));
    fBallBouncer = new Font(settingsForScreens.getTypeCharBallBouncer(), settingsForScreens.getSizeOfTextBallBouncer());
    labelBallBouncer.setTextFill(settingsForScreens.getColorBallBouncerLabel());
    labelBallBouncer.setWrapText(true);
    labelBallBouncer.setFont(fBallBouncer);
  }

  /**
   * Prepare label for four in a row
   */
  private void prepareLabelForFourInARow() {
    labelFourInARow = new Label(lang.get("mainScreen.fourInARow.fourInARowLabel"));
    fFourInARow = new Font(settingsForScreens.getTypeCharFourInARow(), settingsForScreens.getSizeOfTextFourInARow());
    labelFourInARow.setTextFill(settingsForScreens.getColorFourInARowLabel());
    labelFourInARow.setWrapText(true);
    labelFourInARow.setFont(fFourInARow);
  }

  /**
   * Preparation of profile screen, additional information about user will be loaded from database
   */
  private void prepareProfileScreen() {
    playerProfileScreen = new PlayerProfileScreen(new Stage());
    ServerOutLogin login = new ServerOutLogin(user.getPassword(),user.getUserName());
    new Server().sendPacket(login);

    ServerInChangeProfile serverInChangeProfile = new ServerInChangeProfile();
    UserPuzzleBattle userFromDatabase = serverInChangeProfile.getUserPuzzleBattle();

    if (userFromDatabase != null) {
      updateInformationPlayerProfileScreen(playerProfileScreen, userFromDatabase);
      playerProfileScreen.show();
    } else {
      Logging.logWarning("User data hasn't been obtained so far");
    }
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
    new LoginScreen(getStage()).show();
  }

  /*
  private void updateInformationPlayerProfileScreen1(PlayerProfileScreen playerProfile, UserPuzzleBattle userFromDatabase) {

    DateFormat df;
    String convertedDate;
    Calendar calendar;
    int year, currentYear;
    ByteArrayOutputStream b;
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
      df = new SimpleDateFormat("MM/dd/yyyy");
      convertedDate = df.format(userFromDatabase.getDateOfBirth());
      calendar = Calendar.getInstance();
      try {
        calendar.setTime(AdditionalInformationScreen.convertStringToDate(userFromDatabase.getDateOfBirth()));
      }
      catch (ParseException e)
      {
        e.printStackTrace();
      }
      year = calendar.get(Calendar.YEAR);
      currentYear = Calendar.getInstance().get(Calendar.YEAR);

      playerProfile.setLoadedAge(String.valueOf((currentYear - year)));
      playerProfile.setLoadedDateOfBirth(convertedDate);
    }

    Logging.logInfo("all available data have been added to profile from database");
  }*/

  /**
   * Update information for player profile screen
   *
   * @param playerProfile    player profile screen
   * @param userFromDatabase user loaded width additional information from database using his nickname and password
   */
  private void updateInformationPlayerProfileScreen(PlayerProfileScreen playerProfile, UserPuzzleBattle userFromDatabase) {

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

    if(userFromDatabase.getAge() != 0) {
      playerProfile.setLoadedAge("" + userFromDatabase.getAge());
    }
    Logging.logInfo("all available data have been added to profile from database");
  }

  /**
   * Method creates screen for best players and shows it for user
   */
  private void viewBestPlayers() {
    BestPlayersScreen bestPlayersScreen = new BestPlayersScreen(user,new Stage(), settingsForScreens);
    bestPlayersScreen.show();
  }
}
