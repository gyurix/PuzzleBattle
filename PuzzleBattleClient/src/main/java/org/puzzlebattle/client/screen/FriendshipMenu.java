package org.puzzlebattle.client.screen;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lombok.Data;
import org.puzzlebattle.client.protocol.Client;
import org.puzzlebattle.client.protocol.packets.in.ServerInBestPlayers;
import org.puzzlebattle.client.protocol.packets.out.ServerOutBestPlayersRequest;
import org.puzzlebattle.client.protocol.packets.out.ServerOutLoadFriends;
import org.puzzlebattle.client.utils.ThreadUtils;

import java.util.ArrayList;
import java.util.List;

import static org.puzzlebattle.core.utils.LangFile.lang;

/*
 * Class for managing friendships
 */
public class FriendshipMenu extends AbstractScreen {

  private final String actualFriendsOnlyText = lang.get("friendship.actualFriendsOnly");
  private final String allFriendsText = lang.get("friendship.allFriends");
  private final String findUserText = lang.get("friendship.findUser");
  private final String friendsText = lang.get("friendship.friends");
  private Button actualFriendsOnly, findFriend, searchButton;
  private FriendshipTable foundUsers, friendshipTable;
  private Region horizontalForButtonRegion;
  private VBox tableContent, menuButtons;
  private HBox wholeScreen;

  /*
   *  Creates friendship  menu
   */
  public FriendshipMenu(Stage stage, Client client) {
    super(stage, client);
    prepareComponentsForFriendshipMenu();
  }

  /*
   * Creates button with specified name
   */
  private Button createButton(String text) {
    Button button = new Button(text);
    button.setMaxWidth(Double.MAX_VALUE);
    return button;
  }

  /*
   * Creates vBox layout with specified spacing, minimal width and given components
   */
  private VBox createVBoxLayout(int spacing, double minWidth, Node... args) {
    VBox vBox = new VBox(spacing);
    vBox.setMinWidth(minWidth);
    vBox.setMaxHeight(Double.MAX_VALUE);
    vBox.getChildren().addAll(args);
    return vBox;
  }

  /*
   * Find user for friendship
   */
  private void findUser() {
    if (findFriend.getText().equals(findUserText)) {
      findFriend.setText(friendsText);
      foundUsers.setItems(loadBestFriends());
      tableContent.getChildren().remove(friendshipTable);
      tableContent.getChildren().add(foundUsers);
      menuButtons.getChildren().addAll(horizontalForButtonRegion, searchButton);
    } else {
      findFriend.setText(findUserText);
      tableContent.getChildren().remove(foundUsers);
      tableContent.getChildren().add(friendshipTable);
      menuButtons.getChildren().removeAll(horizontalForButtonRegion, searchButton);
    }
  }

  /*
   * Loads best users to fill table with no specified users for starting friendship
   */
  private ObservableList<UserGameAttributes> loadBestFriends() {
    ServerOutBestPlayersRequest bestPlayers = new ServerOutBestPlayersRequest(10);
    client.sendPacket(bestPlayers);
    ServerInBestPlayers bestPlayersReceinved = new ServerInBestPlayers();
    return bestPlayersReceinved.getUserGameAttributes();
  }

  /*
   * Loads and fills table with friends
   */
  private void loadDataAndFillTableFromDatabase() {
    ServerOutLoadFriends loadFriends = new ServerOutLoadFriends();
    client.sendPacket(loadFriends);
  }

  @Override
  public void onClose() {
    ThreadUtils.ui(() -> new MainScreen(stage, client).show());
  }

  /*
   * Method which prepares buttons.
   */
  private void prepareButtons() {
    findFriend = createButton(findUserText);
    findFriend.setOnAction(e -> findUser());

    actualFriendsOnly = createButton(actualFriendsOnlyText);
    actualFriendsOnly.setOnAction(e -> prepareToFilterFriends());

    searchButton = createButton(lang.get("friendship.search"));
    searchButton.setOnAction(e -> prepareSearchCriteria());
  }

  /*
   * Prepares components for friendship menu
   */
  private void prepareComponentsForFriendshipMenu() {
    prepareButtons();
    prepareRegions();
    prepareTables();
    prepareLayouts();
  }

  /*
   * Prepares friendship layout
   */
  private void prepareFriendshipLayout() {
    menuButtons = createVBoxLayout(10, 150, findFriend, actualFriendsOnly);
  }

