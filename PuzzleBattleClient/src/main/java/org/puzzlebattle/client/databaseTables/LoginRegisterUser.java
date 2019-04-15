package org.puzzlebattle.client.databaseTables;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.mindrot.jbcrypt.BCrypt;
import org.puzzlebattle.client.screen.UserGameAttributes;

import java.util.List;

public class LoginRegisterUser {

  public static void registerUser(String nickname,String email, String password) {
    SessionFactory sf = new Configuration().configure("/META-INF/hibernate.cfg.xml").buildSessionFactory();
    Session session = sf.openSession();
    Transaction t = session.beginTransaction();
    UserPuzzleBattle newUser = new UserPuzzleBattle();

    newUser.setNickName(nickname);
    newUser.setEmail(email);
    newUser.setPassword(hashPassword(password));
    session.persist(newUser);

    t.commit();
    session.close();
    sf.close();
  }

  public static UserPuzzleBattle getRegisterUser(String nickName, String password) {
    SessionFactory sf = new Configuration().configure("/META-INF/hibernate.cfg.xml").buildSessionFactory();
    Session session = sf.openSession();
    Transaction t = session.beginTransaction();
    UserPuzzleBattle registeredUser = null;

    String hql = "FROM UserPuzzleBattle WHERE nickName=?1";
    Query query = session.createQuery(hql);
    query.setParameter(1,nickName);
    List<UserPuzzleBattle> list=  query.list();
    if(list.size()>0)
    {
      registeredUser = list.get(0);
      if(verifyPassword(password,registeredUser.getPassword()))
      {
        t.commit();
        session.close();
        sf.close();
        return registeredUser;
      }
    }
    t.commit();
    session.close();
    sf.close();
    return null;
  }

  public static UserPuzzleBattle getRegister(String nickName, String password) {
    SessionFactory sf = new Configuration().configure("/META-INF/hibernate.cfg.xml").buildSessionFactory();
    Session session = sf.openSession();
    Transaction t = session.beginTransaction();
    UserPuzzleBattle registeredUser = null;

    String hql = "FROM UserPuzzleBattle WHERE nickName=?1 AND password= ?2";
    Query query = session.createQuery(hql);
    query.setParameter(1,nickName);
    query.setParameter(2,password);
    List<UserPuzzleBattle> list=  query.list();
    if(list.size()>0)
    {
      registeredUser = list.get(0);
    }

    t.commit();
    session.close();
    sf.close();
    return registeredUser;
  }

  private static boolean verifyPassword(String password,String cryptedPassword) {
    return BCrypt.checkpw(password, cryptedPassword);
  }

  private static String hashPassword(String password)
  {
    return BCrypt.hashpw(password, BCrypt.gensalt());
  }


  public static ObservableList<UserGameAttributes> getBestPlayers(int maxPlayers) {
    SessionFactory sf = new Configuration().configure("/META-INF/hibernate.cfg.xml").buildSessionFactory();
    Session session = sf.openSession();
    Transaction t = session.beginTransaction();

    String hql = "SELECT  u, g.score FROM GamePlayer g LEFT JOIN UserPuzzleBattle u ON u.id = g.player ORDER BY g.score DESC";
    Query query = session.createQuery(hql);
    query.setMaxResults(maxPlayers);
    List<Object[]> list = null;
    list = query.list();
    ObservableList<UserGameAttributes> userGameAttributes = FXCollections.observableArrayList();
    for(Object[] object: list){
      if(object[0]!=null) {
        userGameAttributes.add(new UserGameAttributes(((UserPuzzleBattle)object[0]).getNickName(),((int) object[1])));
        //System.out.println(((UserPuzzleBattle) objekt[0]).getNickName());
        //System.out.println((int) objekt[1]);
      }
    }

    t.commit();
    session.close();
    sf.close();

    return userGameAttributes;
  }
}
