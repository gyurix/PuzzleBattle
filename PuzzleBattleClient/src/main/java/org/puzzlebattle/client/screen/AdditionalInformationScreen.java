package org.puzzlebattle.client.screen;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.stage.Stage;
import org.puzzlebattle.client.games.UserPuzzleBattle;
import org.puzzlebattle.client.protocol.Client;
import org.puzzlebattle.client.protocol.packets.in.ServerInChangeProfile;
import org.puzzlebattle.client.protocol.packets.out.ServerOutChangeProfile;
import org.puzzlebattle.core.utils.Logging;

import java.io.File;
import java.io.FileInputStream;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.puzzlebattle.core.utils.LangFile.lang;

/**
 * Class for obtaining additional information from given forms, which are created here.
 */
public class AdditionalInformationScreen extends AbstractScreen {

  private Label addDateOfBirth, addNameLabel, addPhotoLabel, addSurnameLabel;
  private String addYourDateOfBirth = lang.get("additionalInformation.set.dateOfBirth");
  private String addYourName = lang.get("additionalInformation.set.addYourName");
  private String addYourSurname = lang.get("additionalInformation.set.addYourSurname");
  private BorderPane borderScene1, borderScene2;
  private Region bottomLeftScene1, bottomLeftScene2;
  private Button confirmScene1, confirmScene2;
  private Date createdDate;
  private TextField dateOfBirthText;
  private String defaultImage = "faces/face1.png";
  private String emailOfPlayer;
  private Region leftConfirmScene1, leftConfirmScene2;
  private Label leftSideLabelScene1, leftSideLabelScene2;
  private Button loadPhoto;
  private File loadedImage;
  private String loadedImagePath = defaultImage, loadedName, loadedSurname, loadedAge, loadedDateOfBirth;
  private UserPuzzleBattle loadedUser;
  private VBox mainComponentsScene1, mainComponentsScene2;
  private HBox movePageScene1, movePageScene2;
  private TextField nameText;
  private String nickNameOfPlayer;
  private String noImageLoaded = lang.get("additionalInformation.set.noImageLoaded");
  private Button pageNextScene1, pagePreviousScene1, pageNextScene2, pagePreviousScene2;
  private PlayerProfileScreen playerProfile;
  private Region regionNameSurname, regionSurnameDateOfBirth;
  private Label rightSideLabelScene1, rightSideLabelScene2, pathForPhotoLabel;
  private Scene scene1, scene2;
  private TextField surnameText;

  /**
   * Constructor which prepares window with its basic components for obtaining additional information from them
   *
   * @param stage         stage which is used to display window
   * @param nickName      nickname of user
   * @param emailOfPlayer email of user
   */
  public AdditionalInformationScreen(Stage stage, String nickName, String emailOfPlayer, Client client) {
    super(stage, client);
    this.nickNameOfPlayer = nickName;
    this.emailOfPlayer = emailOfPlayer;
    prepareComponents();
    prepareScenes();
    stage.setTitle(lang.get("additionalInformation.title"));
    Logging.logInfo("Additional information screen has been created, but no shown.");
  }

  /**
   * Conversion string date to sql date, for storing into database
   *
   * @param dateToConvert string representation of date which should be converted
   * @return sql date prepared to be stored into database
   * @throws ParseException if there is error while convertion
   */
  public static Date convertStringToDate(String dateToConvert) throws ParseException {
    SimpleDateFormat simpleDateF = new SimpleDateFormat("dd. MM. yyyy");
    java.util.Date date = simpleDateF.parse(dateToConvert);
    Date sqlDate = new Date(date.getTime());
    return sqlDate;
  }

  /**
   * Creates border pane and adds components to selected position
   *
   * @param bottom - component which will be stored in bottom
   * @param center - component which will be stored center
   * @param left   - component which will be stored left
   * @param right  - component which will be stored right
   * @return created borer pane
   */
  private BorderPane createBorderPane(Node bottom, Node center, Node left, Node right) {
    BorderPane border = new BorderPane();
    border.setBottom(bottom);
    border.setCenter(center);
    border.setLeft(left);
    border.setRight(right);
    return border;
  }

  /**
   * Creation of button with specified text and minWidth
   *
   * @param text     - text string which will be added to button
   * @param minWidth - minimal width of button
   * @return created button
   */
  private Button createButton(String text, int minWidth) {
    Button button = new Button(text);
    button.setMinWidth(minWidth);
    return button;
  }

