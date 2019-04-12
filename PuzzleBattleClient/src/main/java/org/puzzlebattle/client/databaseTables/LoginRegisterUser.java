package org.puzzlebattle.client.databaseTables;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.mindrot.jbcrypt.BCrypt;

import java.util.List;

public class LoginRegisterUser {

  public static void registerUser(String nickname,String email, String password)
  {
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

  public static UserPuzzleBattle getRegisterUser(String nickName, String password)
  {
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

  public static UserPuzzleBattle getRegister(String nickName, String password)
  {
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

  private static boolean verifyPassword(String password,String cryptedPassword)
  {
    return BCrypt.checkpw(password, cryptedPassword);
  }

  private static String hashPassword(String password)
  {
    return BCrypt.hashpw(password, BCrypt.gensalt());
  }
}
