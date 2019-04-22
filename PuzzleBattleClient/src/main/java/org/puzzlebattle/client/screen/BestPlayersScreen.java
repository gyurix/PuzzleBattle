package org.puzzlebattle.client.screen;

import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.puzzlebattle.client.databaseTables.LoginRegisterUser;

import static org.puzzlebattle.core.utils.LangFile.lang;

public class BestPlayersScreen extends AbstractScreen {

  private BestPlayersTable ballBouncerGameTable;
  private Font fFourInARow, fBallBouncer;
  private VBox fourInARowGameLayout, ballBouncerGameLayout;
  private BestPlayersTable fourInARowGameTable;
  private Label fourInARowLabel, ballBouncerLabel;
  private Separator gameSeparator;
  private SettingsForScreens settingsForScreens;
  private HBox wholeScreen;

  public BestPlayersScreen(Stage stage, SettingsForScreens settingsForScreens) {
    super(stage);
    this.settingsForScreens = settingsForScreens;
    createTables();
    prepareComponentsForBestPlayerTable();
    loadDataAndFillTablesFromDatabase();
  }

  private void createTables() {
    fourInARowGameTable = new BestPlayersTable();
    fourInARowGameTable.setMaxHeight(Double.MAX_VALUE);
    fourInARowGameTable.setMaxWidth(Double.MAX_VALUE);
    ballBouncerGameTable = new BestPlayersTable();
    ballBouncerGameTable.setMaxWidth(Double.MAX_VALUE);
    ballBouncerGameTable.setMaxHeight(Double.MAX_VALUE);
  }

  private VBox createVBox(int spacing, double minWidth, double maxHeight) {
    VBox vBox = new VBox(spacing);
    vBox.setMinWidth(minWidth);
    vBox.setMaxHeight(maxHeight);
    return vBox;
  }

  private void loadDataAndFillTablesFromDatabase() {
    fourInARowGameTable.setItems(LoginRegisterUser.getBestPlayers(10));
  }

  /**
   * Prepare horizontal layout for four in a row
   */
  private void prepareBallBouncerGameLabel() {
    ballBouncerLabel = new Label(lang.get("mainScreen.ballBouncer.ballBouncerLabel"));
    fBallBouncer = new Font(settingsForScreens.getTypeCharBallBouncer(), settingsForScreens.getSizeOfTextBallBouncer());
    ballBouncerLabel.setTextFill(settingsForScreens.getColorBallBouncerLabel());
    ballBouncerLabel.setWrapText(true);
    ballBouncerLabel.setFont(fBallBouncer);
  }

  private void prepareComponentsForBestPlayerTable() {
    prepareGameLabels();
    prepareLayoutsForBestPlayer();
    setComponentsToLayouts();
  }

  /**
   * Prepare label for four in a row
   */
  private void prepareFourInARowGameLabel() {
    fourInARowLabel = new Label(lang.get("mainScreen.fourInARow.fourInARowLabel"));
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
    ballBouncerGameLayout = createVBox(10, super.getWidth() / 2 - 25, super.getHeight());

    gameSeparator = new Separator();
    gameSeparator.setMinWidth(50);
    gameSeparator.setMinHeight(super.getHeight());
    gameSeparator.setOrientation(Orientation.VERTICAL);

    fourInARowGameLayout = createVBox(10, super.getWidth() / 2 - 25, super.getHeight());
  }

  private void prepareLayoutsForBestPlayer() {
    prepareGameLayoutsAndSeparators();
    prepareWholeLayout();
  }

  private void prepareWholeLayout() {
    wholeScreen = new HBox(10);
    wholeScreen.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
    this.pane = wholeScreen;
  }

  private void setComponentsToLayouts() {
    ballBouncerGameLayout.getChildren().setAll(ballBouncerLabel, ballBouncerGameTable);
    fourInARowGameLayout.getChildren().setAll(fourInARowLabel, fourInARowGameTable);
    wholeScreen.getChildren().setAll(ballBouncerGameLayout, gameSeparator, fourInARowGameLayout);
  }
}
