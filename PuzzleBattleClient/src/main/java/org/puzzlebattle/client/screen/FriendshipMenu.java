package org.puzzlebattle.client.screen;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.puzzlebattle.client.databaseTables.Friendship;
import org.puzzlebattle.client.databaseTables.LoginRegisterUser;
import org.puzzlebattle.client.databaseTables.UserPuzzleBattle;

import static org.puzzlebattle.core.utils.LangFile.lang;

public class FriendshipMenu extends AbstractScreen {

  private final String actualFriendsOnlyText = lang.get("friendship.actualFriendsOnly");
  private final String allFriendsText = lang.get("friendship.allFriends");
  private final String findUserText = lang.get("friendship.findUser");
  private final String friendsText = lang.get("friendship.friends");
  private Button actualFriendsOnly, findFriend, searchButton;
  private FriendshipTable foundUsers, friendshipTable;
  private Region horizontalForButtonRegion;
  private VBox tableContent, menuButtons;
  private UserPuzzleBattle user;
  private HBox wholeScreen;


  public FriendshipMenu(Stage stage, UserPuzzleBattle user) {
    super(stage);
    this.user = user;
    prepareComponentsForFriendshipMenu();
  }

  private Button createButton(String text) {
    Button button = new Button(text);
    button.setMaxWidth(Double.MAX_VALUE);
    return button;
  }

  private VBox createVBoxLayout(int spacing, double minWidth, Node... args) {
    VBox vBox = new VBox(spacing);
    vBox.setMinWidth(minWidth);
    vBox.setMaxHeight(Double.MAX_VALUE);
    vBox.getChildren().addAll(args);
    return vBox;
  }

  private void findUser() {
    if (findFriend.getText().equals(findUserText)) {
      findFriend.setText(friendsText);
      foundUsers.setItems(LoginRegisterUser.getBestPlayers(10));
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

  private void loadDataAndFillTableFromDatabase() {
    friendshipTable.setItems(Friendship.loadFriends(user));
  }

  private void prepareButtons() {
    findFriend = createButton(findUserText);
    findFriend.setOnAction(e -> findUser());

    actualFriendsOnly = createButton(actualFriendsOnlyText);
    actualFriendsOnly.setOnAction(e -> prepareToFilterFriends());

    searchButton = createButton(lang.get("friendship.search"));
    searchButton.setOnAction(e -> prepareSearchCriteria());
  }

  private void prepareComponentsForFriendshipMenu() {
    prepareButtons();
    prepareRegions();
    prepareTables();
    prepareLayouts();
  }

  private void prepareFriendshipLayout() {
    menuButtons = createVBoxLayout(10, 150, findFriend, actualFriendsOnly);
  }

  private void prepareLayouts() {
    prepareFriendshipLayout();
    prepareMenuLayout();
    prepareWholeScreen();
  }

  private void prepareMenuLayout() {
    tableContent = createVBoxLayout(10, super.getWidth() - 200, friendshipTable);
  }

  private void prepareRegions() {
    horizontalForButtonRegion = new Region();
    horizontalForButtonRegion.setMinWidth(350);
  }

  private void prepareSearchCriteria() {

  }

  private void prepareTables() {
    friendshipTable = new FriendshipTable("startTimestamp", "endTimeStamp");
    friendshipTable.setSizeForAllColumns(150);
    foundUsers = new FriendshipTable("score");
  }

  private void prepareToFilterFriends() {
    if (actualFriendsOnly.getText().equals(actualFriendsOnlyText)) {
      actualFriendsOnly.setText(allFriendsText);
    } else {
      actualFriendsOnly.setText(actualFriendsOnlyText);
    }
  }

  private void prepareWholeScreen() {
    wholeScreen = new HBox(10);
    wholeScreen.setMinSize(super.getWidth(), super.getHeight());
    wholeScreen.setPadding(new Insets(10, 10, 10, 10));
    wholeScreen.getChildren().addAll(tableContent, menuButtons);
    this.pane = wholeScreen;
  }

}
