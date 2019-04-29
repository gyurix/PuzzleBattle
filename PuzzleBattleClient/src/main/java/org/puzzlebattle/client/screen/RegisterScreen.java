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
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import org.puzzlebattle.client.protocol.Server;
import org.puzzlebattle.client.protocol.packets.in.ServerInRegisterSuccessful;
import org.puzzlebattle.client.protocol.packets.out.ServerOutRegister;
import org.puzzlebattle.core.utils.Logging;


/**
 * Class where screen will be registered
 *
 * @author (Jakub Perdek, Juraj Barath)
 * @version (1.0)
 */
public class RegisterScreen extends AbstractScreen {

  private Button confirmButton;
  private PasswordField confirmPasswordField, passwordField;
  private Label emailLabel, nickLabel, passwordConfLabel, leftLabel, passwordLabel, rightLabel;
  private HBox leftHBox, rightHBox;
  private Separator leftSeparator, rightSeparator;
  private TextField nickField, emailField;
  private Region regionNickEmail, regionEmailPassword, regionBetweenPassword, regionUpConfirmButton;
  private VBox registerComponents;

  /**
   * Constructor which apply registering of the screen
   */
  RegisterScreen(Stage stage) {
    super(stage);
    prepareComponents();
    stage.setResizable(false);
    Logging.logInfo("Registration screen is created, but not shown.");
  }

  /**
   * Creates label with specified text which will be added inside
   *
   * @param text - text which will be added on label
   * @return created label
   */
  private Label createLabel(String text) {
    Label label = new Label(text);
    label.setMaxWidth(Double.MAX_VALUE);
    label.setFont(getDefaultFont());
    return label;
  }

  /**
   * Creates region with specified height
   *
   * @param height - minimal height which will be used on created region
   * @return created region
   */
  private Region createRegion(double height) {
    Region region = new Region();
    region.setMinHeight(height);
    return region;
  }

  /**
   * Creates specific side label
   *
   * @param text           - text which should be displayed on side label
   * @param pos            - position for concrete label
   * @param contentDisplay - used content display
   * @param top            - insets from top, which will be set
   * @param bottom         - insets from bottom, which will be set
   * @return created special side label
   */
  private Label createSpecialSideLabel(String text, Pos pos, ContentDisplay contentDisplay, int top, int bottom) {
    Label label = new Label(text);
    label.setFont(specialFontForSideLabels());
    label.setWrapText(true);
    label.setAlignment(pos);
    label.setContentDisplay(contentDisplay);
    label.setPrefWidth(50);
    label.setPadding(new Insets(top, 10, bottom, 20));
    return label;
  }

  /**
   * Returns height for register screen
   *
   * @return height of register screen
   */
  @Override
  public double getHeight() {
    return 615;
  }

  /**
   * Get title for register screen
   *
   * @return title for register screen
   */
  @Override
  public String getTitle() {
    return "Register Puzzle Battle";
  }

  /**
   * Registration of events, as pushing enter after typing requested information
   *
   * @param scene scene which is used to register events
   */
  @Override
  public void registerEvents(Scene scene) {
    confirmPasswordField.setOnAction((e) -> register());
    passwordField.setOnAction((e) -> register());
    nickField.setOnAction((e) -> register());
    emailField.setOnAction((e) -> register());
  }

  /**
   * Alert which will be showed if user types not same passwords
   */
  private void noSamePasswordsAlert() {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Not same passwords");
    alert.setContentText("Type your password and confirm password again!");
    alert.showAndWait();
  }

  /**
   * Prepares components for registration screen
   */
  private void prepareComponents() {
    prepareLabelsWithUserInf();
    prepareTextAndPasswordFields();
    prepareRegions();
    prepareConfirmButton();
    prepareSpecialSideComponents();
    prepareLayoutForRegisterComponents();
    prepareMainLayout();
  }

  /**
   * Prepares confirm button
   */
  private void prepareConfirmButton() {
    confirmButton = new Button("Register");
    confirmButton.setMaxWidth(Double.MAX_VALUE);
    confirmButton.setOnAction(e -> register());
  }

