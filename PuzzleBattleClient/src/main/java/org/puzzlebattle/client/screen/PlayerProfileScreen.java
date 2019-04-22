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
import org.puzzlebattle.core.utils.Logging;

import java.util.Random;

import static org.puzzlebattle.core.utils.LangFile.lang;

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

  public PlayerProfileScreen(Stage stage) {
    super(stage);
    prepareComponents();
    this.pane = grid;
    Logging.logInfo("Player screen has been created.");
  }

  private Label createLabel(String text, Font font) {
    Label label = new Label(text);
    label.setMaxWidth(Double.MAX_VALUE);
    label.setFont(font);
    return label;
  }

  private Region createRegion(double minHeight) {
    Region region = new Region();
    region.setMinHeight(minHeight);
    return region;
  }

  public String getTitle() {
    return lang.get("playerProfile.title");
  }

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

  private void prepareComponents() {
    prepareMainLabels();
    prepareInformationLabels();
    prepareButtonsAndPhoto();
    prepareRegion();
    prepareLayouts();
  }

  private Font prepareFontForLabels() {
    return Font.font("Arial", FontPosture.ITALIC, 20);
  }

  private Font prepareFontForMainLabels() {
    return Font.font("Courier", FontWeight.BOLD, FontPosture.ITALIC, 30);
  }

  private void prepareHorizontalForContent() {
    informationInside = new HBox(10);
    informationInside.setMinWidth(300);
    informationInside.getChildren().addAll(informationOnRight, informationVBox);
  }

  private void prepareInformationLabels() {
    name = createLabel(lang.get("playerProfile.name"), prepareFontForLabels());
    surname = createLabel(lang.get("playerProfile.surname"), prepareFontForLabels());
    age = createLabel(lang.get("playerProfile.age"), prepareFontForLabels());
    dateOfBirth = createLabel(lang.get("playerProfile.dateOfBirth"), prepareFontForLabels());
    email = createLabel(lang.get("playerProfile.email"), prepareFontForLabels());
  }

  private void prepareLayoutWithInformationComponents() {
    prepareVerticalForInformation();
    prepareHorizontalForContent();
    prepareVerticalForHeading();
  }

  private void prepareLayouts() {
    prepareLayoutWithInformationComponents();
    grid = new GridPane();
    grid.setPadding(new Insets(20, 0, 50, 0));
    grid.add(nickName, 0, 0);
    grid.add(selfPhoto, 1, 1);
    grid.add(basicInformation, 0, 1);
  }

  private void prepareMainLabels() {
    nickName = createLabel(lang.get("playerProfile.labels.profileNickName"), prepareFontForMainLabels());
    informationLabel = createLabel(lang.get("playerProfile.labels.profileInformation"), prepareFontForMainLabels());
  }

  private void prepareRegion() {
    betweenInformationCompHeading = createRegion(20);
    informationOnRight = createRegion(25);
  }

  private void prepareVerticalForHeading() {
    basicInformation = new VBox(10);
    basicInformation.setMinWidth(300);
    basicInformation.setPadding(new Insets(50, 25, 50, 25));
    basicInformation.getChildren().addAll(betweenInformationCompHeading, informationLabel, informationInside);
  }

  private void prepareVerticalForInformation() {
    informationVBox = new VBox(10);
    informationVBox.setMaxWidth(Double.MAX_VALUE);
    informationVBox.setPadding(new Insets(25, 10, 25, 10));
    informationVBox.getChildren().addAll(name, surname, age, dateOfBirth, email);
  }

  public void setEmail(String emailText) {
    email.setText(emailText);
  }

  public void setLoadedAge(String ageText) {
    age.setText(lang.get("playerProfile.set.age") + ageText);
  }

  public void setLoadedDateOfBirth(String dateOfBirthText) {
    dateOfBirth.setText(lang.get("playerProfile.set.date") + dateOfBirthText);
  }

  public void setLoadedImage(String imagePath) {
    System.out.println(imagePath);
    photoImage = new Image(imagePath);
    selfPhoto.setImage(photoImage);
    selfPhoto.setFitWidth(PICTURE_WIDTH);
    selfPhoto.setFitHeight(PICTURE_HEIGHT);
  }

  public void setLoadedName(String nameText) {
    name.setText(lang.get("playerProfile.set.name") + nameText);
  }

  public void setLoadedSurname(String surnameText) {
    surname.setText(lang.get("playerProfile.set.surname") + surnameText);
  }

  public void setNickName(String nickNameText) {
    nickName.setText(nickNameText);
  }

}
