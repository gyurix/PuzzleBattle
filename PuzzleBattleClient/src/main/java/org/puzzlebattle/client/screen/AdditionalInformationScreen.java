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
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.puzzlebattle.client.databaseTables.UserPuzzleBattle;
import org.puzzlebattle.core.utils.Logging;

import java.io.File;
import java.io.FileInputStream;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Class for obtaining additional information from given forms, which are created here.
 */
public class AdditionalInformationScreen extends AbstractScreen {

  private Label addDateOfBirth, addNameLabel, addPhotoLabel, addSurnameLabel;
  private String addYourDateOfBirth = "Add your birth, in format 12. 11. 2016";
  private String addYourName = "Add your name";
  private String addYourSurname = "Add your surname";
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
  private VBox mainComponentsScene1, mainComponentsScene2;
  private HBox movePageScene1, movePageScene2;
  private TextField nameText;
  private String nickNameOfPlayer;
  private String noImageLoaded = "No image loaded";
  private Button pageNextScene1, pagePreviousScene1, pageNextScene2, pagePreviousScene2;
  private Label pathForPhotoLabel;
  private PlayerProfileScreen playerProfile;
  private Region regionNameSurname, regionSurnameDateOfBirth;
  private Label rightSideLabelScene1, rightSideLabelScene2;
  private Scene scene1, scene2;
  private TextField surnameText;
  private Stage stage;

  /**
   * Constructor which prepares window with its basic components for obtaining additional information from them
   *
   * @param stage         stage which is used to display window
   * @param nickName      nickname of user
   * @param emailOfPlayer email of user
   */
  public AdditionalInformationScreen(Stage stage, String nickName, String emailOfPlayer) {
    super(stage);
    this.stage=stage;
    this.nickNameOfPlayer = nickName;
    this.emailOfPlayer = emailOfPlayer;
    prepareComponents();
    prepareScenes();
    stage.setTitle("Additional information of player");
    Logging.logInfo("Additional information screen has been created, but no shown.");
  }

