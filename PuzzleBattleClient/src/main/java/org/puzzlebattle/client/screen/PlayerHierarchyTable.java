package org.puzzlebattle.client.screen;


import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import javafx.scene.control.cell.PropertyValueFactory;
import org.puzzlebattle.client.databaseTables.UserPuzzleBattle;

public class PlayerHierarchyTable extends TableView<UserGameAttributes> {

  private TableColumn userName;

  protected static final int MIN_WIDTH_FOR_COLUMN = 150;

  public PlayerHierarchyTable()
  {
      this(150,300);
  }

  public PlayerHierarchyTable(double width,double height)
  {
        this.setMinSize(width,height);
        userName = new TableColumn("NickName");
        userName.setMinWidth(MIN_WIDTH_FOR_COLUMN);
        userName.setCellValueFactory(new PropertyValueFactory<UserGameAttributes,String>("nickName"));
        this.getColumns().addAll(userName);
  }

}
