package org.puzzlebattle.client.screen;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.puzzlebattle.client.databaseTables.*;
import org.puzzlebattle.client.games.bouncer.BallBouncerScreen;
import org.puzzlebattle.client.games.bouncer.BouncerGame;
import org.puzzlebattle.client.games.bouncer.BouncerGameSettings;
import org.puzzlebattle.client.games.fourinarow.FourInARowGame;
import org.puzzlebattle.client.games.fourinarow.FourInARowGameSettings;
import org.puzzlebattle.client.games.fourinarow.FourInARowScreen;
import org.puzzlebattle.core.utils.Logging;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Main screen in the program. Probably games can be chosen here.
 *
 * @author (Jakub Perdek, Juraj Barath)
 * @version (1.0)
 */
public class MainScreen extends AbstractScreen {

  Separator separator;
  private Button closeMainScreen;
  private Font fBallBouncer;
  private Font fFourInARow;
  private GridPane gridPane;
  private HBox hEscapeBox;
  private Image imageBallBouncer;
  private Image imageFourInARow;
  private ImageView imageViewBallBouncer;
  private ImageView imageViewFourInARow;
  private Label labelBallBouncer;
  private Label labelFourInARow;
  private Scene mainScene;
  private Button reLoginButton;
  private Button viewProfileButton;
  private SettingsForScreens settingsForScreens;
  private Stage stage;
  private Button startBallBouncerGame;
  private Button startFourInARowGame;
  private VBox vBoxBallBouncerGame;
  private VBox vBoxFourInARowGame;
  private Button viewBestPlayersBallBouncer;
  private Button viewBestPlayersFourInARow;
  private UserPuzzleBattle user;
  private PlayerProfileScreen playerProfileScreen;
  private Region regionFourInARow, regionBallBouncer;

  /**
   * Constructor which creates main screen in the program.
   */
  public MainScreen(Stage stage, SettingsForScreens settingsForScreens, UserPuzzleBattle user) {
    super(stage);
    this.stage = stage;
    this.settingsForScreens = settingsForScreens;
    this.user = user;

    GameType.addGamesToDBIfTheyAreNot();

    prepareBallBouncerGameMenu();
    prepareFourInARowGameMenu();
    prepareEscapeButtons();
    prepareEscapeLayout();
    prepareSceneAndGridPane();
    Logging.logInfo("Main scree has been created.");
  }

  /**
   * Launching Ball bouncer game
   */
  private void launchBallBouncer() {

    addBallBouncerGameToDatabase(user,true);
    super.getStage().close();
    new BallBouncerScreen(new Stage(), new BouncerGame(null, new BouncerGameSettings())).show();
  }

  /**
   * Launching Four in a row game
   */
  private void launchFourInARow() {
    addFourInARowGameToDatabase(user,true);
    super.getStage().close();
    new FourInARowScreen(new Stage(), new FourInARowGame(null, new FourInARowGameSettings()),user).show();
  }

  private void addFourInARowGameToDatabase(UserPuzzleBattle userPuzzleBattle,boolean test){
    int gameType =GameType.getFourInARowGame().getId();
    long gameSettingId = GameSettings.insertGameSettingsToDBIfTheyAreNotExistAndGetId(new FourInARowGameSettings());
    GameSettings fourInARowGameSetting = new FourInARowGameSettings();
    ((FourInARowGameSettings) fourInARowGameSetting).setGameType(gameType);
    GameTable gameTable= GameTable.prepareGameTable(userPuzzleBattle,test,gameType,fourInARowGameSetting);
  }

  private void addBallBouncerGameToDatabase(UserPuzzleBattle userPuzzleBattle,boolean test) {
    int gameType =GameType.getFourInARowGame().getId();
    long gameSettingId = GameSettings.insertGameSettingsToDBIfTheyAreNotExistAndGetId(new BouncerGameSettings());
    GameSettings bouncerGameSettings = new BouncerGameSettings();
    ((BouncerGameSettings) bouncerGameSettings).setGameType( gameType);
    GameTable gameTable= GameTable.prepareGameTable(userPuzzleBattle,test,gameType,bouncerGameSettings);
  }

  /**
   * Prepare ball bouncer game menu, with all components which are made of
   */
  private void prepareBallBouncerGameMenu() {
    prepareBallBouncerRegion();
    prepareLabelForBallBouncer();
    prepareButtonsBallBouncer();
    prepareLayoutsBallBouncer();
    prepareImageBallBouncer();
  }

