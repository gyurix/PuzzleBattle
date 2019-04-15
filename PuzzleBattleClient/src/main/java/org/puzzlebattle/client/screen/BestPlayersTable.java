package org.puzzlebattle.client.screen;

import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;


public class BestPlayersTable extends PlayerHierarchyTable {

  private TableColumn userScore;

  public BestPlayersTable() {
    userScore = new TableColumn("User score");
    userScore.setMinWidth(super.MIN_WIDTH_FOR_COLUMN);
    userScore.setCellValueFactory(new PropertyValueFactory<UserGameAttributes,Integer>("score"));
    this.getColumns().addAll(userScore);
  }
}