  /**
   * Creation of label with specified text which will be added and font
   *
   * @param text - text string which will be added to label
   * @param font - font which will be applied on label
   * @return created label
   */
  private Label createLabel(String text, Font font) {
    Label label = new Label(text);
    label.setMaxWidth(Double.MAX_VALUE);
    label.setFont(font);
    return label;
  }

  /**
   * Creation of label with specified minWidth
   *
   * @param minWidth - minimal width of label
   * @return created label
   */
  private Label createLabel(int minWidth) {
    Label label = new Label();
    label.setMinWidth(minWidth);
    return label;
  }

  /**
   * Creation of region with specified minWidth
   *
   * @param minWidth - minimal width of region
   * @return created region
   */
  private Region createRegion(int minWidth) {
    Region region = new Region();
    region.setMinWidth(minWidth);
    return region;
  }

  /**
   * Creation of VBox with specified padding, minWidth and componets
   *
   * @param spacing  - spacing which should be applied on VBox
   * @param minWidth - minimal width of VBox
   * @param args     - components of VBox
   * @return created VBox
   */
  private VBox createVBox(int spacing, double minWidth, Node... args) {
    VBox vBox = new VBox(spacing);
    vBox.setMinWidth(minWidth);
    vBox.getChildren().addAll(args);
    vBox.setPadding(super.createDefaultInsets());
    return vBox;
  }

  /**
   * Alert window which appears if image cant't be loaded
   */
  private void displayAlert() {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle(lang.get("additionalInformation.imageError.errorImage"));
    alert.setContentText(lang.get("additionalInformation.imageError.content"));
    alert.showAndWait();
  }

  /**
   * Abstracting string from year and its conversion to in
   */
  private int getYear(String yearInFormat) {
    yearInFormat = yearInFormat.substring(8);
    return Integer.parseInt(yearInFormat);
  }

  /**
   * Chosen photo is loaded here
   */
  private void loadPhoto() {
    loadedImagePath = "faces/face1.png";
    pathForPhotoLabel.setText(noImageLoaded);
    FileScreen fileScreen = new FileScreen(lang.get("additionalInformation.fileScreenTitle"));
    fileScreen.setPictureFilter();
    loadedImage = fileScreen.showDialog();
    pathForPhotoLabel.setText(loadedImage.getPath());
    try {
      loadedImagePath = loadedImage.toURI().toURL().toExternalForm();
    } catch (MalformedURLException e) {
      Logging.logSevere("Wrong url typed!", e);
      displayAlert();
    }
  }

  /**
   * Loading given components from text fields, and given conversions are used if necessary
   */
  private void prepareBasicComponentsForProfileScreen() throws ParseException {
    int currentYear = Calendar.getInstance().get(Calendar.YEAR);
    loadedName = nameText.getText();
    loadedSurname = surnameText.getText();
    loadedDateOfBirth = dateOfBirthText.getText();
    createdDate = convertStringToDate(loadedDateOfBirth);
    if (dateOfBirthText.getLength() == 12) {
      loadedAge = Integer.toString(currentYear - getYear(loadedDateOfBirth));
    } else {
      Logging.logSevere("Bad length of given date of birth!");
    }
  }

  /**
   * Preparing basic settings for profile screen, especially if one or more
   * information haven.t been stored
   */
  private void prepareBasicSettingsForProfileScreen() {
    if (loadedImagePath.equals(noImageLoaded)) {
      Logging.logInfo("Default image must be set!");
      loadedImagePath = "faces/face1.png";
    }
    if (loadedName.equals(addYourName)) {
      Logging.logInfo("No name has been found!");
      loadedName = lang.get("additionalInformation.defaultSet.name");
    }
    if (loadedSurname.equals(addYourSurname)) {
      Logging.logInfo("No surname has been found!");
      loadedSurname = lang.get("additionalInformation.defaultSet.surname");
    }
    if (loadedDateOfBirth.equals(addYourDateOfBirth)) {
      Logging.logInfo("No date of birth has been found!");
      loadedDateOfBirth = lang.get("additionalInformation.defaultSet.dateOfBirth");
    }
  }