  /**
   * Prepare ball bounce
   */
  private void prepareBallBouncerRegion()
  {
    regionBallBouncer =  new Region();
    regionBallBouncer.setMinHeight(10);
  }

  /**
   * Prepare four in a row region
   */
  private void prepareFourInARowRegion()
  {
    regionFourInARow =  new Region();
    regionFourInARow.setMinHeight(10);
  }

  /**
   * Prepare buttons for ball bouncer
   */
  private void prepareButtonsBallBouncer() {
    startBallBouncerGame = new Button("Launch Ball Bouncer game.");
    startBallBouncerGame.setMaxWidth(Double.MAX_VALUE);
    startBallBouncerGame.setOnAction(
            e -> launchBallBouncer());

    viewBestPlayersBallBouncer = new Button("View best Ball Bouncer Players");
    viewBestPlayersBallBouncer.setMaxWidth(Double.MAX_VALUE);
    viewBestPlayersBallBouncer.setOnAction(e -> System.out.println("Not complete functionality"));
  }

  /**
   * Prepare buttons for Four in a row
   */
  private void prepareButtonsFourInARow() {
    startFourInARowGame = new Button("Launch Four in a row game");
    startFourInARowGame.setMaxWidth(Double.MAX_VALUE);
    startFourInARowGame.setOnAction(
            e -> launchFourInARow());

    viewBestPlayersFourInARow = new Button("View best Four In A Row Players");
    viewBestPlayersFourInARow.setMaxWidth(Double.MAX_VALUE);
    viewBestPlayersFourInARow.setOnAction(e -> System.out.println("Not completed functionality"));
  }

  /**
   * Prepare escape layout using horizontal layout
   */
  private void prepareEscapeLayout() {
    hEscapeBox = new HBox(settingsForScreens.getSpacingForVBox());
    hEscapeBox.getChildren().addAll(reLoginButton, closeMainScreen,viewProfileButton);
  }

  /**
   * Preparing escape buttons, for example reLoginButton, closeMainScreen and viewProfileButton
   */
  private void prepareEscapeButtons() {
    reLoginButton = new Button("Re login");
    reLoginButton.setMaxWidth(Double.MAX_VALUE);
    reLoginButton.setOnAction(e -> reLogin());
    closeMainScreen = new Button("Close");
    closeMainScreen.setMaxWidth(Double.MAX_VALUE);
    closeMainScreen.setOnAction(e -> stage.close());
    viewProfileButton = new Button("View profile");
    viewProfileButton.setMaxWidth(Double.MAX_VALUE);
    viewProfileButton.setOnAction(e->prepareProfileScreen());
  }

  /**
   * Preparing all components of Four in a row menu
   */
  private void prepareFourInARowGameMenu() {
    prepareFourInARowRegion();
    prepareLabelForFourInARow();
    prepareButtonsFourInARow();
    prepareLayoutFourInARow();
    prepareImageFourInARow();
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
    labelBallBouncer = new Label("Ball Bouncer");
    fBallBouncer = new Font(settingsForScreens.getTypeCharBallBouncer(), settingsForScreens.getSizeOfTextBallBouncer());
    labelBallBouncer.setTextFill(settingsForScreens.getColorBallBouncerLabel());
    labelBallBouncer.setWrapText(true);
    labelBallBouncer.setFont(fBallBouncer);
  }

  /**
   * Prepare label for four in a row
   */
  private void prepareLabelForFourInARow() {
    labelFourInARow = new Label("Four In A Row");
    fFourInARow = new Font(settingsForScreens.getTypeCharFourInARow(), settingsForScreens.getSizeOfTextFourInARow());
    labelFourInARow.setTextFill(settingsForScreens.getColorFourInARowLabel());
    labelFourInARow.setWrapText(true);
    labelFourInARow.setFont(fFourInARow);
  }

  /**
   * Prepare horizontal layout for four in a row
   */
  private void prepareLayoutFourInARow() {
    vBoxFourInARowGame = new VBox(settingsForScreens.getSpacingForVBox());
    vBoxFourInARowGame.setMaxWidth(Double.MAX_VALUE);
    vBoxFourInARowGame.setMaxHeight(Double.MAX_VALUE);
    vBoxFourInARowGame.getChildren().addAll(labelFourInARow,regionFourInARow, startFourInARowGame, viewBestPlayersFourInARow);
  }

