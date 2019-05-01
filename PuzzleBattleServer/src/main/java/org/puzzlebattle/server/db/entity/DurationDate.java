package org.puzzlebattle.server.db.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Interval between starting and ending of game or friendship between two players
 *
 * @author Jakub Perdek, Juraj Barath
 * @version 1.0
 */
@Data
@Entity
@NoArgsConstructor
@Table
public class DurationDate extends AbstractEntity {
  @Column
  private Timestamp endDate;
  @Column
  @Id
  @GeneratedValue
  private long id;
  @Column
  private Timestamp startDate;

  public DurationDate(long start, long end) {
    startDate = new Timestamp(start);
    endDate = new Timestamp(end);
  }
}
