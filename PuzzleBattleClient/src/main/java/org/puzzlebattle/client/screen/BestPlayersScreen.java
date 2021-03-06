package org.puzzlebattle.client.screen;

import javafx.geometry.Orientation;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import lombok.Getter;
import org.puzzlebattle.client.config.ConfigManager;
import org.puzzlebattle.client.config.SettingsForScreens;
import org.puzzlebattle.client.protocol.Client;
import org.puzzlebattle.client.protocol.packets.out.ServerOutBestPlayersRequest;
import org.puzzlebattle.client.utils.ThreadUtils;

import static org.puzzlebattle.core.utils.LangFile.lang;

/**
 * Screen for showing best players to user
 */
public class BestPlayersScreen extends AbstractScreen {

  @Getter
  private static BestPlayersScreen instance;
  private BestPlayersTable ballBouncerGameTable;
  private Font fFourInARow, fBallBouncer;
  private VBox fourInARowGameLayout, ballBouncerGameLayout;
  @Getter
  private BestPlayersTable fourInARowGameTable;
  private Label fourInARowLabel, ballBouncerLabel;
  private Separator gameSeparator;
  private SettingsForScreens settingsForScreens;
  private HBox wholeScreen;

  /**
   * Constructor which constructs screen for best players
   *
   * @param stage stage where information about best players will be set
   */
  public BestPlayersScreen(Stage stage, Client client) {
    super(stage, client);
    instance = this;
    this.settingsForScreens = ConfigManager.getInstance().getConfig().getScreenSettings();
    createTables();
    prepareComponentsForBestPlayerTable();
    client.sendPacket(new ServerOutBestPlayersRequest(10));
    showAlert(Alert.AlertType.INFORMATION, "bestplayers.loading");
  }

  /**
   * Creates tables for best player screen
   */
  private void createTables() {
    fourInARowGameTable = new BestPlayersTable();
    fourInARowGameTable.setMaxHeight(Double.MAX_VALUE);
    fourInARowGameTable.setMaxWidth(Double.MAX_VALUE);
    ballBouncerGameTable = new BestPlayersTable();
    ballBouncerGameTable.setMaxWidth(Double.MAX_VALUE);
    ballBouncerGameTable.setMaxHeight(Double.MAX_VALUE);
  }

  /**
   * Creates VBox according to inserted parameters
   *
   * @param spacing   - spacing
   * @param minWidth  - minimal width of the layout
   * @param maxHeight - maximal height of the layout
   * @return created VBox
   */
  private VBox createVBox(int spacing, double minWidth, double maxHeight) {
    VBox vBox = new VBox(spacing);
    vBox.setMinWidth(minWidth);
    vBox.setMaxHeight(maxHeight);
    return vBox;
  }

  @Override
  public void onClose() {
    ThreadUtils.ui(() -> new MainScreen(stage, client).show());
  }

  /**
   * Prepare horizontal layout for four in a row
   */
  private void prepareBallBouncerGameLabel() {
    ballBouncerLabel = new Label(lang.get("mainScreen.ballBouncer.ballBouncerLabel"));
    fBallBouncer = new Font(settingsForScreens.getTypeCharBallBouncer(), settingsForScreens.getSizeOfTextBallBouncer());
    ballBouncerLabel.setTextFill(Paint.valueOf(settingsForScreens.getColorBallBouncerLabel()));
    ballBouncerLabel.setWrapText(true);
    ballBouncerLabel.setFont(fBallBouncer);
  }

  /**
   * Prepares components for BestPlayerTable
   */
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
    fourInARowLabel.setTextFill(Paint.valueOf(settingsForScreens.getColorFourInARowLabel()));
    fourInARowLabel.setWrapText(true);
    fourInARowLabel.setFont(fFourInARow);
  }

  /**
   * Prepares game labels
   */
  private void prepareGameLabels() {
    prepareFourInARowGameLabel();
    prepareBallBouncerGameLabel();
  }

  /**
   * Prepares game layouts and separators
   */
  private void prepareGameLayoutsAndSeparators() {
    ballBouncerGameLayout = createVBox(10, super.getWidth() / 2 - 25, super.getHeight());

    gameSeparator = new Separator();
    gameSeparator.setMinWidth(50);
    gameSeparator.setMinHeight(super.getHeight());
    gameSeparator.setOrientation(Orientation.VERTICAL);

    fourInARowGameLayout = createVBox(10, super.getWidth() / 2 - 25, super.getHeight());
  }

  /**
   * Prepares layouts for best playes
   */
  private void prepareLayoutsForBestPlayer() {
    prepareGameLayoutsAndSeparators();
    prepareWholeLayout();
  }

  /**
   * Prepare layout with both best players tables, each for each game
   */
  private void prepareWholeLayout() {
    wholeScreen = new HBox(10);
    wholeScreen.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
    this.pane = wholeScreen;
  }

  /**
   * Sets components to layout
   */
  private void setComponentsToLayouts() {
    ballBouncerGameLayout.getChildren().setAll(ballBouncerLabel, ballBouncerGameTable);
    fourInARowGameLayout.getChildren().setAll(fourInARowLabel, fourInARowGameTable);
    wholeScreen.getChildren().setAll(ballBouncerGameLayout, gameSeparator, fourInARowGameLayout);
  }

  /**
   * Table for best players, uses hierarchy of tables
   */
  public static class BestPlayersTable extends FriendshipMenu.UserGameAttributes.PlayerHierarchyTable {

    private TableColumn userScore;

    /**
     * Creates table for best players
     */
    public BestPlayersTable() {
      userScore = new TableColumn(lang.get("columns.userScore"));
      userScore.setMinWidth(super.MIN_WIDTH_FOR_COLUMN);
      userScore.setCellValueFactory(new PropertyValueFactory<FriendshipMenu.UserGameAttributes, Integer>("score"));
      this.getColumns().addAll(userScore);
    }
  }
}
