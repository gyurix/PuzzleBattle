package org.puzzlebattle.client.screen;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import org.puzzlebattle.core.utils.Logging;

import java.util.Random;

public class PlayerProfileScreen extends AbstractScreen{

  private Label nickName;
  private Label informationLabel;
  private Label name, surname;
  private Label age;
  private Label dateOfBirth;
  private Label email;
  private ImageView selfPhoto;
  private VBox basicInformation;
  private VBox informationVBox;
  private HBox informationInside;
  private Button returnButton;
  private GridPane grid;
  private Image photoImage;
  private Stage stage;
  private Region betweenInformationCompHeading;
  private Region informationOnRight;
  private Scene scene;

  private static int PICTURE_WIDTH = 300;
  private static int PICTURE_HEIGHT = 350;

  public PlayerProfileScreen(Stage stage){
    super(stage);
    this.stage = stage;
    prepareComponents();
    prepareScene();
    Logging.logInfo("Player screen has been created.");
  }

  public void show() {
    stage.setTitle("Player profile");
    stage.setOnCloseRequest(e->stage.close());
    stage.sizeToScene();
    stage.show();
  }

  private void prepareComponents() {
    prepareMainLabels();
    prepareInformationLabels();
    prepareButtonsAndPhoto();
    prepareRegion();
    prepareLayouts();
  }

  private Font prepareFontForMainLabels() { return Font.font("Courier", FontWeight.BOLD, FontPosture.ITALIC, 30); }

  private Font prepareFontForLabels(){ return Font.font("Arial", FontPosture.ITALIC, 20); }

  private void prepareMainLabels(){
    nickName = new Label("Profile Nick name");
    nickName.setMaxWidth(Double.MAX_VALUE);
    nickName.setFont(prepareFontForMainLabels());
    informationLabel= new Label("Profile information");
    informationLabel.setMaxWidth(Double.MAX_VALUE);
    informationLabel.setFont(prepareFontForMainLabels());
  }

  private void prepareInformationLabels(){
    name = new Label("name");
    name.setMaxWidth(Double.MAX_VALUE);
    name.setFont(prepareFontForLabels());
    surname = new Label("surname");
    surname.setMaxWidth(Double.MAX_VALUE);
    surname.setFont(prepareFontForLabels());
    age= new Label("age");
    age.setMaxWidth(Double.MAX_VALUE);
    age.setFont(prepareFontForLabels());
    dateOfBirth = new Label("date of birth");
    dateOfBirth.setMaxWidth(Double.MAX_VALUE);
    dateOfBirth.setFont(prepareFontForLabels());
    email= new Label("email");
    email.setMaxWidth(Double.MAX_VALUE);
    email.setFont(prepareFontForLabels());
  }

  private void prepareButtonsAndPhoto(){
    Random r = new Random(10);
    int pictureNumber=  r.nextInt(10);
    photoImage= new Image("faces/face"+pictureNumber+".png");
    selfPhoto = new ImageView(photoImage);
    selfPhoto.setFitWidth(PICTURE_WIDTH);
    selfPhoto.setFitHeight(PICTURE_HEIGHT);

    returnButton = new Button("Return");
    returnButton.setMinWidth(100);
  }

  private void prepareRegion(){
    betweenInformationCompHeading = new Region();
    betweenInformationCompHeading.setMinHeight(20);
    informationOnRight = new Region();
    informationOnRight.setMinWidth(25);
  }

  private void prepareLayouts(){
    prepareLayoutWithInformationComponents();
    grid = new GridPane();
    grid.setPadding(new Insets(20,0,50,0));
    grid.add(nickName,0,0);
    grid.add(selfPhoto,1,1);
    grid.add(basicInformation,0,1);

  }

  private void prepareLayoutWithInformationComponents(){
    prepareVerticalForInformation();
    prepareHorizontalForContent();
    prepareVerticalForHeading();
  }

  private void prepareHorizontalForContent(){
    informationInside= new HBox(10);
    informationInside.setMinWidth(300);
    informationInside.getChildren().addAll(informationOnRight,informationVBox);
  }

  private void prepareVerticalForHeading(){
    basicInformation = new VBox(10);
    basicInformation.setMinWidth(300);
    basicInformation.setPadding(new Insets(50,25,50,25));
    basicInformation.getChildren().addAll(betweenInformationCompHeading,informationLabel,informationInside);
  }

  private void prepareVerticalForInformation(){
    informationVBox = new VBox(10);
    informationVBox.setMaxWidth(Double.MAX_VALUE);
    informationVBox.setPadding(new Insets(25,10,25,10));
    informationVBox.getChildren().addAll(name,surname,age,dateOfBirth,email);
  }

  private void prepareScene(){
    scene= new Scene(grid,super.getWidth(),super.getHeight());
    stage.setScene(scene);
  }

  public void setLoadedImage(String imagePath){
    System.out.println(imagePath);
    photoImage= new Image(imagePath);
    selfPhoto.setImage(photoImage);
    selfPhoto.setFitWidth(PICTURE_WIDTH);
    selfPhoto.setFitHeight(PICTURE_HEIGHT);
  }

  public void setLoadedName(String nameText){
    name.setText("Name: "+nameText);
  }

  public void setLoadedSurname(String surnameText){
    surname.setText("Surname: "+surnameText);
  }

  public void setLoadedAge(String ageText){
    age.setText("Age: "+ageText);
  }

  public void setLoadedDateOfBirth(String dateOfBirthText){
    dateOfBirth.setText("Date: "+dateOfBirthText);
  }

  public void setNickName(String nickNameText){
    nickName.setText(nickNameText);
  }

  public void setEmail(String emailText){
    email.setText(emailText);
  }
}
