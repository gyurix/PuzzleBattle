package org.puzzlebattle.server.db.entity;

import lombok.Data;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import javax.persistence.*;
import java.util.List;


/**
 * Player who plays certain type of game and collecting score while playing
 *
 * @author Jakub Perdek
 * @version 1.0
 */
@Entity
@Table(name = "gamePlayer")
@Data
public class GamePlayer {

  private int gameType;
  @Id
  @GeneratedValue
  private long id;
  @ManyToOne
  @JoinColumn
  private User player;
  private int score = 0;

  /**
   * @param user
   * @param gameTypeForNewUser
   * @return
   */
  public static GamePlayer createGamePlayerFromUserIfNotExist(User user, int gameTypeForNewUser) {
    GamePlayer foundGamePlayer;
    if ((foundGamePlayer = findGamePlayerInDB(UserManager.findUser(user.getNickName(), user.getPassword()), gameTypeForNewUser)) == null) {
      GamePlayer newGamePlayer = new GamePlayer();
      newGamePlayer.setPlayer(UserManager.findUser(user.getNickName(), user.getPassword()));
      newGamePlayer.setScore(0);
      newGamePlayer.setGameType(gameTypeForNewUser);
      insertGamePlayerToDB(newGamePlayer);
      return findGamePlayerInDB(UserManager.findUser(user.getNickName(), user.getPassword()), gameTypeForNewUser);
    } else {
      return foundGamePlayer;
    }
  }

  /**
   * @param gameType
   * @return
   */
  public static GamePlayer createVirtualPlayerForTest(int gameType) {

    GamePlayer virtualGamePlayer = new GamePlayer();
    virtualGamePlayer.setGameType(gameType);
    virtualGamePlayer.setScore(0);
    virtualGamePlayer.setPlayer(null); //NOT IN TABLE!!!
    insertGamePlayerToDB(virtualGamePlayer);
    return virtualGamePlayer;
  }

  /**
   * @param user
   * @param gameTypeOfUser
   * @return
   */
  public static GamePlayer findGamePlayerInDB(User user, int gameTypeOfUser) {
    SessionFactory sf = new Configuration().configure("/META-INF/hibernate.cfg.xml").buildSessionFactory();
    Session session = sf.openSession();
    Transaction t = session.beginTransaction();
    GamePlayer gamePlayer = null;

    String hql = "FROM GamePlayer WHERE player=?1 AND gameType= ?2";
    Query query = session.createQuery(hql);
    query.setParameter(1, user);
    query.setParameter(2, gameTypeOfUser);
    List<GamePlayer> list = query.list();
    if (list.size() > 0) {
      gamePlayer = list.get(0);

    }
    t.commit();
    session.close();
    sf.close();
    return gamePlayer;
  }

  /**
   * @param gamePlayer
   */
  private static void insertGamePlayerToDB(GamePlayer gamePlayer) {
    SessionFactory sf = new Configuration().configure("/META-INF/hibernate.cfg.xml").buildSessionFactory();
    Session session = sf.openSession();
    Transaction t = session.beginTransaction();

    session.persist(gamePlayer);

    t.commit();
    session.close();
    sf.close();
  }

  /**
   * @param score
   */
  public void setScore(int score) {
    this.score = this.score + score;
  }

}
