package org.puzzlebattle.client.screen;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.puzzlebattle.client.databaseTables.LoginRegisterUser;
import org.puzzlebattle.client.databaseTables.UserPuzzleBattle;
import org.puzzlebattle.core.utils.Logging;

import static org.puzzlebattle.core.utils.LangFile.lang;


/**
 * Dialog, screen, where user logs in his/her account to play games.
 *
 * @author (Juraj Barath, Jakub Perdek)
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

  private UserPuzzleBattle user;

  /**
   * Constructor which creates screen for log in
   */
  public LoginScreen(Stage stage) {
    super(stage);
    createComponentsForLoginScreen();
    prepareScreenAndPane(stage);
    Logging.logInfo("Login screen created.");
  }

  /**
   * Creates components for logging screen, for example separators, panels, buttons and labels
   */
  private void createComponentsForLoginScreen() {
    prepareButtonsAndLabels();
    prepareEffectSeparators();
    prepareRegions();
    registerPanel = new HBox(0);
    HBox.setHgrow(registerRegion, Priority.ALWAYS);
    registerPanel.getChildren().addAll(registerRegion, registerButton);
  }

  private Label createLabel(String key) {
    Label label = new Label(lang.get(key));
    label.setFont(getDefaultFont());
    return label;
  }

  /**
   * Preparing border panel as main panel to situate other components on it
   */
  private void prepareBorderPanel() {

    borderPane = new BorderPane();

    borderPane.setCenter(loginPanel);
    borderPane.setBottom(registerPanel);
    borderPane.setLeft(separatorLeft);
    borderPane.setRight(separatorRight);
  }

  /**
   * Preparing buttons and labels for login screen
   */
  private void prepareButtonsAndLabels() {
    loginLabel = createLabel("login.username");
    loginTextField = new TextField(lang.get("login.usernameText"));
    passwordLabel = createLabel("login.password");
    passwordField = new PasswordField();

    prepareConfirmButton();
    prepareRegisterButton();
  }

  /**
   * Method which prepares confirm button
   */
  private void prepareConfirmButton() {
    confirmButton = new Button("Login");
    confirmButton.setMaxWidth(Double.MAX_VALUE);
    confirmButton.setOnAction(e -> verifyLogin());
  }

  /**
   * Prepare effect separators
   */
  private void prepareEffectSeparators() {
    separatorLeft = new Label();
    separatorRight = new Label();
    separatorRight.setMaxHeight(Double.MAX_VALUE);
    separatorRight.setMaxHeight(Double.MAX_VALUE);
    separatorLeft.setMinWidth(50);
    separatorRight.setMinWidth(50);
  }

  /**
   * Prepare login panel and its components
   */
  private void prepareLoginPanel() {
    loginPanel = new VBox(10);
    loginPanel.setPadding(super.createDefaultInsets());

    loginPanel.getChildren().addAll(loginLabel, loginTextField, loginPasswordRegion,
            passwordLabel, passwordField, loginButtonRegion, confirmButton);
  }

  /**
   * Prepare password label
   */
  private void preparePasswordLabel() {
    passwordLabel = new Label("Password");
    passwordLabel.setFont(super.getDefaultFont());
  }

  /**
   * Prepare regions
   */
  private void prepareRegions() {
    loginButtonRegion = new Region();
    loginPasswordRegion = new Region();
    loginButtonRegion.setMinHeight(100);
    loginPasswordRegion.setMinHeight(10);
    registerRegion = new Region();
    registerRegion.setMaxWidth(Double.MAX_VALUE);
  }

  /**
   * Prepare button for registration
   */
  private void prepareRegisterButton() {
    registerButton = new Button("Register");
    registerButton.setMinWidth(200);
    registerButton.setOnAction(e -> new RegisterScreen(stage).show());
  }

  /**
   * Preparing  screen and pane
   *
   * @param stage
   */
  private void prepareScreenAndPane(Stage stage) {
    prepareLoginPanel();
    prepareBorderPanel();

    sceneLogin = new Scene(borderPane, super.getWidth(), super.getHeight());
    stage.setScene(sceneLogin);
  }

  /**
   * Showing of login window
   */
  public void show() {
    stage.setScene(sceneLogin);
    stage.setTitle("Login to the Puzzle Battle.");
    stage.setOnCloseRequest(e -> stage.close());
    stage.sizeToScene();
    stage.show();
  }

  /**
   * Verification of login is done here
   */
  private void verifyLogin() {
    try {
      obtainedPassword = passwordField.getText();
      obtainedLogin = loginTextField.getText();
      if ((user = LoginRegisterUser.getRegisterUser(obtainedLogin, obtainedPassword)) != null) {
        Logging.logInfo("User successfully authenticated!");
        new MainScreen(stage, new SettingsForScreens(), user).show();
      }
    } catch (Exception e) {
      Logging.logWarning("Authentication failed|", e);
    }
  }
}