  /**
   * Prepare vertical layout for ball bouncer
   */
  private void prepareLayoutsBallBouncer() {
    vBoxBallBouncerGame = new VBox(settingsForScreens.getSpacingForVBox());
    vBoxBallBouncerGame.setMaxWidth(Double.MAX_VALUE);
    vBoxBallBouncerGame.setMaxHeight(Double.MAX_VALUE);
    vBoxBallBouncerGame.getChildren().addAll(labelBallBouncer,regionBallBouncer, startBallBouncerGame, viewBestPlayersBallBouncer);
  }

  /**
   * Prepare scene and grid panel for
   */
  private void prepareSceneAndGridPane() {
    gridPane = new GridPane();
    separator = new Separator();
    gridPane.setHgap(20);
    gridPane.setVgap(20);
    separator.setOrientation(Orientation.VERTICAL);
    gridPane.setPadding(new Insets(10, 20, 10, 20));
    gridPane.add(vBoxBallBouncerGame, 0, 1);
    gridPane.add(separator, 1, 1);
    gridPane.add(vBoxFourInARowGame, 2, 1);
    GridPane.setValignment(hEscapeBox, VPos.BOTTOM);
    gridPane.add(hEscapeBox, 0, 3);
    mainScene = new Scene(gridPane, super.getWidth(), super.getHeight());
  }

  /**
   * Re login for user, user will log off and main menu will be closed
   */
  private void reLogin() {
    stage.close();
    new LoginScreen(stage).show();
  }

  /**
   * Showing of main screen
   */
  public void show() {
    stage.setScene(mainScene);
    stage.setTitle("Main menu");
    stage.setOnCloseRequest(e -> stage.close());
    stage.show();
  }

  /**
   * Preparation of profile screen, additional information about user will be loaded from database
   */
  private void prepareProfileScreen() {
    playerProfileScreen = new PlayerProfileScreen(new Stage());
    UserPuzzleBattle userFromDatabase = LoginRegisterUser.getRegister(user.getNickName(),user.getPassword());
    if(userFromDatabase!=null) {
      updateInformationPlayerProfileScreen(playerProfileScreen, userFromDatabase);
      playerProfileScreen.show();
    }
    else
    {
      Logging.logWarning("User data hasn't been obtained so far");
    }
  }

  /**
   * Update information for player profile screen
   *
   * @param playerProfile player profile screen
   * @param userFromDatabase user loaded width additional information from database using his nickname and password
   */
  private void updateInformationPlayerProfileScreen(PlayerProfileScreen playerProfile,UserPuzzleBattle userFromDatabase){

    DateFormat df;
    String convertedDate;
    Calendar calendar;
    int year, currentYear;
    ByteArrayOutputStream b;
    File imageFile = new File("PuzzleBattleClient/src/main/resources/pictures/avatar.bmp");
    try {
      FileOutputStream fos = new FileOutputStream(imageFile);
      if(userFromDatabase.getAvatar()!=null)
      fos.write(userFromDatabase.getAvatar());
      fos.close();
    }
    catch(IOException i)
    {
      Logging.logWarning("Error while writing image", i);
    }

    playerProfile.setNickName(userFromDatabase.getNickName());
    playerProfile.setEmail(userFromDatabase.getEmail());
    if(userFromDatabase.getAvatar()!=null) {
      playerProfile.setLoadedImage("pictures/avatar.bmp");
    }
    else
    {
      Logging.logFiner("no image is set, default will be applied");
      playerProfile.setLoadedImage("faces/face1.png");
    }

    if(userFromDatabase.getName()!=null) {
      playerProfile.setLoadedName(userFromDatabase.getName());
    }

    if(userFromDatabase.getSurname()!=null) {
      playerProfile.setLoadedSurname(userFromDatabase.getSurname());
    }

    if(userFromDatabase.getDateOfBirth()!=null) {
      df = new SimpleDateFormat("MM/dd/yyyy");
      convertedDate = df.format(userFromDatabase.getDateOfBirth());
      calendar = Calendar.getInstance();
      calendar.setTime(userFromDatabase.getDateOfBirth());
      year = calendar.get(Calendar.YEAR);
      currentYear = Calendar.getInstance().get(Calendar.YEAR);

      playerProfile.setLoadedAge(String.valueOf((currentYear - year)));
      playerProfile.setLoadedDateOfBirth(convertedDate);
    }

    Logging.logInfo("all available data have been added to profile from database");
  }
}
