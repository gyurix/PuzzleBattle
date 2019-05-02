package org.puzzlebattle.client.screen;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import org.puzzlebattle.client.games.User;
import org.puzzlebattle.client.protocol.Client;
import org.puzzlebattle.core.utils.Logging;

import java.util.Random;

import static org.puzzlebattle.core.utils.LangFile.lang;

/**
 * Player profile screen should show details about player if are available
 *
 * @author Jakub Perdek
 * @version 1.0
 */
public class PlayerProfileScreen extends AbstractScreen {

  private static int PICTURE_HEIGHT = 350;
  private static int PICTURE_WIDTH = 300;
  private VBox basicInformation, informationVBox;
  private Region betweenInformationCompHeading;
  private Label dateOfBirth, email, informationLabel, name, surname, nickName, age;
  private GridPane grid;
  private HBox informationInside;
  private Region informationOnRight;
  private Image photoImage;
  private Button returnButton;
  private ImageView selfPhoto;

  /**
   * Created instance of players profile screen
   *
   * @param stage - stage to set scene with specified profile detail
   */
  public PlayerProfileScreen(Stage stage, Client client) {
    super(stage, client);
    prepareComponents();
    this.pane = grid;
    Logging.logInfo("Player screen has been created.");
  }

  /**
   * Creates label with specified text and font
   *
   * @param text - specified name which should be displayed on label
   * @param font - specific font which should be applied
   * @return created label
   */
  private Label createLabel(String text, Font font) {
    Label label = new Label(text);
    label.setMaxWidth(Double.MAX_VALUE);
    label.setFont(font);
    return label;
  }

  /**
   * Creates region with specified text and font
   *
   * @param minHeight - min height of region
   * @return created region
   */
  private Region createRegion(double minHeight) {
    Region region = new Region();
    region.setMinHeight(minHeight);
    return region;
  }

  /**
   * Set given name for player profile screen
   *
   * @return title for player profile screen
   */
  public String getTitle() {
    return lang.get("playerProfile.title");
  }

  /**
   * Prepares buttons and photo
   */
  private void prepareButtonsAndPhoto() {
    Random r = new Random(10);
    int pictureNumber = r.nextInt(10);
    photoImage = new Image("faces/face" + pictureNumber + ".png");
    selfPhoto = new ImageView(photoImage);
    selfPhoto.setFitWidth(PICTURE_WIDTH);
    selfPhoto.setFitHeight(PICTURE_HEIGHT);

    returnButton = new Button(lang.get("login.return"));
    returnButton.setMinWidth(100);
  }

  /**
   * Prepares components for player profileScreen
   */
  private void prepareComponents() {
    prepareMainLabels();
    prepareInformationLabels();
    prepareButtonsAndPhoto();
    prepareRegion();
    prepareLayouts();
  }

  /**
   * Returns given font for labels
   *
   * @return - given font for labels
   */
  private Font prepareFontForLabels() {
    return Font.font("Arial", FontPosture.ITALIC, 20);
  }

  /**
   * Prepare font for main label
   *
   * @return font Courier which can be applied
   */
  private Font prepareFontForMainLabels() {
    return Font.font("Courier", FontWeight.BOLD, FontPosture.ITALIC, 30);
  }

  /**
   * Prepares horizontal content
   */
  private void prepareHorizontalForContent() {
    informationInside = new HBox(10);
    informationInside.setMinWidth(300);
    informationInside.getChildren().addAll(informationOnRight, informationVBox);
  }

  /**
   * Prepares labels with information about player
   */
  private void prepareInformationLabels() {
    name = createLabel(lang.get("playerProfile.name"), prepareFontForLabels());
    surname = createLabel(lang.get("playerProfile.surname"), prepareFontForLabels());
    age = createLabel(lang.get("playerProfile.age"), prepareFontForLabels());
    dateOfBirth = createLabel(lang.get("playerProfile.dateOfBirth"), prepareFontForLabels());
    email = createLabel(lang.get("playerProfile.email"), prepareFontForLabels());
  }

