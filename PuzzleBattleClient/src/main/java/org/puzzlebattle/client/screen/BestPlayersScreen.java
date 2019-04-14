package org.puzzlebattle.client.screen;

import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class BestPlayersScreen extends AbstractScreen{

  private BestPlayersTable fourInARowGameTable;
  private BestPlayersTable ballBouncerGameTable;
  private TableColumn userScore;
  private HBox wholeScreen;
  private VBox ballBouncerGameLayout;
  private VBox fourInARowGameLayout;
  private Separator gameSeparator;
  private Label fourInARowLabel;
  private Label ballBouncerLabel;
  private SettingsForScreens settingsForScreens;
  private Font fFourInARow, fBallBouncer;

  public BestPlayersScreen(Stage stage,SettingsForScreens settingsForScreens) {
    super(stage);
    this.settingsForScreens = settingsForScreens;
    fourInARowGameTable = new BestPlayersTable();
    ballBouncerGameTable = new BestPlayersTable();
    prepareComponentsForBestPlayerTable();
    loadDataAndFillTablesFromDatabase();
  }

  private void loadDataAndFillTablesFromDatabase(){

  }

  private void prepareComponentsForBestPlayerTable() {
    prepareGameLabels();
    prepareLayoutsForBestPlayer();
  }

  private void prepareGameLabels(){
    prepareFourInARowGameLabel();
    prepareBallBouncerGameLabel();
  }

  /**
   * Prepare label for four in a row
   */
  private void prepareFourInARowGameLabel(){
    fourInARowLabel = new Label("Four In A Row");
    fFourInARow = new Font(settingsForScreens.getTypeCharFourInARow(), settingsForScreens.getSizeOfTextFourInARow());
    fourInARowLabel.setTextFill(settingsForScreens.getColorFourInARowLabel());
    fourInARowLabel.setWrapText(true);
    fourInARowLabel.setFont(fFourInARow);
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

  private void prepareLayoutsForBestPlayer() {
    prepareGameLayoutsAndSeparators();
    prepareWholeLayout();
  }

  private void setComponentsToLayouts() {
    ballBouncerGameLayout.getChildren().setAll(ballBouncerLabel,ballBouncerGameTable);
    fourInARowGameLayout.getChildren().setAll(fourInARowLabel,fourInARowGameTable);
    wholeScreen.getChildren().setAll(ballBouncerGameLayout,gameSeparator,fourInARowGameTable);
  }

  private void prepareWholeLayout(){
    wholeScreen = new HBox(10);
    wholeScreen.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
  }

  private void prepareGameLayoutsAndSeparators(){
    ballBouncerGameLayout = new VBox(10);
    ballBouncerGameLayout.setMinWidth(super.getWidth()/2-25);
    ballBouncerGameLayout.setMinHeight(super.getHeight());

    gameSeparator = new Separator();
    gameSeparator.setMinWidth(50);
    gameSeparator.setMinHeight(super.getHeight());

    fourInARowGameLayout = new VBox(10);
    fourInARowGameLayout.setMinWidth(super.getWidth()/2-25);
    fourInARowGameLayout.setMinHeight(super.getHeight());
  }
}
