package org.puzzlebattle.server.db;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.puzzlebattle.core.utils.ErrorAcceptedConsumer;
import org.puzzlebattle.server.ThreadUtils;

import java.util.HashMap;

public enum DB {
  INSTANCE;
  private SessionFactory sessionFactory;
  private HashMap<Thread, Session> sessions = new HashMap<>();

  DB() {
    sessionFactory = new Configuration().configure("/META-INF/hibernate.cfg.xml").buildSessionFactory();
  }

  private void shutdown() {
    sessionFactory.close();
  }

  public void withSession(ErrorAcceptedConsumer<Session> sessionConsumer) {
    ThreadUtils.async(() -> {
      Thread t = Thread.currentThread();
      Session s = sessions.computeIfAbsent(t, thread -> sessionFactory.openSession());
      sessionConsumer.accept(s);
    });
  }
}