  /**
   * Prepares layout with information components
   */
  private void prepareLayoutWithInformationComponents() {
    prepareVerticalForInformation();
    prepareHorizontalForContent();
    prepareVerticalForHeading();
  }

  /**
   * Prepares layouts for player profile
   */
  private void prepareLayouts() {
    prepareLayoutWithInformationComponents();
    grid = new GridPane();
    grid.setPadding(new Insets(20, 0, 50, 0));
    grid.add(nickName, 0, 0);
    grid.add(selfPhoto, 1, 1);
    grid.add(basicInformation, 0, 1);
  }

  /**
   * Prepares main labels
   */
  private void prepareMainLabels() {
    nickName = createLabel(lang.get("playerProfile.labels.profileNickName"), prepareFontForMainLabels());
    informationLabel = createLabel(lang.get("playerProfile.labels.profileInformation"), prepareFontForMainLabels());
  }

  /**
   * Prepares right region
   */
  private void prepareRegion() {
    betweenInformationCompHeading = createRegion(20);
    informationOnRight = createRegion(25);
  }

  /**
   * Prepares vertical VBox for heading
   */
  private void prepareVerticalForHeading() {
    basicInformation = new VBox(10);
    basicInformation.setMinWidth(300);
    basicInformation.setPadding(new Insets(50, 25, 50, 25));
    basicInformation.getChildren().addAll(betweenInformationCompHeading, informationLabel, informationInside);
  }

  /**
   * Prepares vertical VBox for information
   */
  private void prepareVerticalForInformation() {
    informationVBox = new VBox(10);
    informationVBox.setMaxWidth(Double.MAX_VALUE);
    informationVBox.setPadding(new Insets(25, 10, 25, 10));
    informationVBox.getChildren().addAll(name, surname, age, dateOfBirth, email);
  }

  /**
   * Set email to specific label
   *
   * @param emailText - text containing user email
   */
  public void setEmail(String emailText) {
    email.setText(emailText);
  }

  /**
   * Set age to specific label
   *
   * @param ageText - text containing user age
   */
  public void setLoadedAge(String ageText) {
    age.setText(lang.get("playerProfile.set.age") + ageText);
  }

  /**
   * Set date of birth to specific label
   *
   * @param dateOfBirthText - text containing user birth
   */
  public void setLoadedDateOfBirth(String dateOfBirthText) {
    dateOfBirth.setText(lang.get("playerProfile.set.dateOfBirth") + dateOfBirthText);
  }

  /**
   * Loads image from specified path
   *
   * @param imagePath - image path for stored image
   */
  public void setLoadedImage(String imagePath) {
    System.out.println(imagePath);
    photoImage = new Image(imagePath);
    selfPhoto.setImage(photoImage);
    selfPhoto.setFitWidth(PICTURE_WIDTH);
    selfPhoto.setFitHeight(PICTURE_HEIGHT);
  }

  /**
   * Set name into specific label
   *
   * @param nameText - specific text containing name
   */
  public void setLoadedName(String nameText) {
    name.setText(lang.get("playerProfile.set.name") + nameText);
  }

  /**
   * Set surname into specific label
   *
   * @param surnameText - specific text containing surname
   */
  public void setLoadedSurname(String surnameText) {
    surname.setText(lang.get("playerProfile.set.surname") + surnameText);
  }

  /**
   * Set nickName into specific label
   *
   * @param nickNameText - specific text containing nickname
   */
  public void setNickName(String nickNameText) {
    nickName.setText(nickNameText);
  }


  public void  updateInformationPlayerProfileScreen(User user){
    name.setText(lang.get("playerProfile.set.name") + user.getName());
    surname.setText(lang.get("playerProfile.set.surname") + user.getSurname());
    dateOfBirth.setText(lang.get("playerProfile.set.dateOfBirth") + user.getDateOfBirth());
    age.setText(lang.get("playerProfile.set.age") + user.getAge());
    //avatar = user.getAvatar();
  }
}
