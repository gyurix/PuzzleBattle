package org.puzzlebattle.server.db.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.puzzlebattle.core.entity.GameWinner;

import javax.persistence.*;


/**
 * Table which represents one game between two players. Every game contains settings, result and interval
 *
 * @author Jakub Perdek
 * @version 1.0
 */
@Data
@Entity
@Table
@AllArgsConstructor
public class GameResult extends AbstractEntity {
  @ManyToOne(cascade = {CascadeType.ALL})
  private DurationDate duration;
  @OneToOne(cascade = CascadeType.ALL)
  private GameSettings gameSettings;
  @ManyToOne
  private GamePlayer player1;
  @ManyToOne
  private GamePlayer player2;
  private GameWinner winner;
}

