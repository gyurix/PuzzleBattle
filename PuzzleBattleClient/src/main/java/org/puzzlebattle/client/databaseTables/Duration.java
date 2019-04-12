package org.puzzlebattle.client.databaseTables;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;

@Entity
@Table(name = "duration")
@Data
public class Duration {

  @Id
  @GeneratedValue//(strategy=GenerationType.IDENTITY)
  private long id;

  private Date start;
  private Date end;
}
