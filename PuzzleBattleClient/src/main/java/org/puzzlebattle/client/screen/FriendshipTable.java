package org.puzzlebattle.client.screen;


import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.List;

public class FriendshipTable extends PlayerHierarchyTable{

  List<TableColumn> columns;

  public FriendshipTable(String ... args)
  {
    TableColumn column;
    columns= new ArrayList<TableColumn>();

    for(String columnName: args){
      column= new TableColumn(columnName);
      column.setCellValueFactory(new PropertyValueFactory<UserGameAttributes,Integer>(columnName));
      this.getColumns().add(column);
      columns.add(column);
    }
  }

  public void setSizeForAllColumns(double newSize){
    for(int i=0;i<columns.size();i=i+1){
      columns.get(i).setMinWidth(newSize);
    }
  }
}
