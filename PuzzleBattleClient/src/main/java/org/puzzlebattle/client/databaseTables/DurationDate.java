package org.puzzlebattle.client.databaseTables;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
public class DurationDate {

  @Id
  @GeneratedValue//(strategy=GenerationType.IDENTITY)
  private long id;

  private Timestamp startDate;

  private Timestamp endDate;
}
