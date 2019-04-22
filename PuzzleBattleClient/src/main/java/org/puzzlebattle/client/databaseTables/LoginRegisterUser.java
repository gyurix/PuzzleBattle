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
import org.puzzlebattle.client.utils.ThreadUtils;

import java.util.List;
import java.util.function.Consumer;

import static org.puzzlebattle.core.utils.Logging.logInfo;

/**
 * Class contains methods for log in, password verification, inserting and updating user
 *
 * @author Jakub Perdek
 * @version 1.0
 */
public class LoginRegisterUser {

  /**
   * Method which returns best players suitable to be stored in table
   *
   * @param maxPlayers maximum best players which should be displayed
   * @return list of best players
   */
  public static ObservableList<UserGameAttributes> getBestPlayers(int maxPlayers) {
    SessionFactory sf = new Configuration().configure("/META-INF/hibernate.cfg.xml").buildSessionFactory();
    Session session = sf.openSession();

    String hql = "SELECT  u, g.score FROM GamePlayer g LEFT JOIN UserPuzzleBattle u ON u.id = g.player ORDER BY g.score DESC";
    Query query = session.createQuery(hql);
    query.setMaxResults(maxPlayers);
    List<Object[]> list = null;
    list = query.list();
    ObservableList<UserGameAttributes> userGameAttributes = FXCollections.observableArrayList();
    for (Object[] object : list) {
      if (object[0] != null) {
        userGameAttributes.add(new UserGameAttributes(((UserPuzzleBattle) object[0]).getNickName(), ((int) object[1])));
      }
    }

    session.close();
    sf.close();

    return userGameAttributes;
  }

  /**
   * Method which obtains registered user from database using hashed form of password,
   * if passwords are not equal as strings null will be returned
   *
   * @param nickName nickName of user
   * @param password password of user, hashed
   * @return registered user, stored in database
   */
  public static UserPuzzleBattle getRegister(String nickName, String password) {
    SessionFactory sf = new Configuration().configure("/META-INF/hibernate.cfg.xml").buildSessionFactory();
    Session session = sf.openSession();
    UserPuzzleBattle registeredUser = null;

    String hql = "FROM UserPuzzleBattle WHERE nickName=?1 AND password= ?2";
    Query query = session.createQuery(hql);
    query.setParameter(1, nickName);
    query.setParameter(2, password);
    List<UserPuzzleBattle> list = query.list();
    if (list.size() > 0) {
      registeredUser = list.get(0);
    }

    session.close();
    sf.close();
    return registeredUser;
  }

  /**
   * Method which hashes password and returns its hash form
   *
   * @param password which should be hashed
   * @return hashed password
   */
  private static String hashPassword(String password) {
    return BCrypt.hashpw(password, BCrypt.gensalt());
  }

  /**
   * Creates new user and saves information about him to database
   *
   * @param nickname nickName of user
   * @param email    email of user
   * @param password password of user, non-hashed
   */
  public static void registerUser(String nickname, String email, String password) {
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

  /**
   * Verification of non-hashed password with hashed password, usually obtained from  database
   *
   * @param password        non-hashed password, plain password
   * @param cryptedPassword hashed password
   * @return true, if passwords are the same, otherwise false
   */
  private static boolean verifyPassword(String password, String cryptedPassword) {
    return BCrypt.checkpw(password, cryptedPassword);
  }

  /**
   * Returns user according his nickname and verifies stored hashed password with non-hashed once
   *
   * @param nickName nickName of user
   * @param password password of user, non-hashed
   * @return user or null if user was not found or passwords are not equal
   */
  public static void withRegisterUser(String nickName, String password, Consumer<UserPuzzleBattle> resultHandler) {
    ThreadUtils.async(() -> {
      logInfo("withRegisterUser", "nick", nickName, "password", password.length(), "resultHandler", resultHandler);
      SessionFactory sf = new Configuration().configure("/META-INF/hibernate.cfg.xml").buildSessionFactory();
      logInfo("Created sessionFactory");
      try (Session session = sf.openSession()) {
        logInfo("Opened session");
        String hql = "FROM UserPuzzleBattle WHERE nickName=?1";
        Query<UserPuzzleBattle> query = session.createQuery(hql, UserPuzzleBattle.class);
        query.setParameter(1, nickName);
        List<UserPuzzleBattle> list = query.list();
        logInfo("Executed query");
        if (list.size() > 0) {
          UserPuzzleBattle registeredUser = list.get(0);
          if (verifyPassword(password, registeredUser.getPassword())) {
            ThreadUtils.ui(() -> resultHandler.accept(registeredUser));
            return;
          }
        }
        ThreadUtils.ui(() -> resultHandler.accept(null));
      }
    });
  }
}
