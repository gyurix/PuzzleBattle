package org.puzzlebattle.server.db.entity;

import org.hibernate.Transaction;
import org.puzzlebattle.server.db.DB;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
class AbstractEntity {
  @Id
  @GeneratedValue
  @Column
  private int id;

  public void persist() {
    DB.INSTANCE.withSession((s) -> {
      Transaction t = s.beginTransaction();
      s.persist(this);
      t.commit();
    });
  }

  public void update() {
    DB.INSTANCE.withSession((s) -> {
      Transaction t = s.beginTransaction();
      s.update(this);
      t.commit();
    });
  }
}
