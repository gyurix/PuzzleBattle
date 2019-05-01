package org.puzzlebattle.server.db.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Result of an actual game
 *
 * @author Jakub Perdek, Juraj Barath
 * @version 1.0
 */
@Entity
@Table
public class GameResult extends AbstractEntity {
  @Column
  private int player1;
  @Column
  private int player2;
  @Column
  private int type;
  @Column
  private GameWinner winner;
}
