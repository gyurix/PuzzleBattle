package org.puzzlebattle.client.screen;


import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Table of friendships
 */
public class FriendshipTable extends PlayerHierarchyTable {

  List<TableColumn> columns;

  /**
   * Creates friendship table
   *
   * @param args names of new columns
   */
  public FriendshipTable(String... args) {
    TableColumn column;
    columns = new ArrayList<TableColumn>();

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
    for (int i = 0; i < columns.size(); i = i + 1) {
      columns.get(i).setMinWidth(newSize);
    }
  }
}
