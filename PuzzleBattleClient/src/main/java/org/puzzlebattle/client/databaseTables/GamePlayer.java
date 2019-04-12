package org.puzzlebattle.client.databaseTables;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "gamePlayer")
@Data
public class GamePlayer {

  @Id
  @GeneratedValue
  private long id;

  private long player;
  private int score;
}
