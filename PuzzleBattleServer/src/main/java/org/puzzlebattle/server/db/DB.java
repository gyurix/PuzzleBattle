package org.puzzlebattle.server.db;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DB {
  EntityManager em;
  EntityManagerFactory emf;

  public DB() {
    emf = Persistence.createEntityManagerFactory("main-db");
    EntityManager em = emf.createEntityManager();
    em.getTransaction().begin();
  }
}
