package org.puzzlebattle.client.screen;

import javafx.stage.Stage;

public class FriendshipMenu extends AbstractScreen{

  private FriendshipTable friendshipTable;

  public FriendshipMenu(Stage stage)
  {
    super(stage);
    friendshipTable = new FriendshipTable();
  }


}
