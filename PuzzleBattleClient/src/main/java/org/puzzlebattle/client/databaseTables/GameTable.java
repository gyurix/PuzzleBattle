package org.puzzlebattle.client.databaseTables;

import lombok.Data;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import javax.persistence.*;
import java.sql.Timestamp;


@Entity
@Table(name = "game")
@Data
public class GameTable {

  @Id
  @GeneratedValue//(strategy=GenerationType.IDENTITY)
  private long id;


  @ManyToOne
  private GamePlayer player1;

  @ManyToOne
  private GamePlayer player2;

  @ManyToOne(cascade = {CascadeType.ALL})
  private DurationDate duration;

  @ManyToOne(cascade = {CascadeType.ALL})
  private GameSettings gameSettings;

  public enum Winner {
    P1,P2,DRAW
  }

  private Winner winner= Winner.DRAW;

  public static GameTable prepareGameTable(UserPuzzleBattle userPuzzleBattle,boolean test,int gameType,GameSettings gameSettings){
    GamePlayer player1;
    GamePlayer player2;
    GameTable gameTable;

    player1 = GamePlayer.createGamePlayerFromUserIfNotExist(userPuzzleBattle,gameType);
    if(test) {
      player2 = GamePlayer.createVirtualPlayerForTest(gameType);
    }
    else
    {
      player2=null;
    }

    gameTable = new GameTable();
    gameTable.setPlayer1(player1);
    gameTable.setPlayer2(player2);
    gameTable.setGameSettings(gameSettings);
    gameTable.setDuration(new DurationDate());
    gameTable.getDuration().setStartDate(new Timestamp(System.currentTimeMillis()));
    insertGameTableIntoDatabase(gameTable);
    //gameTable.getDuration().setStart(Calendar.getInstance().getTime().getTime());
    return gameTable;
  }

  public static GameTable createTheSameGameFromOlderGame(GameTable gameTable){
    GameTable newGameTable = new GameTable();
    newGameTable.setPlayer1(gameTable.getPlayer1());
    newGameTable.setPlayer2(gameTable.getPlayer2());
    newGameTable.setGameSettings(gameTable.getGameSettings());
    newGameTable.setDuration(new DurationDate());
    gameTable.getDuration().setStartDate(new Timestamp(System.currentTimeMillis()));
    insertGameTableIntoDatabase(gameTable);
    return newGameTable;
  }

  public static void updateGameTableInDB(GameTable gameTable){
    SessionFactory sf = new Configuration().configure("/META-INF/hibernate.cfg.xml").buildSessionFactory();
    Session session = sf.openSession();
    Transaction t = session.beginTransaction();
    System.out.println("SAAAAAAAAAAAAAAAKRAAAAAAAAAAAAKRA "+gameTable.getPlayer1().getGameType() +" "+gameTable.getPlayer1().getId()+" "+gameTable.getPlayer1().getScore());
    System.out.println("SAAAAAAAAAAAAAAAKRAAAAAAAAAAAAKRA "+gameTable.getPlayer2().getGameType() +" "+gameTable.getPlayer2().getId()+" "+gameTable.getPlayer2().getScore());
    session.update(gameTable.getPlayer1());
    session.update(gameTable.getPlayer2());
    session.update(gameTable);
    t.commit();
    session.close();
    sf.close();
  }

  private static void insertGameTableIntoDatabase(GameTable gameTable){
    SessionFactory sf = new Configuration().configure("/META-INF/hibernate.cfg.xml").buildSessionFactory();
    Session session = sf.openSession();
    Transaction t = session.beginTransaction();
    session.persist(gameTable);
    t.commit();
    session.close();
    sf.close();
  }

}
