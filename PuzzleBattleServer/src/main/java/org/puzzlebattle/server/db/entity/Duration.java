package org.puzzlebattle.server.db.entity;

import javax.persistence.Entity;

@Entity
public class Duration extends Identificable {
  private long end;
  private long start;
}