  /*
   * Prepares layouts
   */
  private void prepareLayouts() {
    prepareFriendshipLayout();
    prepareMenuLayout();
    prepareWholeScreen();
  }

  /*
   * Prepares menu layout
   */
  private void prepareMenuLayout() {
    tableContent = createVBoxLayout(10, super.getWidth() - 200, friendshipTable);
  }

  /*
   * Prepares regions
   */
  private void prepareRegions() {
    horizontalForButtonRegion = new Region();
    horizontalForButtonRegion.setMinWidth(350);
  }

  /*
   * Loads and fills table with friends where condition to find them has been applied
   */
  private void prepareSearchCriteria() {

  }

  /*
   * Prepares tables for games
   */
  private void prepareTables() {
    friendshipTable = new FriendshipTable("startTimestamp", "endTimeStamp");
    friendshipTable.setSizeForAllColumns(150);
    foundUsers = new FriendshipTable("score");
  }

  /*
   * Prepares filter for friends, not actual friendship can be removed or displayed with actual ones
   */
  private void prepareToFilterFriends() {
    if (actualFriendsOnly.getText().equals(actualFriendsOnlyText)) {
      actualFriendsOnly.setText(allFriendsText);
    } else {
      actualFriendsOnly.setText(actualFriendsOnlyText);
    }
  }

  /*
   * Prepares whole screen of a friendship menu, its size, padding, components
   */
  private void prepareWholeScreen() {
    wholeScreen = new HBox(10);
    wholeScreen.setMinSize(super.getWidth(), super.getHeight());
    wholeScreen.setPadding(new Insets(10, 10, 10, 10));
    wholeScreen.getChildren().addAll(tableContent, menuButtons);
    this.pane = wholeScreen;
  }

  /**
   * Table of friendships
   */
  public static class FriendshipTable extends UserGameAttributes.PlayerHierarchyTable {

    List<TableColumn> columns;

    /**
     * Creates friendship table
     *
     * @param args names of new columns
     */
    public FriendshipTable(String... args) {
      TableColumn column;
      columns = new ArrayList<>();

      for (String columnName : args) {
        column = new TableColumn(columnName);
        column.setCellValueFactory(new PropertyValueFactory<UserGameAttributes, Integer>(columnName));
        this.getColumns().add(column);
        columns.add(column);
      }
    }

    /**
     * Sets wicth for all columns to specified one
     *
     * @param newSize new width which will be applied
     */
    public void setSizeForAllColumns(double newSize) {
      for (TableColumn column : columns) {
        column.setMinWidth(newSize);
      }
    }
  }

  /**
   * User game attributes to store attributes about game players
   *
   * @author Jakub Perdek
   * @version 1.0
   */
  @Data
  public static class UserGameAttributes {

    private String endTimestamp;
    private String nickName;
    private Integer score;
    private String startTimestamp;

    /**
     * User game Attributes
     *
     * @param nickName - nickname of user
     * @param score    - score which he gained
     */
    public UserGameAttributes(String nickName, int score) {
      this.score = score;
      this.nickName = nickName;
    }

    /**
     * User game attributes
     *
     * @param nickName       - nickname of user
     * @param score          - score which he gained
     * @param startTimestamp - time stamp of start of the game
     * @param endTimestamp   - end timestamp of the game
     */
    public UserGameAttributes(String nickName, int score, String startTimestamp, String endTimestamp) {
      this.score = score;
      this.nickName = nickName;
      this.startTimestamp = startTimestamp;
      this.endTimestamp = endTimestamp;
    }

    /**
     * Class with most used column containing user, represented by his nickName
     */
    public static class PlayerHierarchyTable extends TableView<UserGameAttributes> {

      protected static final int MIN_WIDTH_FOR_COLUMN = 150;
      private TableColumn userName;

      /**
       * Creates default TableView according to UserGameAttribute class
       */
      public PlayerHierarchyTable() {
        this(150, 300);
      }

      /**
       * Creates specific TableView according to UserGameAttribute class
       *
       * @param width  - minimal width of the column and size of table
       * @param height - minimla height of table
       */
      public PlayerHierarchyTable(double width, double height) {
        this.setMinSize(width, height);
        userName = new TableColumn(lang.get("columns.nickName"));
        userName.setMinWidth(MIN_WIDTH_FOR_COLUMN);
        userName.setCellValueFactory(new PropertyValueFactory<UserGameAttributes, String>("nickName"));
        this.getColumns().addAll(userName);
      }

    }
  }
}
