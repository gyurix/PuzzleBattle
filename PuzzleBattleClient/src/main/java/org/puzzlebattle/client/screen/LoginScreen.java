package org.puzzlebattle.client.screen;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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


  /**
   * Constructor which creates screen for log in
   */

  public LoginScreen(Stage stage) {
    super(stage);
    createComponentsForLoginScreen();
    prepareScreenAndPane(stage);
    applySettingsOnStage(stage);
  }

  private void applySettingsOnStage(Stage stage) {
    stage.setTitle("Login to the Puzzle Battle.");
    stage.setOnCloseRequest(e->stage.close());
    //stage.sizeToScene();
  }

  private void prepareScreenAndPane(Stage stage) {
    pane.getChildren().addAll(loginLabel,loginTextField,passwordLabel,passwordField);
    sceneLogin = new Scene(pane,super.getWidth(),super.getHeight());
    stage.setScene(sceneLogin);
  }

  private void createComponentsForLoginScreen() {
    passwordField = new PasswordField();
    loginLabel = new Label("Login");
    passwordLabel = new Label("Password");
    loginTextField = new TextField("Put your login here!");
  }

}
