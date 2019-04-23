package org.puzzlebattle.server.db.entity.gameSettings;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import javax.persistence.*;
import java.util.List;

//import org.puzzlebattle.client.games.bouncer.BouncerGameSettings;
//import org.puzzlebattle.client.games.fourinarow.FourInARowGameSettings;


/**
 * Abstract class for storing game settings
 *
 * @author Jakub Perdek
 * @version 1.0
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class GameSettings {

  protected int gameType;
  @Id
  @GeneratedValue
  protected long id;

  /**
   * @param gameTypeId
   * @return
   */
  private static long findGameSettings(int gameTypeId) {
    SessionFactory sf = new Configuration().configure("/META-INF/hibernate.cfg.xml").buildSessionFactory();
    Session session = sf.openSession();
    GameSettings gameSettings = null;

    String hql = "FROM GameSettings WHERE gameType= ?1";
    Query query = session.createQuery(hql);
    query.setParameter(1, gameTypeId);
    List<GameSettings> list = query.list();
    if (list.size() > 0) {
      gameSettings = list.get(0);
    }
    session.close();
    sf.close();
    if (gameSettings != null) {
      return gameSettings.getId();
    } else {
      return 0;
    }
  }

  /**
   * @param gameSettings
   * @param gameTypeId
   */
  private static void insertGameSettingsIntoDB(GameSettings gameSettings, int gameTypeId) {
    SessionFactory sf = new Configuration().configure("/META-INF/hibernate.cfg.xml").buildSessionFactory();
    Session session = sf.openSession();
    Transaction t = session.beginTransaction();
    session.persist(gameSettings);
    t.commit();
    session.close();
    sf.close();
  }

  /**
   * @param gameSettings
   * @return
   *//*
  public static long insertGameSettingsToDBIfTheyAreNotExistAndGetId(GameSettings gameSettings) {
    if (gameSettings instanceof FourInARowGameSettings) {
      FourInARowGameSettings fourInARowGameSettings = (FourInARowGameSettings) gameSettings;
      fourInARowGameSettings.setGameType(GameType.getFourInARowGame().getId());
      return storeGameSettingsIfNotExistAndReturnId(fourInARowGameSettings, GameType.getFourInARowGame().getId());
    } else if (gameSettings instanceof BouncerGameSettings) {
      BouncerGameSettings bouncerGameSettings = (BouncerGameSettings) gameSettings;
      bouncerGameSettings.setGameType(GameType.getBallBouncerGame().getId());
      return storeGameSettingsIfNotExistAndReturnId(bouncerGameSettings, GameType.getBallBouncerGame().getId());
    } else {
      return 0;
    }
  }*/

  /**
   * @param gameSettings
   * @param gameTypeId
   * @return
   */
  private static long storeGameSettingsIfNotExistAndReturnId(GameSettings gameSettings, int gameTypeId) {
    long gameSettingsId;
    if ((gameSettingsId = findGameSettings(gameTypeId)) == 0) {
      insertGameSettingsIntoDB(gameSettings, gameTypeId);
      return findGameSettings(gameTypeId);
    } else {
      return gameSettingsId;
    }

  }

  protected abstract int getGameType();

  protected abstract void setGameType(int gameType);

  protected abstract long getId();

  protected abstract void setId(int id);
}

