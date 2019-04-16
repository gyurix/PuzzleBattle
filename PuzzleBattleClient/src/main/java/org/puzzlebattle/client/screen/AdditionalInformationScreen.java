package org.puzzlebattle.client.screen;

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

  private Label addDateOfBirth;
  private Label addNameLabel;
  private Label addPhotoLabel;
  private Label addSurnameLabel;
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
  private String loadedImagePath = defaultImage;
  private String loadedName, loadedSurname, loadedAge, loadedDateOfBirth;
  private UserPuzzleBattle loadedUser;
  private VBox mainComponentsScene1;
  private VBox mainComponentsScene2;
  private HBox movePageScene1, movePageScene2;
  private TextField nameText;
  private String nickNameOfPlayer;
  private String noImageLoaded = "No image loaded";
  private Button pageNextScene1, pagePreviousScene1;
  private Button pageNextScene2, pagePreviousScene2;
  private Label pathForPhotoLabel;
  private PlayerProfileScreen playerProfile;
  private Region regionNameSurname, regionSurnameDateOfBirth;
  private Label rightSideLabelScene1, rightSideLabelScene2;
  private Scene scene1, scene2;
  private Stage stage;
  private TextField surnameText;

  /**
   * Constructor which prepares window with its basic components for obtaining additional information from them
   *
   * @param stage         stage which is used to display window
   * @param nickName      nickname of user
   * @param emailOfPlayer email of user
   */
  public AdditionalInformationScreen(Stage stage, String nickName, String emailOfPlayer) {
    super(stage);
    this.stage = stage;
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
    pageNextScene1 = new Button("Next");
    pageNextScene1.setOnAction(e -> stage.setScene(scene2));
    pageNextScene1.setMinWidth(100);
    pagePreviousScene1 = new Button("Previous");
    pagePreviousScene1.setDisable(true);
    pagePreviousScene1.setMinWidth(100);
    confirmScene1 = new Button("Confirm");
    confirmScene1.setMinWidth(200);
    confirmScene1.setOnAction(e -> prepareProfileScreen());
  }

  /**
   * Buttons for scene 2
   */
  private void prepareButtonsForScene2() {
    pageNextScene2 = new Button("Next");
    pageNextScene2.setDisable(true);
    pageNextScene2.setMinWidth(100);
    pagePreviousScene2 = new Button("Previous");
    pagePreviousScene2.setOnAction(e -> stage.setScene(scene1));
    pagePreviousScene2.setMinWidth(100);
    confirmScene2 = new Button("Confirm");
    confirmScene2.setMinWidth(200);
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

  /**
   * Labels for scene 1
   */
  private void prepareLabelsForScene1() {
    prepareSideLabelsForScene1();
    addNameLabel = new Label("Your name");
    addNameLabel.setFont(super.getDefaultFont());
    addNameLabel.setMaxWidth(Double.MAX_VALUE);
    addSurnameLabel = new Label("Your surname");
    addSurnameLabel.setFont(super.getDefaultFont());
    addSurnameLabel.setMaxWidth(Double.MAX_VALUE);
    addDateOfBirth = new Label("Your birth");
    addDateOfBirth.setFont(super.getDefaultFont());
    addDateOfBirth.setMaxWidth(Double.MAX_VALUE);
  }

  /**
   * Labels for scene 2
   */
  private void prepareLabelsForScene2() {
    prepareSideLabelsForScene2();
    addPhotoLabel = new Label("Your photo");
    addPhotoLabel.setFont(super.getDefaultFont());
    addPhotoLabel.setMaxWidth(Double.MAX_VALUE);
    pathForPhotoLabel = new Label(noImageLoaded);
    pathForPhotoLabel.setFont(Font.font("Courier", FontPosture.ITALIC, 15));
    pathForPhotoLabel.setMaxWidth(Double.MAX_VALUE);
  }

  /**
   * Layouts for scene 1
   */
  private void prepareLayoutsForScene1() {
    prepareMovingPageLayoutScene1();
    prepareMainComponentLayoutScene1();

    borderScene1 = new BorderPane();
    borderScene1.setBottom(movePageScene1);
    borderScene1.setCenter(mainComponentsScene1);
    borderScene1.setLeft(leftSideLabelScene1);
    borderScene1.setRight(rightSideLabelScene1);
  }

  /**
   * Layouts for scene 2
   */
  private void prepareLayoutsForScene2() {
    prepareMovingPageLayoutScene2();
    prepareMainComponentLayoutScene2();

    borderScene2 = new BorderPane();
    borderScene2.setBottom(movePageScene2);
    borderScene2.setCenter(mainComponentsScene2);
    borderScene2.setLeft(leftSideLabelScene2);
    borderScene2.setRight(rightSideLabelScene2);
  }

  /**
   * Main component layout for scene 1
   */
  private void prepareMainComponentLayoutScene1() {
    mainComponentsScene1 = new VBox(10);
    mainComponentsScene1.setMinWidth(super.getWidth() - 200);
    mainComponentsScene1.getChildren().addAll(addNameLabel, nameText, regionNameSurname, addSurnameLabel, surnameText, regionSurnameDateOfBirth,
            addDateOfBirth, dateOfBirthText);
    mainComponentsScene1.setPadding(super.createDefaultInsets());
  }

  /**
   * Main components layout for scene 2
   */
  private void prepareMainComponentLayoutScene2() {
    mainComponentsScene2 = new VBox(10);
    mainComponentsScene2.setMinWidth(super.getWidth() - 200);
    mainComponentsScene2.getChildren().addAll(addPhotoLabel, pathForPhotoLabel, loadPhoto);
    mainComponentsScene2.setPadding(super.createDefaultInsets());
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
      playerProfile = new PlayerProfileScreen(new Stage());
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
    regionSurnameDateOfBirth = new Region();
    regionSurnameDateOfBirth.setMinHeight(10);
    regionNameSurname = new Region();
    regionNameSurname.setMinHeight(10);
    bottomLeftScene1 = new Region();
    bottomLeftScene1.setMinWidth(100);
    leftConfirmScene1 = new Region();
    leftConfirmScene1.setMinWidth(50);
  }

  /**
   * Regions for scene 2
   */
  private void prepareRegionsForScene2() {
    bottomLeftScene2 = new Region();
    bottomLeftScene2.setMinWidth(100);
    leftConfirmScene2 = new Region();
    leftConfirmScene2.setMinWidth(50);
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
    leftSideLabelScene1 = new Label();
    leftSideLabelScene1.setMinWidth(50);
    rightSideLabelScene1 = new Label();
    rightSideLabelScene1.setMinWidth(50);
  }

  /**
   * Side labels for scene 2
   */
  private void prepareSideLabelsForScene2() {
    leftSideLabelScene2 = new Label();
    leftSideLabelScene2.setMinWidth(50);
    rightSideLabelScene2 = new Label();
    rightSideLabelScene2.setMinWidth(50);
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
