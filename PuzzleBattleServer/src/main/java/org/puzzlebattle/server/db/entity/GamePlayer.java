package org.puzzlebattle.server.db.entity;

import javax.persistence.Entity;

@Entity
public class GamePlayer extends Identificable {
  private int player;
  private int score;
}