  /**
   * Prepares labels with user information
   */
  private void prepareLabelsWithUserInf() {
    nickLabel = createLabel("Your nick");
    passwordLabel = createLabel("Your password");
    passwordConfLabel = createLabel("Confirm password");
    emailLabel = createLabel("Your email");
  }

  /**
   * Prepares layout for registration components as labels and text fields
   */
  private void prepareLayoutForRegisterComponents() {
    registerComponents = new VBox(10);
    registerComponents.setMinWidth(super.getWidth() - 200);
    registerComponents.setPadding(super.createDefaultInsets());
    registerComponents.getChildren().addAll(nickLabel, nickField, regionNickEmail, emailLabel, emailField,
            regionEmailPassword, passwordLabel, passwordField, regionBetweenPassword, passwordConfLabel,
            confirmPasswordField, regionUpConfirmButton, confirmButton);
  }

  /**
   * Prepares main layout, is created, with two layouts on each size with name of the game
   */
  private void prepareMainLayout() {
    BorderPane pane = new BorderPane();
    pane.setLeft(leftHBox);
    pane.setRight(rightHBox);
    pane.setCenter(registerComponents);
    this.pane = pane;
  }

  /**
   * Prepares regions for registration screen
   */
  private void prepareRegions() {
    regionBetweenPassword = createRegion(10);
    regionEmailPassword = createRegion(10);
    regionNickEmail = createRegion(10);
    regionUpConfirmButton = createRegion(50);
  }

  /**
   * Prepares special side components as separators and horizontal layouts
   */
  private void prepareSpecialSideComponents() {
    prepareSpecialSideLabels();

    leftSeparator = new Separator();
    rightSeparator = new Separator();

    leftHBox = new HBox();
    leftHBox.getChildren().addAll(leftSeparator, leftLabel);

    rightHBox = new HBox();
    rightHBox.getChildren().addAll(rightSeparator, rightLabel);
  }

  /**
   * Prepares special side labels with name of the game
   */
  private void prepareSpecialSideLabels() {
    leftLabel = createSpecialSideLabel("PUZZLE", Pos.CENTER_RIGHT, ContentDisplay.TOP, 10, 100);
    rightLabel = createSpecialSideLabel("BATTLE", Pos.BOTTOM_RIGHT, ContentDisplay.BOTTOM, 100, 20);
  }

  /**
   * Prepares text fields and password fields with specific name for them
   */
  private void prepareTextAndPasswordFields() {
    nickField = new TextField("Add your nick");
    emailField = new TextField("Add your email");
    passwordField = new PasswordField();
    confirmPasswordField = new PasswordField();
  }

  /**
   * Registration will be checked and data will be stored to database
   */
  private void register() {
    String nickName = nickField.getText();
    String email = emailField.getText();
    String password = passwordField.getText();
    String passwordConfirm = confirmPasswordField.getText();

    if (!password.equals(passwordConfirm)) {
      Logging.logWarning("Passwords aren't same!");
      noSamePasswordsAlert();
      return;
    } else {
      Logging.logInfo("Passwords are the same. Registration is completed!");
      ServerOutRegister sor = new ServerOutRegister(email, password, nickName);
      new Server().sendPacket(sor);
      ServerInRegisterSuccessful registerSuccessful = new ServerInRegisterSuccessful();
      if (!registerSuccessful.isSuccessfulRegistration()) {
        Logging.logSevere("Registration failed");
        registrationFailedAlert();
      }
    }
    new AdditionalInformationScreen(new Stage(), nickName, email).show();
    new LoginScreen(getStage()).show();
  }

  /**
   * Alert which will be showed if registration fails
   */
  private void registrationFailedAlert() {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Registration failed");
    alert.setContentText("Registration failed! Please Register again!");
    alert.showAndWait();
  }

  /**
   * Default font for special labels is returned
   *
   * @return special font for side labels
   */
  private Font specialFontForSideLabels() {
    return Font.font("Lithograph", FontWeight.BOLD, 55);
  }
}
