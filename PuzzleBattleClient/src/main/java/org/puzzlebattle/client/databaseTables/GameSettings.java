package org.puzzlebattle.client.databaseTables;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.puzzlebattle.client.games.bouncer.BouncerGameSettings;
import org.puzzlebattle.client.games.fourinarow.FourInARowGameSettings;

import javax.persistence.*;
import java.util.List;



@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class GameSettings {

  @Id
  @GeneratedValue
  protected long id;

  protected int gameType;

  protected abstract long getId();
  protected abstract int getGameType();
  protected abstract void setId(int id);
  protected abstract void setGameType(int gameType);

  public static long insertGameSettingsToDBIfTheyAreNotExistAndGetId(GameSettings gameSettings){
    if(gameSettings  instanceof FourInARowGameSettings) {
      FourInARowGameSettings fourInARowGameSettings = (FourInARowGameSettings) gameSettings;
      fourInARowGameSettings.setGameType(GameType.getFourInARowGame().getId());
      return storeGameSettingsIfNotExistAndReturnId(fourInARowGameSettings,GameType.getFourInARowGame().getId());
    }
    else if(gameSettings instanceof BouncerGameSettings){
      BouncerGameSettings bouncerGameSettings = (BouncerGameSettings) gameSettings;
      bouncerGameSettings.setGameType(GameType.getBallBouncerGame().getId());
      return storeGameSettingsIfNotExistAndReturnId(bouncerGameSettings,GameType.getBallBouncerGame().getId());
    }
    else {
      return 0;
    }
  }

  private static long storeGameSettingsIfNotExistAndReturnId(GameSettings gameSettings, int gameTypeId){
    long gameSettingsId;
    if((gameSettingsId= findGameSettings(gameTypeId))==0){
        insertGameSettingsIntoDB(gameSettings,gameTypeId);
        return findGameSettings(gameTypeId);
    }
    else{
      return gameSettingsId;
    }

  }

  private static void insertGameSettingsIntoDB(GameSettings gameSettings,int gameTypeId) {
    SessionFactory sf = new Configuration().configure("/META-INF/hibernate.cfg.xml").buildSessionFactory();
    Session session = sf.openSession();
    Transaction t = session.beginTransaction();
    session.persist(gameSettings);
    /*if(gameSettings  instanceof FourInARowGameSettings) {
      FourInARowGameSettings fourInARowGameSettings = (FourInARowGameSettings) gameSettings;
      fourInARowGameSettings.setGameType(gameTypeId);
      session.persist(fourInARowGameSettings);
    }
    else if(gameSettings instanceof BouncerGameSettings){
     BouncerGameSettings bouncerGameSettings = (BouncerGameSettings) gameSettings;
     bouncerGameSettings.setGameType(gameTypeId);
      session.persist(bouncerGameSettings);
    }
    */
    t.commit();
    session.close();
    sf.close();
  }

  private static long findGameSettings(int gameTypeId) {
    SessionFactory sf = new Configuration().configure("/META-INF/hibernate.cfg.xml").buildSessionFactory();
    Session session = sf.openSession();
    Transaction t = session.beginTransaction();
    GameSettings gameSettings = null;

    String hql = "FROM GameSettings WHERE gameType= ?1";
    Query query = session.createQuery(hql);
    query.setParameter(1, gameTypeId);
    List<GameSettings> list = query.list();
    if (list.size() > 0) {
      gameSettings = list.get(0);
    }
    t.commit();
    session.close();
    sf.close();
    if(gameSettings!=null) {
      return gameSettings.getId();
    }
    else{
      return 0;
    }
  }
}
