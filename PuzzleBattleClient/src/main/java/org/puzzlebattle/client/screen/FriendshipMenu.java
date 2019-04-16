package org.puzzlebattle.client.screen;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.puzzlebattle.client.databaseTables.Friendship;
import org.puzzlebattle.client.databaseTables.LoginRegisterUser;
import org.puzzlebattle.client.databaseTables.UserPuzzleBattle;

public class FriendshipMenu extends AbstractScreen {

  private final String actualFriendsOnlyText = "Actual friends only";
  private final String allFriendsText = "All friends";
  private final String findUserText = "Find user";
  private final String friendsText = "Friends";
  private Label actualFriends;
  private Button actualFriendsOnly;
  private Button findFriend;
  private FriendshipTable foundUsers;
  private FriendshipTable friendshipTable;
  private Region horizontalForButtonRegion;
  private VBox menuButtons;
  private Scene sceneForFriendship;
  private Button searchButton;
  private Stage stage;
  private VBox tableContent;
  private UserPuzzleBattle user;
  private HBox wholeScreen;


  public FriendshipMenu(Stage stage, UserPuzzleBattle user) {
    super(stage);
    this.user = user;
    this.stage = stage;
    prepareComponentsForFriendshipMenu();
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
    findFriend = new Button(findUserText);
    findFriend.setMaxWidth(Double.MAX_VALUE);
    findFriend.setOnAction(e -> findUser());

    actualFriendsOnly = new Button(actualFriendsOnlyText);
    actualFriendsOnly.setMaxWidth(Double.MAX_VALUE);
    actualFriendsOnly.setOnAction(e -> prepareToFilterFriends());

    searchButton = new Button("Search");
    searchButton.setMaxWidth(Double.MAX_VALUE);
    searchButton.setOnAction(e -> prepareSearchCriteria());
  }

  private void prepareComponentsForFriendshipMenu() {
    prepareButtons();
    prepareRegions();
    prepareTables();
    prepareLayouts();
    sceneForFriendship = new Scene(wholeScreen, super.getWidth(), super.getHeight());
  }

  private void prepareFriendshipLayout() {
    menuButtons = new VBox(10);
    menuButtons.setMinWidth(150);
    menuButtons.setMaxHeight(Double.MAX_VALUE);
    menuButtons.getChildren().addAll(findFriend, actualFriendsOnly);
  }

  private void prepareLayouts() {
    prepareFriendshipLayout();
    prepareMenuLayout();
    prepareWholeScreen();
  }

  private void prepareMenuLayout() {
    tableContent = new VBox(10);
    tableContent.setMinWidth(super.getWidth() - 200);
    tableContent.setMaxHeight(Double.MAX_VALUE);
    tableContent.getChildren().add(friendshipTable);
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
  }

  public void show() {
    stage.setScene(sceneForFriendship);
    stage.setTitle("Friendship table");
    stage.setOnCloseRequest(e -> stage.close());
    stage.sizeToScene();
    stage.show();
  }
}
