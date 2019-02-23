package org.puzzlebattle.client.screen;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
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

    private PasswordField passwordField;
    private TextField loginTextField;
    private Label loginLabel;
    private Label passwordLabel;
    private Scene sceneLogin;
    private BorderPane  borderPane;
    private VBox loginPanel;
    private HBox registerPanel;
    private Button confirmButton;
    private Button registerButton;
    private Stage stage;
    private String obtainedPassword, obtainedLogin;
    private Label separatorLeft;
    private Label separatorRight;
    private Region loginButtonRegion, loginPasswordRegion, registerRegion;

  /**
   * Constructor which creates screen for log in
   */

  public LoginScreen(Stage stage) {
    super(stage);
    this.stage = stage;
    createComponentsForLoginScreen();
    prepareScreenAndPane(stage);
  }

  public void show() {
    stage.setScene(sceneLogin);
    stage.setTitle("Login to the Puzzle Battle.");
    stage.setOnCloseRequest(e->stage.close());
    stage.sizeToScene();
    stage.show();
  }

  private void prepareScreenAndPane(Stage stage) {
    prepareLoginPanel();
    prepareBorderPanel();

    sceneLogin = new Scene(borderPane,super.getWidth(),super.getHeight());
    stage.setScene(sceneLogin);
  }

  private void prepareBorderPanel(){

    borderPane= new BorderPane();

    borderPane.setCenter(loginPanel);
    borderPane.setBottom(registerPanel);
    borderPane.setLeft(separatorLeft);
    borderPane.setRight(separatorRight);
  }

  private void prepareLoginPanel(){
    loginPanel = new VBox(10);
    loginPanel.setPadding(new Insets(50,100,50,100));

    loginPanel.getChildren().addAll(loginLabel,loginTextField,loginPasswordRegion,
            passwordLabel,passwordField,loginButtonRegion,confirmButton);
  }

  private void createComponentsForLoginScreen() {

    prepareButtonsAndLabels();
    prepareEffectSeparators();
    prepareRegions();
    registerPanel = new HBox(0);
    HBox.setHgrow(registerRegion, Priority.ALWAYS);
    registerPanel.getChildren().addAll(registerRegion,registerButton);
  }

  private void prepareButtonsAndLabels(){

    prepareLoginLabel();
    loginTextField = new TextField("Put your login here!");
    preparePasswordLabel();
    passwordField = new PasswordField();

    prepareConfirmButton();
    prepareRegisterButton();
  }

  private void prepareLoginLabel(){
    loginLabel = new Label("Login");
    loginLabel.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.ITALIC,25));
  }

  private void preparePasswordLabel(){
    passwordLabel = new Label("Password");
    passwordLabel.setFont(Font.font("Arial",FontWeight.BOLD, FontPosture.ITALIC,25));
  }

  private void prepareConfirmButton(){
    confirmButton = new Button("Login");
    confirmButton.setMaxWidth(Double.MAX_VALUE);
    confirmButton.setOnAction(e->verifyLogin());
  }

  private void prepareRegisterButton(){
    registerButton = new Button("Register");
    registerButton.setMinWidth(200);
    registerButton.setOnAction(e-> new RegisterScreen(stage));
  }
  private void prepareEffectSeparators(){
    separatorLeft = new Label();
    separatorRight= new Label();
    separatorRight.setMaxHeight(Double.MAX_VALUE);
    separatorRight.setMaxHeight(Double.MAX_VALUE);
    separatorLeft.setMinWidth(50);
    separatorRight.setMinWidth(50);
  }

  private void prepareRegions() {
    loginButtonRegion = new Region();
    loginPasswordRegion = new Region();
    loginButtonRegion.setMinHeight(100);
    loginPasswordRegion.setMinHeight(10);
    registerRegion = new Region();
    registerRegion.setMaxWidth(Double.MAX_VALUE);
  }

  private void verifyLogin() {
    try {
      obtainedPassword = passwordField.getText();
      obtainedLogin = loginTextField.getText();
      new MainScreen(stage,new SettingsForScreens()).show();
    }
    catch(Exception e){
      e.printStackTrace();
    }
  }
}
