package org.puzzlebattle.client.screen;


import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import javafx.scene.control.cell.PropertyValueFactory;
import org.puzzlebattle.client.databaseTables.UserPuzzleBattle;

public class PlayerHierarchyTable extends TableView<UserPuzzleBattle> {

  private TableColumn userName;

  protected static final int MIN_WIDTH_FOR_COLUMN = 200;

  public PlayerHierarchyTable()
  {
      this(200,500);
  }

  public PlayerHierarchyTable(double width,double height)
  {
        this.setMinSize(width,height);
        userName = new TableColumn("Friend");
        userName.setMinWidth(MIN_WIDTH_FOR_COLUMN);
        userName.setCellFactory(new PropertyValueFactory<UserPuzzleBattle,String>("nickName"));
        this.getColumns().addAll(userName);
  }

}
