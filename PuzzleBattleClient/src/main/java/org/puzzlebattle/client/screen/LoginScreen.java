package org.puzzlebattle.client.screen;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;


/**
 * Dialog, screen, where user logs in his/her account to play games.
 *
 * @author (Juraj Barath)
 * @version (1.0)
 */

public class LoginScreen extends AbstractScreen {

  private BorderPane borderPane;
  private Button confirmButton;
  private Region loginButtonRegion, loginPasswordRegion, registerRegion;
  private Label loginLabel;
  private VBox loginPanel;
  private TextField loginTextField;
  private String obtainedPassword, obtainedLogin;
  private PasswordField passwordField;
  private Label passwordLabel;
  private Button registerButton;
  private HBox registerPanel;
  private Scene sceneLogin;
  private Label separatorLeft, separatorRight;
  private Stage stage;

  /**
   * Constructor which creates screen for log in
   */

  public LoginScreen(Stage stage) {
    super(stage);
    this.stage = stage;
    createComponentsForLoginScreen();
    prepareScreenAndPane(stage);
  }

  private void createComponentsForLoginScreen() {

    prepareButtonsAndLabels();
    prepareEffectSeparators();
    prepareRegions();
    registerPanel = new HBox(0);
    HBox.setHgrow(registerRegion, Priority.ALWAYS);
    registerPanel.getChildren().addAll(registerRegion, registerButton);
  }

  private void prepareBorderPanel() {

    borderPane = new BorderPane();

    borderPane.setCenter(loginPanel);
    borderPane.setBottom(registerPanel);
    borderPane.setLeft(separatorLeft);
    borderPane.setRight(separatorRight);
  }

  private void prepareButtonsAndLabels() {

    prepareLoginLabel();
    loginTextField = new TextField("Put your login here!");
    preparePasswordLabel();
    passwordField = new PasswordField();

    prepareConfirmButton();
    prepareRegisterButton();
  }

  private void prepareConfirmButton() {
    confirmButton = new Button("Login");
    confirmButton.setMaxWidth(Double.MAX_VALUE);
    confirmButton.setOnAction(e -> verifyLogin());
  }

  private void prepareEffectSeparators() {
    separatorLeft = new Label();
    separatorRight = new Label();
    separatorRight.setMaxHeight(Double.MAX_VALUE);
    separatorRight.setMaxHeight(Double.MAX_VALUE);
    separatorLeft.setMinWidth(50);
    separatorRight.setMinWidth(50);
  }

  private void prepareLoginLabel() {
    loginLabel = new Label("Login");
    loginLabel.setFont(super.getDefaultFont());
  }

  private void prepareLoginPanel() {
    loginPanel = new VBox(10);
    loginPanel.setPadding(super.createDefaultInsets());

    loginPanel.getChildren().addAll(loginLabel, loginTextField, loginPasswordRegion,
            passwordLabel, passwordField, loginButtonRegion, confirmButton);
  }

  private void preparePasswordLabel() {
    passwordLabel = new Label("Password");
    passwordLabel.setFont(super.getDefaultFont());
  }

  private void prepareRegions() {
    loginButtonRegion = new Region();
    loginPasswordRegion = new Region();
    loginButtonRegion.setMinHeight(100);
    loginPasswordRegion.setMinHeight(10);
    registerRegion = new Region();
    registerRegion.setMaxWidth(Double.MAX_VALUE);
  }

  private void prepareRegisterButton() {
    registerButton = new Button("Register");
    registerButton.setMinWidth(200);
    registerButton.setOnAction(e -> new RegisterScreen(stage).show());
  }

  private void prepareScreenAndPane(Stage stage) {
    prepareLoginPanel();
    prepareBorderPanel();

    sceneLogin = new Scene(borderPane, super.getWidth(), super.getHeight());
    stage.setScene(sceneLogin);
  }

  public void show() {
    stage.setScene(sceneLogin);
    stage.setTitle("Login to the Puzzle Battle.");
    stage.setOnCloseRequest(e -> stage.close());
    stage.sizeToScene();
    stage.show();
  }

  private void verifyLogin() {
    try {
      obtainedPassword = passwordField.getText();
      obtainedLogin = loginTextField.getText();
      new MainScreen(stage, new SettingsForScreens()).show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
