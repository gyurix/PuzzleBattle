package org.puzzlebattle.client.screen;

import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.puzzlebattle.client.databaseTables.LoginRegisterUser;

public class BestPlayersScreen extends AbstractScreen {

  private VBox ballBouncerGameLayout;
  private BestPlayersTable ballBouncerGameTable;
  private Label ballBouncerLabel;
  private Font fFourInARow, fBallBouncer;
  private VBox fourInARowGameLayout;
  private BestPlayersTable fourInARowGameTable;
  private Label fourInARowLabel;
  private Separator gameSeparator;
  private Scene sceneBestPlayers;
  private SettingsForScreens settingsForScreens;
  private Stage stage;
  private TableColumn userScore;
  private HBox wholeScreen;

  public BestPlayersScreen(Stage stage, SettingsForScreens settingsForScreens) {
    super(stage);
    this.stage = stage;
    this.settingsForScreens = settingsForScreens;
    fourInARowGameTable = new BestPlayersTable();
    fourInARowGameTable.setMaxHeight(Double.MAX_VALUE);
    fourInARowGameTable.setMaxWidth(Double.MAX_VALUE);
    ballBouncerGameTable = new BestPlayersTable();
    ballBouncerGameTable.setMaxWidth(Double.MAX_VALUE);
    ballBouncerGameTable.setMaxHeight(Double.MAX_VALUE);
    prepareComponentsForBestPlayerTable();
    loadDataAndFillTablesFromDatabase();
  }

  private void loadDataAndFillTablesFromDatabase() {
    fourInARowGameTable.setItems(LoginRegisterUser.getBestPlayers(10));
  }

  /**
   * Prepare horizontal layout for four in a row
   */
  private void prepareBallBouncerGameLabel() {
    ballBouncerLabel = new Label("Ball Bouncer");
    fBallBouncer = new Font(settingsForScreens.getTypeCharBallBouncer(), settingsForScreens.getSizeOfTextBallBouncer());
    ballBouncerLabel.setTextFill(settingsForScreens.getColorBallBouncerLabel());
    ballBouncerLabel.setWrapText(true);
    ballBouncerLabel.setFont(fBallBouncer);
  }

  private void prepareComponentsForBestPlayerTable() {
    prepareGameLabels();
    prepareLayoutsForBestPlayer();
    setComponentsToLayouts();
    sceneBestPlayers = new Scene(wholeScreen, super.getWidth() + 50, super.getHeight());
  }

  /**
   * Prepare label for four in a row
   */
  private void prepareFourInARowGameLabel() {
    fourInARowLabel = new Label("Four In A Row");
    fFourInARow = new Font(settingsForScreens.getTypeCharFourInARow(), settingsForScreens.getSizeOfTextFourInARow());
    fourInARowLabel.setTextFill(settingsForScreens.getColorFourInARowLabel());
    fourInARowLabel.setWrapText(true);
    fourInARowLabel.setFont(fFourInARow);
  }

  private void prepareGameLabels() {
    prepareFourInARowGameLabel();
    prepareBallBouncerGameLabel();
  }

  private void prepareGameLayoutsAndSeparators() {
    ballBouncerGameLayout = new VBox(10);
    ballBouncerGameLayout.setMinWidth(super.getWidth() / 2 - 25);
    ballBouncerGameLayout.setMinHeight(super.getHeight());

    gameSeparator = new Separator();
    gameSeparator.setMinWidth(50);
    gameSeparator.setMinHeight(super.getHeight());
    gameSeparator.setOrientation(Orientation.VERTICAL);

    fourInARowGameLayout = new VBox(10);
    fourInARowGameLayout.setMinWidth(super.getWidth() / 2 - 25);
    fourInARowGameLayout.setMinHeight(super.getHeight());
  }

  private void prepareLayoutsForBestPlayer() {
    prepareGameLayoutsAndSeparators();
    prepareWholeLayout();
  }

  private void prepareWholeLayout() {
    wholeScreen = new HBox(10);
    wholeScreen.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
  }

  private void setComponentsToLayouts() {
    ballBouncerGameLayout.getChildren().setAll(ballBouncerLabel, ballBouncerGameTable);
    fourInARowGameLayout.getChildren().setAll(fourInARowLabel, fourInARowGameTable);
    wholeScreen.getChildren().setAll(ballBouncerGameLayout, gameSeparator, fourInARowGameLayout);
  }

  public void show() {
    stage.setScene(sceneBestPlayers);
    stage.setTitle("Best players.");
    stage.setOnCloseRequest(e -> stage.close());
    stage.sizeToScene();
    stage.show();
  }
}
