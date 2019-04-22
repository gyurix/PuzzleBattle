package org.puzzlebattle.client.screen;

import javafx.event.Event;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.puzzlebattle.client.databaseTables.LoginRegisterUser;

import static javafx.scene.control.Alert.AlertType.ERROR;
import static javafx.scene.control.Alert.AlertType.INFORMATION;
import static org.puzzlebattle.core.utils.LangFile.lang;
import static org.puzzlebattle.core.utils.Logging.logInfo;


/**
 * Dialog, screen, where user logs in his/her account to play games.
 *
 * @author (Juraj Barath, Jakub Perdek)
 * @version (1.0)
 */
public class LoginScreen extends AbstractScreen {

  private Button confirmButton;
  private Region loginButtonRegion, loginPasswordRegion, registerRegion;
  private Label loginLabel;
  private VBox loginPanel;
  private TextField loginTextField;
  private PasswordField passwordField;
  private Label passwordLabel;
  private String pwd, login;
  private Button registerButton;
  private HBox registerPanel;
  private Label separatorLeft, separatorRight;

  /**
   * Constructor which creates screen for log in
   */
  public LoginScreen(Stage stage) {
    super(stage);
    createComponentsForLoginScreen();
    prepareScreenAndPane(stage);
    logInfo("Login screen created.");
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

  private Label createLabel(String text, double minWidth) {
    Label label = new Label(text);
    label.setMaxHeight(Double.MAX_VALUE);
    label.setMinWidth(minWidth);
    return label;
  }

  private Region createRegion(double minHeight) {
    Region region = new Region();
    region.setMinHeight(minHeight);
    return region;
  }

  @Override
  public String getTitle() {
    return "Login to the Puzzle Battle";
  }

  @Override
  public void registerEvents(Scene scene) {
    registerButton.setOnAction(e -> new RegisterScreen(stage).show());
    loginTextField.setOnAction(this::login);
    passwordField.setOnAction(this::login);
  }

  /**
   * Verification of login is done here
   */
  private void login(Event event) {
    pwd = passwordField.getText();
    login = loginTextField.getText();
    logInfo("Logging in...", "login", login);
    LoginRegisterUser.withRegisterUser(login, pwd, (user) -> {
      logInfo("Login result", "user", user);
      if (user == null)
        showAlert(ERROR, "login.incorrect");
      else
        new MainScreen(stage, new SettingsForScreens(), user).show();
    });
    showAlert(INFORMATION, "login.loggingIn");
  }

  /**
   * Preparing border panel as main panel to situate other components on it
   */
  private void prepareBorderPanel() {

    BorderPane pane = new BorderPane();

    pane.setCenter(loginPanel);
    pane.setBottom(registerPanel);
    pane.setLeft(separatorLeft);
    pane.setRight(separatorRight);
    this.pane = pane;
  }

  /**
   * Preparing buttons and labels for login screen
   */
  private void prepareButtonsAndLabels() {
    loginLabel = createLabel("login.username");
    loginTextField = new TextField(lang.get("login.usernameText"));
    passwordLabel = createLabel("login.password");
    passwordField = new PasswordField();

    prepareLoginButton();
    prepareRegisterButton();
  }

  /**
   * Prepare effect separators
   */
  private void prepareEffectSeparators() {
    separatorLeft = createLabel("", 50);
    separatorRight = createLabel("", 50);
  }

  /**
   * Method which prepares confirm button
   */
  private void prepareLoginButton() {
    confirmButton = new Button("Login");
    confirmButton.setMaxWidth(Double.MAX_VALUE);
    confirmButton.setOnAction(this::login);
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
    passwordLabel.setFont(getDefaultFont());
  }

  /**
   * Prepare regions
   */
  private void prepareRegions() {
    loginButtonRegion = createRegion(100);
    loginPasswordRegion = createRegion(10);
    registerRegion = new Region();
    registerRegion.setMaxWidth(Double.MAX_VALUE);
  }

  /**
   * Prepare button for registration
   */
  private void prepareRegisterButton() {
    registerButton = new Button("Register");
    registerButton.setMinWidth(200);
  }

  /**
   * Preparing  screen and pane
   *
   * @param stage
   */
  private void prepareScreenAndPane(Stage stage) {
    prepareLoginPanel();
    prepareBorderPanel();
  }
}