  /**
   * Convertion string date to sql date, for storing into database
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
   * Alert window which appears if image cant't be loaded
   */
  private void displayAlert() {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Image can't be loaded.");
    alert.setContentText("Try to add another image or try to load image again.");
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
    FileScreen fileScreen = new FileScreen("Load your profile photo");
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
      loadedName = "name";
    }
    if (loadedSurname.equals(addYourSurname)) {
      Logging.logInfo("No surname has been found!");
      loadedSurname = "surname";
    }
    if (loadedDateOfBirth.equals(addYourDateOfBirth)) {
      Logging.logInfo("No date of birth has been found!");
      loadedDateOfBirth = "date of birth";
    }
  }

  /**
   * Buttons for scene 1
   */
  private void prepareButtonsForScene1() {
    pageNextScene1 = createButton("Next",100);
    pageNextScene1.setOnAction(e -> stage.setScene(scene2));
    pagePreviousScene1 = createButton("Previous",100);
    pagePreviousScene1.setDisable(true);
    confirmScene1 = createButton("Confirm",200);
    confirmScene1.setOnAction(e -> prepareProfileScreen());
  }

  private Button createButton(String text, int minWidth){
    Button button = new Button(text);
    button.setMinWidth(minWidth);
    return button;
  }
  /**
   * Buttons for scene 2
   */
  private void prepareButtonsForScene2() {
    pageNextScene2 = createButton("Next",100);
    pageNextScene2.setDisable(true);
    pagePreviousScene2 = createButton("Previous",100);
    pagePreviousScene2.setOnAction(e -> stage.setScene(scene1));
    confirmScene2 = createButton("Confirm",200);
    confirmScene2.setOnAction(e -> prepareProfileScreen());
    loadPhoto = new Button("Load photo");
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

  private Label createLabel(String text,Font font){
    Label label = new Label(text);
    label.setMaxWidth(Double.MAX_VALUE);
    label.setFont(font);
    return label;
  }

  /**
   * Labels for scene 1
   */
  private void prepareLabelsForScene1() {
    prepareSideLabelsForScene1();
    addNameLabel = createLabel("Your name",super.getDefaultFont());
    addSurnameLabel = createLabel("Your surname",super.getDefaultFont());
    addDateOfBirth = createLabel("Your birth",super.getDefaultFont());
  }

  /**
   * Labels for scene 2
   */
  private void prepareLabelsForScene2() {
    prepareSideLabelsForScene2();
    addPhotoLabel = createLabel("Your photo",super.getDefaultFont());
    pathForPhotoLabel = createLabel("Your photo",Font.font("Courier", FontPosture.ITALIC, 15));
  }

  private BorderPane createBorderPane(Node bottom, Node center, Node left, Node right){
    BorderPane border = new BorderPane();
    border.setBottom(bottom);
    border.setCenter(center);
    border.setLeft(left);
    border.setRight(right);
    return border;
  }

  /**
   * Layouts for scene 1
   */
  private void prepareLayoutsForScene1() {
    prepareMovingPageLayoutScene1();
    prepareMainComponentLayoutScene1();
    borderScene1 = createBorderPane(movePageScene1,mainComponentsScene1,leftSideLabelScene1,rightSideLabelScene1);
  }

  /**
   * Layouts for scene 2
   */
  private void prepareLayoutsForScene2() {
    prepareMovingPageLayoutScene2();
    prepareMainComponentLayoutScene2();
    borderScene2 = createBorderPane(movePageScene2,mainComponentsScene2,leftSideLabelScene2,rightSideLabelScene2);
  }

  private VBox createVBoxDefaultPadding(int spacing, double minWidth,Node ... args){
    VBox vBox = new VBox(spacing);
    vBox.setMinWidth(minWidth);
    vBox.getChildren().addAll();
    vBox.setPadding(super.createDefaultInsets());
    return vBox;
  }

  /**
   * Main component layout for scene 1
   */
  private void prepareMainComponentLayoutScene1() {
    mainComponentsScene1 = createVBoxDefaultPadding(10,super.getWidth() - 200,addNameLabel, nameText, regionNameSurname, addSurnameLabel, surnameText, regionSurnameDateOfBirth,
            addDateOfBirth, dateOfBirthText);
  }

  /**
   * Main components layout for scene 2
   */
  private void prepareMainComponentLayoutScene2() {
    mainComponentsScene2 = createVBoxDefaultPadding(10,super.getWidth() - 200,addPhotoLabel, pathForPhotoLabel, loadPhoto);
  }


  private HBox createHBox(int spacing,double minHeight,Node ... args){
    HBox hBox = new HBox(spacing);
    hBox.setMinHeight(minHeight);
    hBox.getChildren().addAll(leftConfirmScene1, confirmScene1, bottomLeftScene1, pageNextScene1, pagePreviousScene1);
    return hBox;
  }
  /**
   * Page layouts for scene 1
   */
  private void prepareMovingPageLayoutScene1() {
    movePageScene1 = createHBox(10,50,leftConfirmScene1, confirmScene1, bottomLeftScene1, pageNextScene1, pagePreviousScene1);
  }

  /**
   * Page layouts for scene 2
   */
  private void prepareMovingPageLayoutScene2() {
    movePageScene2 = createHBox(10,50,leftConfirmScene2, confirmScene2, bottomLeftScene2, pageNextScene2, pagePreviousScene2);
  }

  /**
   * Creation of profile screen with given data
   */
  private void prepareProfileScreen() {
    try {
      prepareBasicComponentsForProfileScreen();
      prepareBasicSettingsForProfileScreen();
      playerProfile = new PlayerProfileScreen(new Stage());
      updateInformationDatabase();
      updateInformationPlayerProfileScreen(playerProfile);
      playerProfile.show();
      stage.close();
    } catch (ParseException e) {
      Logging.logWarning("Cannot convert string to date!", e);
    }
  }

  private Region createRegion(int minWidth){
    Region region = new Region();
    region.setMinWidth(minWidth);
    return region;
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
    this.pane=borderScene1;
    scene2 = new Scene(borderScene2, super.getWidth(), super.getHeight());
  }

  private Label createLabel(int minWidth){
    Label label =new Label();
    label.setMinWidth(minWidth);
    return label;
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
   * Updating information of database.
   */
  private void updateInformationDatabase() {
    SessionFactory sf = new Configuration().configure("/META-INF/hibernate.cfg.xml").buildSessionFactory();
    Session session = sf.openSession();
    Transaction t = session.beginTransaction();
    String updateUser = "UPDATE UserPuzzleBattle SET name = ?3, surname = ?4,dateOfBirth = ?5, avatar = ?6 WHERE nickName=?1 AND email=?2";
    Query query = session.createQuery(updateUser);
    query.setParameter(1, nickNameOfPlayer);
    query.setParameter(2, emailOfPlayer);
    query.setParameter(3, loadedName);
    query.setParameter(4, loadedSurname);
    query.setParameter(5, createdDate);
    query.setParameter(6, saveImageToBytes(loadedImage));

    query.executeUpdate();
    t.commit();
    session.close();
    sf.close();

    Logging.logInfo("Information about user in database has been updated.");
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
