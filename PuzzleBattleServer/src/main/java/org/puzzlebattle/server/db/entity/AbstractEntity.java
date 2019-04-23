package org.puzzlebattle.server.db.entity;

import org.hibernate.Transaction;
import org.puzzlebattle.core.utils.ErrorAcceptedConsumer;
import org.puzzlebattle.core.utils.Logging;
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

  public void persist(ErrorAcceptedConsumer<Boolean> resultHandler) {
    DB.INSTANCE.withSession((s) -> {
      try {
        Transaction t = s.beginTransaction();
        s.persist(this);
        t.commit();
        s.flush();
        resultHandler.accept(true);
      } catch (Throwable e) {
        Logging.logSevere("Failed to save entity to database.", "entity", this, "error", e);
        resultHandler.accept(false);
      }
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
