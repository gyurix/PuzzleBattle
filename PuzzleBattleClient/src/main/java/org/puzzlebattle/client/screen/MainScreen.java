package org.puzzlebattle.client.screen;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.puzzlebattle.client.games.bouncer.BallBouncerScreen;
import org.puzzlebattle.client.games.bouncer.BouncerGame;
import org.puzzlebattle.client.games.bouncer.BouncerGameSettings;
import org.puzzlebattle.client.games.fourinarow.FourInARowGame;
import org.puzzlebattle.client.games.fourinarow.FourInARowGameSettings;
import org.puzzlebattle.client.games.fourinarow.FourInARowScreen;

/**
 * Main screen in the program. Probably games can be chosen here.
 *
 * @author (Jakub Perdek, Juraj Barath)
 * @version (1.0)
 */

public class MainScreen extends AbstractScreen {

    private Button startFourInARowGame;
    private Button startBallBouncerGame;
    private Button closeMainScreen;
    private Button viewBestPlayersFourInARow;
    private Button viewBestPlayersBallBouncer;
    private Button reLoginButton;
    private GridPane gridPane;
    private VBox vBoxBallBouncerGame;
    private VBox vBoxFourInARowGame;
    private HBox hEscapeBox;
    private Label labelBallBouncer;
    private Label labelFourInARow;
    private Font fFourInARow;
    private Font fBallBouncer;
    private SettingsForScreens settingsForScreens;
    private Stage stage;
    private Scene mainScene;
    private ImageView imageViewBallBouncer;
    private ImageView imageViewFourInARow;
    private Image imageBallBouncer;
    private Image imageFourInARow;
    Separator separator;

  /**
   * Constructor which creates main screen in the program.
   */

  public MainScreen(Stage stage, SettingsForScreens settingsForScreens) {
    super(stage);
    this.stage= stage;
    this.settingsForScreens = settingsForScreens;

    prepareBallBouncerGameMenu();
    prepareFourInARowGameMenu();
    prepareEscapeButtons();
    prepareEscaneLayout();
    prepareSceneAndGridPane();
  }

  public void show(){
    System.out.println("here");
    stage.setScene(mainScene);
    stage.setTitle("Main menu");
    stage.setOnCloseRequest(e->stage.close());
    stage.show();
  }

  private void prepareSceneAndGridPane() {
    gridPane = new GridPane();
    separator = new Separator();
    //gridPane.getChildren().addAll(vBoxBallBouncerGame,vBoxFourInARowGame);
    gridPane.setHgap(50);
    gridPane.setVgap(20);
    separator.setOrientation(Orientation.VERTICAL);
    gridPane.setPadding(new Insets(10, 20, 10, 20));
    gridPane.add(vBoxBallBouncerGame,0,1);
    gridPane.add(separator,1,1);
    gridPane.add(vBoxFourInARowGame,2,1);
    GridPane.setValignment(hEscapeBox, VPos.BOTTOM);
    gridPane.add(hEscapeBox,0,3);
    mainScene = new Scene(gridPane,super.getWidth(),super.getHeight());
  }

  private void prepareLabelForBallBouncer() {
    labelBallBouncer = new Label("Ball Bouncer");
    fBallBouncer = new Font(settingsForScreens.getTypeCharBallBouncer(),settingsForScreens.getSizeOfTextBallBouncer());
    labelBallBouncer.setTextFill(settingsForScreens.getColorBallBouncerLabel());
    labelBallBouncer.setWrapText(true);
    labelBallBouncer.setFont(fBallBouncer);
  }

  private void prepareLabelForFourInARow() {
    labelFourInARow = new Label("Four In A Row");
    fFourInARow = new Font(settingsForScreens.getTypeCharFourInARow(),settingsForScreens.getSizeOfTextFourInARow());
    labelFourInARow.setTextFill(settingsForScreens.getColorFourInARowLabel());
    labelFourInARow.setWrapText(true);
    labelFourInARow.setFont(fFourInARow);
  }

  private void prepareBallBouncerGameMenu() {

    prepareLabelForBallBouncer();
    prepareButtonsBallBouncer();
    prepareLayoutsBallBouncer();
    prepareImageBallBouncer();
  }

