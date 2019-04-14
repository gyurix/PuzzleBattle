package org.puzzlebattle.client.databaseTables;

import lombok.Data;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import javax.persistence.*;
import javax.print.attribute.standard.DateTimeAtProcessing;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Timer;

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

  //private enum  winner[]= {"P1","P2","DRAW"};

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