  /**
   * Buttons for scene 1
   */
  private void prepareButtonsForScene1() {
    pageNextScene1 = createButton(lang.get("additionalInformation.button.next"), 100);
    pageNextScene1.setOnAction(e -> stage.setScene(scene2));
    pagePreviousScene1 = createButton(lang.get("additionalInformation.button.previous"), 100);
    pagePreviousScene1.setDisable(true);
    confirmScene1 = createButton(lang.get("additionalInformation.button.confirm"), 200);
    confirmScene1.setOnAction(e -> prepareProfileScreen());
  }

  /**
   * Buttons for scene 2
   */
  private void prepareButtonsForScene2() {
    pageNextScene2 = createButton(lang.get("additionalInformation.button.next"), 100);
    pageNextScene2.setDisable(true);
    pagePreviousScene2 = createButton(lang.get("additionalInformation.button.previous"), 100);
    pagePreviousScene2.setOnAction(e -> stage.setScene(scene1));
    confirmScene2 = createButton(lang.get("additionalInformation.button.confirm"), 200);
    confirmScene2.setOnAction(e -> prepareProfileScreen());
    loadPhoto = new Button(lang.get("additionalInformation.button.loadPhoto"));
    loadPhoto.setMaxWidth(Double.MAX_VALUE);
    loadPhoto.setOnAction(e -> loadPhoto());
  }

  /**
   * Prepares components to obtain additional information
   */
  private void prepareComponents() {
    prepareLabelsForScene1();
    prepareLabelsForScene2();
    prepareTextFieldsForScene1();
    prepareRegionForScene1();
    prepareRegionsForScene2();
    prepareButtonsForScene1();
    prepareButtonsForScene2();
    prepareLayoutsForScene1();
    prepareLayoutsForScene2();
  }

  /**
   * Labels for scene 1
   */
  private void prepareLabelsForScene1() {
    prepareSideLabelsForScene1();
    addNameLabel = createLabel(lang.get("additionalInformation.labels.yourName"), super.getDefaultFont());
    addSurnameLabel = createLabel(lang.get("additionalInformation.labels.yourName"), super.getDefaultFont());
    addDateOfBirth = createLabel(lang.get("additionalInformation.labels.yourBirth"), super.getDefaultFont());
  }

  /**
   * Labels for scene 2
   */
  private void prepareLabelsForScene2() {
    prepareSideLabelsForScene2();
    addPhotoLabel = createLabel(lang.get("additionalInformation.labels.yourPhoto"), super.getDefaultFont());
    pathForPhotoLabel = createLabel(lang.get("additionalInformation.labels.yourPhoto"), Font.font("Courier", FontPosture.ITALIC, 15));
  }

  /**
   * Layouts for scene 1
   */
  private void prepareLayoutsForScene1() {
    prepareMovingPageLayoutScene1();
    prepareMainComponentLayoutScene1();
    borderScene1 = createBorderPane(movePageScene1, mainComponentsScene1, leftSideLabelScene1, rightSideLabelScene1);
  }

  /**
   * Layouts for scene 2
   */
  private void prepareLayoutsForScene2() {
    prepareMovingPageLayoutScene2();
    prepareMainComponentLayoutScene2();
    borderScene2 = createBorderPane(movePageScene2, mainComponentsScene2, leftSideLabelScene2, rightSideLabelScene2);
  }

  /**
   * Main component layout for scene 1
   */
  private void prepareMainComponentLayoutScene1() {
    mainComponentsScene1 = createVBox(10, super.getWidth() - 200, addNameLabel, nameText, regionNameSurname, addSurnameLabel, surnameText, regionSurnameDateOfBirth,
            addDateOfBirth, dateOfBirthText);
  }

  /**
   * Main components layout for scene 2
   */
  private void prepareMainComponentLayoutScene2() {
    mainComponentsScene2 = createVBox(10, super.getWidth() - 200, addPhotoLabel, pathForPhotoLabel, loadPhoto);
  }

  /**
   * Page layouts for scene 1
   */
  private void prepareMovingPageLayoutScene1() {
    movePageScene1 = new HBox(10);
    movePageScene1.setMinHeight(50);
    movePageScene1.getChildren().addAll(leftConfirmScene1, confirmScene1, bottomLeftScene1, pageNextScene1, pagePreviousScene1);
  }

