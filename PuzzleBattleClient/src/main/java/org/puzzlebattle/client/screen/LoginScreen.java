package org.puzzlebattle.client.screen;

import javafx.event.Event;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.puzzlebattle.client.games.UserPuzzleBattle;
import org.puzzlebattle.client.protocol.Server;
import org.puzzlebattle.client.protocol.packets.in.ServerInLoginResult;
import org.puzzlebattle.client.protocol.packets.out.ServerOutLogin;

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
  private LanguageSelector languageSelector;

  /**
   * Constructor which creates screen for log in
   */
  public LoginScreen(Stage stage,LanguageSelector languageSelector) {
    super(stage);
    this.languageSelector = languageSelector;
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

  /**
   * Creates a label and loads specified language according to given key
   *
   * @param key - key to obtain requested name for created label
   * @return created label
   */
  private Label createLabel(String key) {
    Label label = new Label(lang.get(key));
    label.setFont(getDefaultFont());
    return label;
  }

  /**
   * Creates a label and sets specified text and width
   *
   * @param text     - text which will be added
   * @param minWidth - minimal width of the label
   * @return created label
   */
  private Label createLabel(String text, double minWidth) {
    Label label = new Label(text);
    label.setMaxHeight(Double.MAX_VALUE);
    label.setMinWidth(minWidth);
    return label;
  }

  /**
   * Creates a region and sets specified width
   *
   * @param minHeight - minimal width of the region
   * @return created region
   */
  private Region createRegion(double minHeight) {
    Region region = new Region();
    region.setMinHeight(minHeight);
    return region;
  }

  /**
   * Returns title of the login screen
   *
   * @return title of the loging screen
   */
  @Override
  public String getTitle() {
    return "Login to the Puzzle Battle";
  }

  /**
   * Registration of event is applied here
   *
   * @param scene scene which is used to register events
   */
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
    UserPuzzleBattle user = new UserPuzzleBattle(login, pwd);
    logInfo("Logging in...", "login", login);
    ServerOutLogin serverLogin = new ServerOutLogin(user.getUserName(), user.getPassword());
    new Server().sendPacket(serverLogin);

    ServerInLoginResult loginResult = new ServerInLoginResult();
    if (loginResult.isResult()) {
      showAlert(ERROR, "login.incorrect");
    } else {
      new MainScreen(stage, new SettingsForScreens(), user).show();
      showAlert(INFORMATION, "login.loggingIn");
    }
  }

  /**
   * Preparing border panel as main panel to situate other components on it
   */
  private void prepareBorderPanel() {

    BorderPane pane = new BorderPane();

    pane.setTop(languageSelector);
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
    loginButtonRegion = createRegion(80);
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
