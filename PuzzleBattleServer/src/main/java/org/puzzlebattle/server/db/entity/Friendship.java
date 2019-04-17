package org.puzzlebattle.server.db.entity;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Data;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.puzzlebattle.server.db.UserGameAttributes;

import javax.persistence.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;


/**
 * Table witch characterise friendship  between two players
 *
 * @author Jakub Perdek
 * @version 1.0
 */
@Entity
@Table(name = "friendship")
@Data
public class Friendship {

  @ManyToOne
  @JoinColumn
  private DurationDate duration;
  @Id
  @GeneratedValue
  private long id;
  @ManyToOne
  @JoinColumn
  private UserPuzzleBattle player1;
  @ManyToOne
  @JoinColumn
  private UserPuzzleBattle player2;

  public static ObservableList<UserGameAttributes> loadFriends(UserPuzzleBattle user) {
    SessionFactory sf = new Configuration().configure("/META-INF/hibernate.cfg.xml").buildSessionFactory();
    Session session = sf.openSession();
    SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    ObservableList<UserGameAttributes> friends = FXCollections.observableArrayList();
    List<Object[]> list;

    String hql = "FROM Friendship g LEFT JOIN UserPuzzleBattle u ON u.id = g.player1 " +
            "LEFT JOIN UserPuzzleBattle u ON u.id = g.player2 WHERE player1.id = ?1 DESC";
    Query query = session.createQuery(hql);
    list = null;
    list = query.list();
    for (Object[] object : list) {
      if (object[0] != null) {
        friends.add(new UserGameAttributes(((UserPuzzleBattle) object[0]).getNickName(), ((int) object[1]), (f.format((Timestamp) object[1])), (f.format((Timestamp) object[1]))));
      }
    }

    hql = "FROM Friendship g LEFT JOIN UserPuzzleBattle u ON u.id = g.player1 " +
            "LEFT JOIN UserPuzzleBattle u ON u.id = g.player2 WHERE player2.id = ?1 DESC";
    query = session.createQuery(hql);
    list = null;
    list = query.list();
    for (Object[] object : list) {
      if (object[0] != null) {
        friends.add(new UserGameAttributes(((UserPuzzleBattle) object[0]).getNickName(), ((int) object[1]), (f.format((Timestamp) object[1])), (f.format((Timestamp) object[1]))));
      }
    }

    session.close();
    sf.close();
    return friends;
  }
}