  /**
   * Page layouts for scene 2
   */
  private void prepareMovingPageLayoutScene2() {
    movePageScene2 = new HBox(10);
    movePageScene2.setMinHeight(50);
    movePageScene2.getChildren().addAll(leftConfirmScene2, confirmScene2, bottomLeftScene2, pageNextScene2, pagePreviousScene2);
  }

  /**
   * Creation of profile screen with given data
   */
  private void prepareProfileScreen() {
    try {
      prepareBasicComponentsForProfileScreen();
      prepareBasicSettingsForProfileScreen();
      playerProfile = new PlayerProfileScreen(new Stage(), client);
      updateInformationDatabase();
      updateInformationPlayerProfileScreen(playerProfile);
      playerProfile.show();
      stage.close();
    } catch (ParseException e) {
      Logging.logWarning("Cannot convert string to date!", e);
    }
  }

  /**
   * Prepare components of scene 1, it includes used regions, and labels, text fields
   * <br>
   * name, surname, dateOfBirth
   */
  private void prepareRegionForScene1() {
    regionSurnameDateOfBirth = createRegion(10);
    regionNameSurname = createRegion(10);
    bottomLeftScene1 = createRegion(100);
    leftConfirmScene1 = createRegion(50);
  }

  /**
   * Regions for scene 2
   */
  private void prepareRegionsForScene2() {
    bottomLeftScene2 = createRegion(100);
    leftConfirmScene2 = createRegion(50);
  }

  /**
   * Prepare scenes used in additional information window
   */
  private void prepareScenes() {
    scene1 = new Scene(borderScene1, super.getWidth(), super.getHeight());
    scene2 = new Scene(borderScene2, super.getWidth(), super.getHeight());
  }

  /**
   * Sides labels for scene 1.
   */
  private void prepareSideLabelsForScene1() {
    leftSideLabelScene1 = createLabel(50);
    rightSideLabelScene1 = createLabel(50);
  }

  /**
   * Side labels for scene 2
   */
  private void prepareSideLabelsForScene2() {
    leftSideLabelScene2 = createLabel(50);
    rightSideLabelScene2 = createLabel(50);
  }

  /**
   * Text fields for scene 1
   */
  private void prepareTextFieldsForScene1() {
    nameText = new TextField(addYourName);
    surnameText = new TextField(addYourSurname);
    dateOfBirthText = new TextField(addYourDateOfBirth);
  }

  /**
   * Method which saves image to byte field for inserting it as component for database
   *
   * @param file file which should be inserted into database
   * @return byte field containing loaded picture, if everything is ok during reading
   */
  public byte[] saveImageToBytes(File file) {
    byte[] result = new byte[(int) file.length()];

    try {
      FileInputStream fileInputStream = new FileInputStream(file);
      fileInputStream.read(result);
      fileInputStream.close();
    } catch (Exception e) {
      Logging.logWarning("Cannot read selected image from file|", e);
    }

    return result;
  }

  /**
   * Showing additional information screen
   */
  public void show() {
    stage.setScene(scene1);
    stage.show();
  }

  /**
   * Update of information after collecting them from text fields
   */
  private void updateInformationDatabase() {
    ServerOutChangeProfile changeProfile = new ServerOutChangeProfile(saveImageToBytes(loadedImage), loadedDateOfBirth, loadedName, loadedSurname);
    client.sendPacket(changeProfile);
    ServerInChangeProfile serverInChangeProfile = new ServerInChangeProfile();
    //serverInChangeProfile.getUserPuzzleBattle(); NOT USED DATA WILL BE DISPLAYED FROM TEXT FIELDS, NO WAIT FOR REPLY
  }

  /**
   * Updating information for player profile screen.
   *
   * @param playerProfile player's profile, not shown, window, where given information will be shown
   */
  private void updateInformationPlayerProfileScreen(PlayerProfileScreen playerProfile) {
    playerProfile.setNickName(nickNameOfPlayer);
    playerProfile.setEmail(emailOfPlayer);
    playerProfile.setLoadedImage(loadedImagePath);
    playerProfile.setLoadedName(loadedName);
    playerProfile.setLoadedSurname(loadedSurname);
    playerProfile.setLoadedAge(loadedAge);
    playerProfile.setLoadedDateOfBirth(loadedDateOfBirth);
  }
}
