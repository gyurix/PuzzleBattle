package org.puzzlebattle.client.screen;

import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import org.puzzlebattle.client.databaseTables.GamePlayer;


public class BestPlayersTable extends PlayerHierarchyTable {

  private TableColumn userScore;

  public BestPlayersTable() {
    userScore = new TableColumn("User score");
    userScore.setMinWidth(super.MIN_WIDTH_FOR_COLUMN);
    userScore.setCellValueFactory(new PropertyValueFactory<GamePlayer, String>("score"));
    this.getColumns().addAll(userScore);
  }
}