package org.puzzlebattle.client.screen;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;


/**
 * Class where screen will be registered
 *
 * @author (Jakub Perdek,Juraj Barath)
 * @version (1.0)
 */

public class RegisterScreen extends AbstractScreen {

  private Label nickLabel;
  private Label passwordLabel;
  private Label passwordConfLabel;
  private Label emailLabel;
  private Label leftLabel;
  private Label rightLabel;
  private PasswordField passwordField;
  private PasswordField confirmPasswordField;
  private TextField nickText;
  private TextField emailText;
  private Button confirmButton;
  private BorderPane border;
  private VBox registerComponents;
  private Scene registerScene;
  private Stage stage;
  private Separator leftSeparator, rightSeparator;
  private HBox leftHBox, rightHBox;
  private Region regionNickEmail, regionEmailPassword, regionBetweenPassword;
  private Region regionUpConfirmButton;
  private static int ADDITIONAL_HEIGHT = 100;
  /**
   * Constructor which apply registering of the screen
   */

  RegisterScreen(Stage stage) {
    super(stage);
    this.stage=stage;
    prepareComponents();
    registerScene = new Scene(border,super.getWidth(),super.getHeight()+ADDITIONAL_HEIGHT);
  }

  public void show() {
    stage.setScene(registerScene);
    stage.setTitle("Register Puzzle Battle");
    stage.show();
  }

  private void prepareComponents() {
    prepareLabelsWithUserInf();
    prepareTextAndPasswordFields();
    prepareRegions();
    prepareConfirmButton();
    prepareSpecialSideComponents();
    prepareLayoutForRegisterComponents();
    prepareMainLayout();
  }

  private void prepareLayoutForRegisterComponents() {
    registerComponents = new VBox(10);
    registerComponents.setMinWidth(super.getWidth()-200);
    registerComponents.setPadding(super.createDefaultInsets());
    registerComponents.getChildren().addAll(nickLabel,nickText,regionNickEmail, emailLabel,emailText,
            regionEmailPassword,passwordLabel,passwordField,regionBetweenPassword, passwordConfLabel,
            confirmPasswordField,regionUpConfirmButton,confirmButton);
  }

  private void prepareLabelsWithUserInf() {
    nickLabel = new Label("Your nick");
    nickLabel.setMaxWidth(Double.MAX_VALUE);
    nickLabel.setFont(super.getDefaultFont());
    passwordLabel= new Label("Your password");
    passwordLabel.setMaxWidth(Double.MAX_VALUE);
    passwordLabel.setFont(super.getDefaultFont());
    passwordConfLabel = new Label("Confirm password");
    passwordConfLabel.setMaxWidth(Double.MAX_VALUE);
    passwordConfLabel.setFont(super.getDefaultFont());
    emailLabel = new Label("Yout email");
    emailLabel.setMinWidth(Double.MAX_VALUE);
    emailLabel.setFont(super.getDefaultFont());
  }

  private void prepareTextAndPasswordFields() {
    nickText = new TextField("Add your nick");
    emailText = new TextField("Add your email");
    passwordField = new PasswordField();
    confirmPasswordField = new PasswordField();
  }

  private void prepareRegions() {
    regionBetweenPassword = new Region();
    regionBetweenPassword.setMinHeight(10);
    regionEmailPassword = new Region();
    regionEmailPassword.setMinHeight(10);
    regionNickEmail = new Region();
    regionNickEmail.setMinHeight(10);
    regionUpConfirmButton = new Region();
    regionUpConfirmButton.setMinHeight(50);
  }

  private void prepareConfirmButton() {
    confirmButton = new Button("Register");
    confirmButton.setMaxWidth(Double.MAX_VALUE);
    confirmButton.setOnAction(e->obtainData());
  }

  private void prepareSpecialSideLabels() {
    leftLabel = new Label("PUZZLE");
    leftLabel.setFont(specialFontForSideLabels());
    leftLabel.setWrapText(true);
    leftLabel.setAlignment(Pos.CENTER_RIGHT);
    leftLabel.setContentDisplay(ContentDisplay.TOP);
    leftLabel.setPrefWidth(50);
    leftLabel.setPadding(new Insets(10,10,100,20));

    rightLabel = new Label("BATTLE");
    rightLabel.setFont(specialFontForSideLabels());
    rightLabel.setWrapText(true);
    rightLabel.setAlignment(Pos.BOTTOM_RIGHT);
    rightLabel.setPrefWidth(50);
    rightLabel.setContentDisplay(ContentDisplay.BOTTOM);
    rightLabel.setPadding(new Insets(100,10,20,20));
  }

  private void prepareSpecialSideComponents() {
    prepareSpecialSideLabels();

    leftSeparator = new Separator();
    rightSeparator = new Separator();

    leftHBox = new HBox();
    leftHBox.getChildren().addAll(leftSeparator,leftLabel);

    rightHBox = new HBox();
    rightHBox.getChildren().addAll(rightSeparator,rightLabel);
  }

  private Font specialFontForSideLabels() {
    return Font.font("Lithograph", FontWeight.BOLD, 55);
  }

  private void prepareMainLayout() {
    border = new BorderPane();
    border.setLeft(leftHBox);
    border.setRight(rightHBox);
    border.setCenter(registerComponents);
  }

  private void obtainData()
  {
    String nickName = nickText.getText();
    String email = emailText.getText();
    String password = passwordField.getText();
    String passwordConfirm = confirmPasswordField.getText();
    Alert passwdsAreNotSame;

    if(!password.equals(passwordConfirm))
    {
      passwdsAreNotSame= new Alert(Alert.AlertType.ERROR);
      passwdsAreNotSame.setTitle("Not same passwords");
      passwdsAreNotSame.setContentText("Type your password and confirm password again!");
      passwdsAreNotSame.showAndWait();
      return;
    }

    new AdditionalInformationScreen(stage,nickName,email).show();
  }
}
