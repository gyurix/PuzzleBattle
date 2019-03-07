package org.puzzlebattle.client.screen;

import javafx.scene.Scene;
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

public class AdditionalInformationScreen extends AbstractScreen {

  private Scene scene1, scene2;
  private Button pageNextScene1, pagePreviousScene1;
  private Button pageNextScene2, pagePreviousScene2;
  private Region bottomLeftScene1, bottomLeftScene2;
  private Region leftConfirmScene1, leftConfirmScene2;
  private Region regionNameSurname, regionSurnameDateOfBirth;
  private Button confirmScene1, confirmScene2;
  private Button loadPhoto;
  private VBox mainComponentsScene1;
  private VBox mainComponentsScene2;
  private HBox movePageScene1, movePageScene2;
  private BorderPane borderScene1, borderScene2;
  private Label addPhotoLabel;
  private Label addNameLabel;
  private Label addSurnameLabel;
  private Label pathForPhotoLabel;
  private Label addDateOfBirth;
  private TextField nameText;
  private TextField dateOfBirthText;
  private TextField surnameText;
  private Label leftSideLabelScene1, leftSideLabelScene2;
  private Label rightSideLabelScene1, rightSideLabelScene2;
  private Stage stage;

  public AdditionalInformationScreen(Stage stage) {
    super(stage);
    this.stage=stage;
    prepareComponents();
    prepareScenes();
    stage.setTitle("Additional information of player");
  }

  public void show(){
    stage.setScene(scene1);
    stage.show();
  }

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

  private void prepareScenes(){
    scene1 = new Scene(borderScene1,super.getWidth(),super.getHeight());
    scene2 = new Scene(borderScene2,super.getWidth(),super.getHeight());
  }

  private void prepareRegionForScene1(){
    regionSurnameDateOfBirth = new Region();
    regionSurnameDateOfBirth.setMinHeight(10);
    regionNameSurname = new Region();
    regionNameSurname.setMinHeight(10);
    bottomLeftScene1 = new Region();
    bottomLeftScene1.setMinWidth(100);
    leftConfirmScene1 = new Region();
    leftConfirmScene1.setMinWidth(50);
  }

  private void prepareRegionsForScene2(){
    bottomLeftScene2 = new Region();
    bottomLeftScene2.setMinWidth(100);
    leftConfirmScene2 = new Region();
    leftConfirmScene2.setMinWidth(50);
  }

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

  private void prepareLabelsForScene2(){
    prepareSideLabelsForScene2();
    addPhotoLabel = new Label("Your photo");
    addPhotoLabel.setFont(super.getDefaultFont());
    addPhotoLabel.setMaxWidth(Double.MAX_VALUE);
    pathForPhotoLabel = new Label("No image is loaded");
    pathForPhotoLabel.setFont(Font.font("Courier", FontPosture.ITALIC, 15));
    pathForPhotoLabel.setMaxWidth(Double.MAX_VALUE);
  }

  private void prepareSideLabelsForScene1(){
    leftSideLabelScene1 = new Label();
    leftSideLabelScene1.setMinWidth(50);
    rightSideLabelScene1 = new Label();
    rightSideLabelScene1.setMinWidth(50);
  }

  private void prepareSideLabelsForScene2(){
    leftSideLabelScene2 = new Label();
    leftSideLabelScene2.setMinWidth(50);
    rightSideLabelScene2 = new Label();
    rightSideLabelScene2.setMinWidth(50);
  }

  private void prepareTextFieldsForScene1() {
    nameText = new TextField("Add your name");
    surnameText = new TextField("Add your surname");
    dateOfBirthText = new TextField("Add your birth");
  }

  private void prepareLayoutsForScene1(){
    prepareMovingPageLayoutScene1();
    prepareMainComponentLayoutScene1();

    borderScene1 = new BorderPane();
    borderScene1.setBottom(movePageScene1);
    borderScene1.setCenter(mainComponentsScene1);
    borderScene1.setLeft(leftSideLabelScene1);
    borderScene1.setRight(rightSideLabelScene1);
  }

  private void prepareMovingPageLayoutScene1() {
    movePageScene1 = new HBox(10);
    movePageScene1.setMinHeight(50);
    movePageScene1.getChildren().addAll(leftConfirmScene1,confirmScene1, bottomLeftScene1, pageNextScene1, pagePreviousScene1);
  }

  private void prepareMovingPageLayoutScene2(){
    movePageScene2 = new HBox(10);
    movePageScene2.setMinHeight(50);
    movePageScene2.getChildren().addAll(leftConfirmScene2,confirmScene2,bottomLeftScene2, pageNextScene2, pagePreviousScene2);
  }

  private void prepareLayoutsForScene2(){
    prepareMovingPageLayoutScene2();
    prepareMainComponentLayoutScene2();

    borderScene2 = new BorderPane();
    borderScene2.setBottom(movePageScene2);
    borderScene2.setCenter(mainComponentsScene2);
    borderScene2.setLeft(leftSideLabelScene2);
    borderScene2.setRight(rightSideLabelScene2);
  }

  private void prepareMainComponentLayoutScene1(){
    mainComponentsScene1 = new VBox(10);
    mainComponentsScene1.setMinWidth(super.getWidth()-200);
    mainComponentsScene1.getChildren().addAll(addNameLabel, nameText, regionNameSurname, addSurnameLabel, surnameText, regionSurnameDateOfBirth,
            addDateOfBirth, dateOfBirthText);
    mainComponentsScene1.setPadding(super.createDefaultInsets());
  }

  private void prepareMainComponentLayoutScene2(){
    mainComponentsScene2 = new VBox(10);
    mainComponentsScene2.setMinWidth(super.getWidth()-200);
    mainComponentsScene2.getChildren().addAll(addPhotoLabel, pathForPhotoLabel, loadPhoto);
    mainComponentsScene2.setPadding(super.createDefaultInsets());
  }

  private void prepareButtonsForScene1(){
    pageNextScene1 = new Button("Next");
    pageNextScene1.setOnAction(e->stage.setScene(scene2));
    pageNextScene1.setMinWidth(100);
    pagePreviousScene1 =new Button("Previous");
    pagePreviousScene1.setDisable(true);
    pagePreviousScene1.setMinWidth(100);
    confirmScene1 = new Button("Confirm");
    confirmScene1.setMinWidth(200);
  }

  private void prepareButtonsForScene2(){
    pageNextScene2 = new Button("Next");
    pageNextScene2.setDisable(true);
    pageNextScene2.setMinWidth(100);
    pagePreviousScene2 = new Button("Previous");
    pagePreviousScene2.setOnAction(e->stage.setScene(scene1));
    pagePreviousScene2.setMinWidth(100);
    confirmScene2 = new Button("Confirm");
    confirmScene2.setMinWidth(200);
    loadPhoto = new Button("Load photo");
    loadPhoto.setMaxWidth(Double.MAX_VALUE);
  }
}
