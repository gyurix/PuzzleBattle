package org.puzzlebattle.client.games.fourinarow;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class WinningDialog extends Stage {

  private Scene scene;
  private VBox verticalBox;
  private Button newGame;
  private Button closeButton;
  private Button returnToMenu;
  private Label winner;
  private BorderPane border;
  private Font f;
  private ImageView img1;
  private Image image;

  public WinningDialog(FourInARowScreen fourInARowScreen,FourInARowPlayer winningPlayer) {
      prepareLayouts(fourInARowScreen);
      scene = new Scene(border,fourInARowScreen.getWidth(),fourInARowScreen.getHeight());
      applySettingsToStage(winningPlayer);
  }

  private void applySettingsToStage(FourInARowPlayer winningPlayer) {
    int playerNumber = winningPlayer.getPlayingNumber();
    this.setTitle("Player number "+playerNumber + " is winner.");
    this.setScene(scene);
    this.setOnCloseRequest(e->onClose());
    this.sizeToScene();
  }


  private void createAndPrepareButtons(FourInARowScreen fourInARowScreen) {
    newGame = new Button("New game");
    returnToMenu = new Button("Return to menu");
    closeButton = new Button("Close the game");
    newGame.setMaxWidth(Double.MAX_VALUE);
    returnToMenu.setMaxWidth(Double.MAX_VALUE);
    closeButton.setMaxWidth(Double.MAX_VALUE);

    closeButton.setOnAction(e->onClose());
    newGame.setOnAction(e->startNewGame(fourInARowScreen));
  }

  private void startNewGame(FourInARowScreen fourInARowScreen) {
    this.close();
    fourInARowScreen.getStage().close();
    new FourInARowScreen(fourInARowScreen.getStage(), new FourInARowGame(null, new FourInARowGameSettings())).show();
  }

  private void onClose()
  {
    this.close();
    Platform.exit();
  }

  private void createAndPrepareLabelWinner() {
      winner = new Label("Winner");
      winner.setMaxWidth(Double.MAX_VALUE);
      f = new Font("Arial",55);
      winner.setFont(f);
  }

  private void prepareLayouts(FourInARowScreen fourInARowScreen) {
    border= new BorderPane();
    border.setMaxSize(fourInARowScreen.getWidth(),fourInARowScreen.getHeight());
    verticalBox= new VBox(10);
    createAndPrepareButtons(fourInARowScreen);
    verticalBox.getChildren().addAll(newGame,returnToMenu,closeButton);
    border.setRight(verticalBox);
    tryToAddImage();
    createAndPrepareLabelWinner();
    border.setTop(winner);
  }

  private void tryToAddImage()
  {
    String imageURL =  "file:///C:\\Users\\perdek\\IdeaProjects\\PuzzleBattle\\oldChap.png";
    image =new Image(imageURL);
    img1 = new ImageView();
    img1.setImage(image);
    border.setLeft(img1);
  }


  public double getWinningDialogWidth() {
    return 250;
  }

  public double getWinningDialogHeight(){
    return 400;
  }
}
