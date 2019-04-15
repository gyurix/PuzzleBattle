package org.puzzlebattle.client.databaseTables;

import lombok.Data;

import javax.persistence.*;


/**
 * Table witch characterise friendship  between two players
 *
 * @author Jakub Perdek
 * @version 1.0
 */
@Entity
@Table(name = "friendship")
@Data
public class Friendship {

  @Id
  @GeneratedValue
  private long id;

  private GamePlayer player1;
  private GamePlayer player2;

  @ManyToOne
  private DurationDate duration;
}
