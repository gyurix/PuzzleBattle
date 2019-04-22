package org.puzzlebattle.server.db.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Interval between starting and ending of game or friendship between two players
 *
 * @author Jakub Perdek, Juraj Barath
 * @version 1.0
 */
@Entity
@Data
@Table
public class DurationDate {
  @Column
  private Timestamp endDate;
  @Column
  @Id
  @GeneratedValue
  private long id;
  @Column
  private Timestamp startDate;
}
