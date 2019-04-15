package org.puzzlebattle.client.databaseTables;

import lombok.Data;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "gamePlayer")
@Data
public class GamePlayer {

  @Id
  @GeneratedValue
  private long id;

  private long player;
  private int score = 0;
  private int gameType;

  public void setScore(int score){
    this.score = this.score + score;
  }

  public static GamePlayer createGamePlayerFromUserIfNotExist(UserPuzzleBattle userPuzzleBattle,int gameTypeForNewUser){
    GamePlayer foundGamePlayer;
    if((foundGamePlayer= findGamePlayerInDB(LoginRegisterUser.getRegister(userPuzzleBattle.getNickName(),userPuzzleBattle.getPassword()).getId(),gameTypeForNewUser))==null) {
      GamePlayer newGamePlayer = new GamePlayer();
      newGamePlayer.setPlayer(LoginRegisterUser.getRegister(userPuzzleBattle.getNickName(), userPuzzleBattle.getPassword()).getId());
      newGamePlayer.setScore(0);
      newGamePlayer.setGameType(gameTypeForNewUser);
      insertGamePlayerToDB(newGamePlayer);
      return findGamePlayerInDB(LoginRegisterUser.getRegister(userPuzzleBattle.getNickName(), userPuzzleBattle.getPassword()).getId(), gameTypeForNewUser);
    }
   else{
     return foundGamePlayer;
    }
  }

  private static void insertGamePlayerToDB(GamePlayer gamePlayer){
    SessionFactory sf = new Configuration().configure("/META-INF/hibernate.cfg.xml").buildSessionFactory();
    Session session = sf.openSession();
    Transaction t = session.beginTransaction();

    session.persist(gamePlayer);

    t.commit();
    session.close();
    sf.close();
  }

  public static GamePlayer findGamePlayerInDB(long idOfUserInDB,int gameTypeOfUser){
    SessionFactory sf = new Configuration().configure("/META-INF/hibernate.cfg.xml").buildSessionFactory();
    Session session = sf.openSession();
    Transaction t = session.beginTransaction();
    GamePlayer gamePlayer= null;

    String hql = "FROM GamePlayer WHERE player=?1 AND gameType= ?2";
    Query query = session.createQuery(hql);
    query.setParameter(1,idOfUserInDB);
    query.setParameter(2,gameTypeOfUser);
    List<GamePlayer> list=  query.list();
    if(list.size()>0)
    {
      gamePlayer = list.get(0);

    }
    t.commit();
    session.close();
    sf.close();
    return gamePlayer;
  }

  public static GamePlayer createVirtualPlayerForTest(int gameType){

    GamePlayer virtualGamePlayer = new GamePlayer();
    virtualGamePlayer.setGameType(gameType);
    virtualGamePlayer.setScore(0);
    virtualGamePlayer.setPlayer(0); //NOT IN TABLE!!!
    insertGamePlayerToDB(virtualGamePlayer);
    return virtualGamePlayer;
  }

}
