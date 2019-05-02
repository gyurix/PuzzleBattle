package org.puzzlebattle.server.db.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
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
  private Timestamp startDate;

  public DurationDate(long start, long end) {
    startDate = new Timestamp(start);
    endDate = new Timestamp(end);
  }
}
