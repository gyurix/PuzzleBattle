package org.puzzlebattle.server.db.entity;

import lombok.Data;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.puzzlebattle.server.db.UserGameAttributes;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


/**
 * Friendship between two players
 *
 * @author Jakub Perdek, Juraj Barath
 * @version 1.0
 */
@Data
@Entity
@Table
public class Friendship extends AbstractEntity {

  @ManyToOne
  @JoinColumn
  private DurationDate duration;
  @ManyToOne
  @JoinColumn
  private PBUser player1;
  @ManyToOne
  @JoinColumn
  private PBUser player2;

  public static List<UserGameAttributes> loadFriends(PBUser user) {
    SessionFactory sf = new Configuration().configure("/META-INF/hibernate.cfg.xml").buildSessionFactory();
    Session session = sf.openSession();
    SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    List<UserGameAttributes> friends = new ArrayList<>();
    List<Object[]> list;

    String hql = "FROM Friendship g LEFT JOIN PBUser u ON u.id = g.player1 " +
            "LEFT JOIN PBUser u ON u.id = g.player2 WHERE player1.id = ?1 DESC";
    Query query = session.createQuery(hql);
    list = null;
    list = query.list();
    for (Object[] object : list) {
      if (object[0] != null) {
        friends.add(new UserGameAttributes(((PBUser) object[0]).getNickName(), ((int) object[1]), (f.format((Timestamp) object[1])), (f.format((Timestamp) object[1]))));
      }
    }

    hql = "FROM Friendship g LEFT JOIN PBUser u ON u.id = g.player1 " +
            "LEFT JOIN PBUser u ON u.id = g.player2 WHERE player2.id = ?1 DESC";
    query = session.createQuery(hql);
    list = null;
    list = query.list();
    for (Object[] object : list) {
      if (object[0] != null) {
        friends.add(new UserGameAttributes(((PBUser) object[0]).getNickName(), ((int) object[1]), (f.format((Timestamp) object[1])), (f.format((Timestamp) object[1]))));
      }
    }

    session.close();
    sf.close();
    return friends;
  }
}