  private void prepareFourInARowGameMenu(){
    prepareLabelForFourInARow();
    prepareButtonsFourInARow();
    prepareLayoutFourInARow();
    prepareImageFourInARow();
  }

  private void prepareLayoutFourInARow() {
    vBoxFourInARowGame = new VBox(5);
    vBoxFourInARowGame.setMaxWidth(Double.MAX_VALUE);
    vBoxFourInARowGame.setMaxHeight(Double.MAX_VALUE);
    vBoxFourInARowGame.getChildren().addAll(labelFourInARow,startFourInARowGame,viewBestPlayersFourInARow);
  }

  private void prepareLayoutsBallBouncer() {
    vBoxBallBouncerGame = new VBox(5);
    vBoxBallBouncerGame.setMaxWidth(Double.MAX_VALUE);
    vBoxBallBouncerGame.setMaxHeight(Double.MAX_VALUE);
    vBoxBallBouncerGame.getChildren().addAll(labelBallBouncer,startBallBouncerGame,viewBestPlayersBallBouncer);
  }

  private void prepareEscapeButtons() {
    reLoginButton = new Button("Re login");
    reLoginButton.setMaxWidth(Double.MAX_VALUE);
    reLoginButton.setOnAction(e->relogin());
    closeMainScreen = new Button("Close");
    closeMainScreen.setMaxWidth(Double.MAX_VALUE);
    closeMainScreen.setOnAction(e->stage.close());
  }

  private void prepareEscaneLayout(){
    hEscapeBox = new HBox(10);
    hEscapeBox.getChildren().addAll(reLoginButton,closeMainScreen);
  }

  private void prepareButtonsBallBouncer() {
    startBallBouncerGame = new Button("Launch Ball Bouncer game.");
    startBallBouncerGame.setMaxWidth(Double.MAX_VALUE);
    startBallBouncerGame.setOnAction(
              e->new BallBouncerScreen(new Stage(), new BouncerGame(null, new BouncerGameSettings())).show());

    viewBestPlayersBallBouncer = new Button("View best Ball Bouncer Players");
    viewBestPlayersBallBouncer.setMaxWidth(Double.MAX_VALUE);
    viewBestPlayersBallBouncer.setOnAction(e->System.out.println("Not complete functionality"));
  }

  private void prepareButtonsFourInARow() {
    startFourInARowGame = new Button("Launch Four in a row game");
    startFourInARowGame.setMaxWidth(Double.MAX_VALUE);
    startFourInARowGame.setOnAction(
              e->new FourInARowScreen(new Stage(), new FourInARowGame(null, new FourInARowGameSettings())).show());

    viewBestPlayersFourInARow = new Button("View best Four In A Row Players");
    viewBestPlayersFourInARow.setMaxWidth(Double.MAX_VALUE);
    viewBestPlayersFourInARow.setOnAction(e->System.out.println("Not completed functionality"));
  }

  private void relogin(){

  }

  private void prepareImageBallBouncer() {
    String imageURL = "pictures/BallBouncer.png";
    imageBallBouncer = new Image(imageURL);
    imageViewBallBouncer = new ImageView();
    imageViewBallBouncer.setFitHeight(settingsForScreens.getBallBouncerPictureHeight());
    imageViewBallBouncer.setFitWidth(settingsForScreens.getBallBouncerPictureWidth());
    imageViewBallBouncer.setImage(imageBallBouncer);
    vBoxBallBouncerGame.getChildren().add(imageViewBallBouncer);
  }

  private void prepareImageFourInARow(){
    String imageURL = "pictures/FourInARow1.png";
    imageFourInARow = new Image(imageURL);
    imageViewFourInARow = new ImageView();
    imageViewFourInARow.setFitHeight(settingsForScreens.getFourInARowPictureHeight());
    imageViewFourInARow.setFitWidth(settingsForScreens.getFourInARowPictureWidth());
    imageViewFourInARow.setImage(imageFourInARow);
    vBoxFourInARowGame.getChildren().add(imageViewFourInARow);
  }
}
