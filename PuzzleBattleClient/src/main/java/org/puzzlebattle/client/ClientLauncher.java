package org.puzzlebattle.client;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ClientLauncher extends Application {

  private int x;
  private VBox boxLayout;
  private Scene scene1;
  private Button startGameButton;
  private String startGameButtonName = new String("Start game");
  private String nameOfMenuPanel = new String("Game");
  private static final int totalIntroButtons= 5;

  /**
   * Constructor for objects of class Plocha
   */
  public ClientLauncher()
  {

  }



  private void applySettingsOnIntroButtons(Button introButtons[])
  {

  }

  private void applySettingsOnIntroPanel(Stage menuPanel)
  {
    menuPanel.setTitle(nameOfMenuPanel);
    menuPanel.setScene(scene1);
    menuPanel.setResizable(false);
    menuPanel.show();
  }


  private void createIntroButtons()
  {

  }


  public void start(Stage menuPanel) {
    {
      boxLayout = new VBox(2);
      scene1 = new Scene(boxLayout, 100, 100);

      startGameButton = new Button(startGameButtonName);

      boxLayout.getChildren().add(startGameButton);

      applySettingsOnIntroPanel(menuPanel);

    }
  }
}
