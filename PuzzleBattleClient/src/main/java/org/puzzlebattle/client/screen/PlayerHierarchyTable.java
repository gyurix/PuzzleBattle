package org.puzzlebattle.client.screen;


import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import static org.puzzlebattle.core.utils.LangFile.lang;

/**
 * Class with most used column containing user, represented by his nickName
 */
public class PlayerHierarchyTable extends TableView<UserGameAttributes> {

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
   * @param width - minimal width of the column and size of table
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
