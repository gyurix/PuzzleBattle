package org.puzzlebattle.client.databaseTables;

import lombok.Data;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.List;

/**
 * Type of game. Type of the game is created only once and reference to certain settings is added
 * players can be specified because of it too
 *
 * @author Jakub Perdek
 * @version 1.0
 */
@Data
public class GameType {

  private String description;
  @Id
  @GeneratedValue
  private int id;
  private String name;

  /**
   * @param gameName
   * @param description
   */
  private static void addGameIfNotExist(String gameName, String description) {
    GameType isInTable = getGameType(gameName);
    if (isInTable == null) {
      insertGameIntoDatabase(gameName, description);
    }
  }

  /**
   *
   */
  public static void addGamesToDBIfTheyAreNot() {
    addGameIfNotExist("Ball bouncer game",
            "Game where two players try to hit ball and don't let cross it behind them.");
    addGameIfNotExist("Four in a row game",
            "Players try to have some coins in a row or column, who is fast is winner!");
  }

  /**
   * @return
   */
  public static GameType getBallBouncerGame() {
    return getGameType("Ball bouncer game");
  }

  /**
   * @return
   */
  public static GameType getFourInARowGame() {
    return getGameType("Four in a row game");
  }

  /**
   * @param gameName
   * @return
   */
  public static GameType getGameType(String gameName) {
    SessionFactory sf = new Configuration().configure("/META-INF/hibernate.cfg.xml").buildSessionFactory();
    Session session = sf.openSession();
    Transaction t = session.beginTransaction();
    GameType gameType = null;

    String hql = "FROM GameType WHERE name= ?1";
    Query query = session.createQuery(hql);
    query.setParameter(1, gameName);
    List<GameType> list = query.list();
    if (list.size() > 0) {
      gameType = list.get(0);
    }
    t.commit();
    session.close();
    sf.close();
    return gameType;
  }

  /**
   * @param gameName
   * @param description
   */
  private static void insertGameIntoDatabase(String gameName, String description) {

  }
}
