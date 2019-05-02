package org.puzzlebattle.server.db;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.SharedSessionContract;
import org.hibernate.cfg.Configuration;
import org.puzzlebattle.core.utils.ErrorAcceptedConsumer;
import org.puzzlebattle.core.utils.Logging;
import org.puzzlebattle.server.ThreadUtils;

import java.util.HashMap;

public enum DB {
  INSTANCE;
  private SessionFactory sessionFactory;
  private HashMap<Thread, Session> sessions = new HashMap<>();

  DB() {
    sessionFactory = new Configuration().configure("/META-INF/hibernate.cfg.xml").buildSessionFactory();
  }

  public void init() {
    Logging.logInfo("Initializing database connection...");
    for (int i = 0; i < 5; ++i)
      withSession((s) -> {
      });
  }

  private void shutdown() {
    sessions.values().forEach(SharedSessionContract::close);
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
