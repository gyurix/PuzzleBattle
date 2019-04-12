package org.puzzlebattle.client.databaseTables;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "friendship")
@Data
public class Friendship {

  @Id
  @GeneratedValue
  private long id;

  private long player1;
  private long player2;

  @ManyToOne
  private Duration duration;
}
