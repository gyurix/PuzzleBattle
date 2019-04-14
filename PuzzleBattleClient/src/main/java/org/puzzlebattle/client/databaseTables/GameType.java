package org.puzzlebattle.client.databaseTables;

import lombok.Data;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.List;

@Entity
@Data
public class GameType {

  @Id
  @GeneratedValue
  private int id;

  private String name;

  private String description;

  private static void addGameIfNotExist(String gameName,String description){
    GameType isInTable = getGameType(gameName);
    if(isInTable==null) {
        insertGameIntoDatabase(gameName,description);
    }
  }

  private static void insertGameIntoDatabase(String gameName,String description){
    SessionFactory sf = new Configuration().configure("/META-INF/hibernate.cfg.xml").buildSessionFactory();
    Session session = sf.openSession();
    Transaction t = session.beginTransaction();
    GameType gameType = new GameType();

    gameType.setName(gameName);
    gameType.setDescription(description);

    session.persist(gameType);
    t.commit();
    session.close();
    sf.close();
  }

  public static GameType getGameType(String gameName){
    SessionFactory sf = new Configuration().configure("/META-INF/hibernate.cfg.xml").buildSessionFactory();
    Session session = sf.openSession();
    Transaction t = session.beginTransaction();
    GameType gameType = null;

    String hql = "FROM GameType WHERE name= ?1";
    Query query = session.createQuery(hql);
    query.setParameter(1,gameName);
    List<GameType> list=  query.list();
    if(list.size()>0) {
      System.out.println("TTTTTUUUU");
      gameType = list.get(0);

    }
    t.commit();
    session.close();
    sf.close();
    return gameType;
  }

  public static void addGamesToDBIfTheyAreNot(){
    addGameIfNotExist("Ball bouncer game",
            "Game where two players try to hit ball and don't let cross it behind them.");
    addGameIfNotExist("Four in a row game",
            "Players try to have some coins in a row or column, who is fast is winner!");
  }

  public static GameType getBallBouncerGame(){
    return getGameType("Ball bouncer game");
  }

  public static GameType getFourInARowGame(){
    return getGameType("Four in a row game");
  }
}
