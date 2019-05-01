package org.puzzlebattle.server.db.entity;


import lombok.Data;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.puzzlebattle.server.db.entity.gameSettings.GameSettings;

import javax.persistence.*;
import java.sql.Timestamp;


/**
 * Table which represents one game between two players. Every game contains settings, result and interval
 *
 * @author Jakub Perdek
 * @version 1.0
 */
@Entity
@Table(name = "game")
@Data
public class GameTable {

  @ManyToOne(cascade = {CascadeType.ALL})
  private DurationDate duration;
  @ManyToOne(cascade = {CascadeType.ALL})
  private GameSettings gameSettings;
  @Id
  @GeneratedValue
  private long id;
  @ManyToOne
  private GamePlayer player1;
  @ManyToOne
  private GamePlayer player2;
  private Winner winner = Winner.DRAW;

  /**
   * Creates equal game entity with the same players, but new duration will be set
   *
   * @param gameTable       type of the game, from which the equal game entity should be created
   * @param newGameSettings game settings for certain game type
   * @return
   */
  public static GameTable createTheSameGameFromOlderGame(GameTable gameTable, GameSettings newGameSettings) {
    GameTable newGameTable = new GameTable();
    newGameTable.setPlayer1(gameTable.getPlayer1());
    newGameTable.setPlayer2(gameTable.getPlayer2());
    newGameTable.setGameSettings(newGameSettings);
    newGameTable.setDuration(new DurationDate());
    gameTable.getDuration().setStartDate(new Timestamp(System.currentTimeMillis()));
    insertGameTableIntoDatabase(newGameTable);
    return newGameTable;
  }

  /**
   * Insertion of game entity into database
   *
   * @param gameTable created game table with appropriate attributes which should be stored only
   */
  private static void insertGameTableIntoDatabase(GameTable gameTable) {
    SessionFactory sf = new Configuration().configure("/META-INF/hibernate.cfg.xml").buildSessionFactory();
    Session session = sf.openSession();
    Transaction t = session.beginTransaction();
    session.persist(gameTable);
    t.commit();
    session.close();
    sf.close();
  }

  /**
   * Preparation of game table between two users-players, players will be specified, other attributes used to create this record about played game
   *
   * @param user         user of puzzle battle game, player of the game will be obtained
   * @param test         true if it is only test
   * @param gameType     type of certain game
   * @param gameSettings settings for certain game, game type
   * @return
   */
  public static GameTable prepareGameTable(User user, boolean test, int gameType, GameSettings gameSettings) {
    GamePlayer player1;
    GamePlayer player2;
    GameTable gameTable;

    player1 = null;//GamePlayer.createGamePlayerFromUserIfNotExist(user, gameType);
    if (test) {
      player2 = GamePlayer.createVirtualPlayerForTest(gameType);
    } else {
      player2 = null;
    }

    gameTable = new GameTable();
    gameTable.setPlayer1(player1);
    gameTable.setPlayer2(player2);
    gameTable.setGameSettings(gameSettings);
    gameTable.setDuration(new DurationDate());
    gameTable.getDuration().setStartDate(new Timestamp(System.currentTimeMillis()));
    insertGameTableIntoDatabase(gameTable);
    return gameTable;
  }

  /**
   * Update of game table in database, usually after end of the game to store result and end date,...
   *
   * @param gameTable game table entity which should be updated
   */
  public static void updateGameTableInDB(GameTable gameTable) {
    SessionFactory sf = new Configuration().configure("/META-INF/hibernate.cfg.xml").buildSessionFactory();
    Session session = sf.openSession();
    Transaction t = session.beginTransaction();
    //System.out.println("SAAAAAAAAAAAAAAAKRAAAAAAAAAAAAKRA "+gameTable.getPlayer1().getGameType() +" "+gameTable.getPlayer1().getId()+" "+gameTable.getPlayer1().getScore());
    //System.out.println("SAAAAAAAAAAAAAAAKRAAAAAAAAAAAAKRA "+gameTable.getPlayer2().getGameType() +" "+gameTable.getPlayer2().getId()+" "+gameTable.getPlayer2().getScore());
    session.update(gameTable.getPlayer1());
    session.update(gameTable.getPlayer2());
    session.update(gameTable);
    t.commit();
    session.close();
    sf.close();
  }

  public enum Winner {
    P1, P2, DRAW
  }

}

