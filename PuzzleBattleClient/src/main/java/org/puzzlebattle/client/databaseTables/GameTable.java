package org.puzzlebattle.client.databaseTables;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "game")
@Data
public class GameTable {

  @Id
  @GeneratedValue//(strategy=GenerationType.IDENTITY)
  private long id;

  @ManyToOne
  private GamePlayer player1;

  @ManyToOne
  private GamePlayer player2;

  @ManyToOne
  private Duration duration;

  //private enum  winner[]= {"P1","P2","DRAW"};

}
