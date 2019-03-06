package org.puzzlebattle.server.db.entity;

import javax.persistence.Entity;

@Entity
public class Game {
  private int player1;
  private int player2;
  private int type;
  private GameWinner winner;
}
