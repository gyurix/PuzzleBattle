package org.puzzlebattle.client.databaseTables;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Timestamp;

/**
 * Interval between starting and ending of game or friendship between two players
 *
 * @author Jakub Perdek
 * @version 1.0
 */
@Entity
@Data
public class DurationDate {

  private Timestamp endDate;
  @Id
  @GeneratedValue//(strategy=GenerationType.IDENTITY)
  private long id;
  private Timestamp startDate;
}