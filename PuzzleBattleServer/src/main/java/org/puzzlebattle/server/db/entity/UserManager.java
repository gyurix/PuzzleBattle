package org.puzzlebattle.server.db.entity;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.mindrot.jbcrypt.BCrypt;
import org.puzzlebattle.core.utils.ErrorAcceptedConsumer;
import org.puzzlebattle.core.utils.Logging;
import org.puzzlebattle.server.ThreadUtils;
import org.puzzlebattle.server.db.DB;
import org.puzzlebattle.server.db.UserGameAttributes;

import java.util.List;
import java.util.function.Consumer;

/**
 * Class contains methods for log in, password verification, inserting and updating user
 *
 * @author Jakub Perdek
 * @version 1.0
 */
public class UserManager {

  /**
   * Method which obtains registered user from database using hashed form of password,
   * if passwords are not equal as strings null will be returned
   *
   * @param nickName nickName of user
   * @return registered user, stored in database
   */
  public static void findUser(String nickName, ErrorAcceptedConsumer<User> resultHandler) {
    DB.INSTANCE.withSession((s) -> {
      try {
        String hql = "FROM User WHERE nickName=?1";
        Query<User> query = s.createQuery(hql);
        query.setParameter(1, nickName);
        List<User> list = query.list();
        if (list.size() > 0) {
          User registeredUser = list.get(0);
          resultHandler.accept(registeredUser);
          return;
        }
      } catch (Throwable e) {
        Logging.logSevere("Error on finding registered user.", "nick", nickName, "error", e);
      }
      resultHandler.accept(null);
    });
  }

  /**
   * Method which returns best players suitable to be stored in table
   *
   * @param maxPlayers maximum best players which should be displayed
   * @return list of best players
   */
  public static ObservableList<UserGameAttributes> getBestPlayers(int maxPlayers) {
    SessionFactory sf = new Configuration().configure("/META-INF/hibernate.cfg.xml").buildSessionFactory();
    Session session = sf.openSession();

    String hql = "SELECT  u, g.score FROM GamePlayer g LEFT JOIN User u ON u.id = g.player ORDER BY g.score DESC";
    Query query = session.createQuery(hql);
    query.setMaxResults(maxPlayers);
    List<Object[]> list = null;
    list = query.list();
    ObservableList<UserGameAttributes> userGameAttributes = FXCollections.observableArrayList();
    for (Object[] object : list) {
      if (object[0] != null) {
        userGameAttributes.add(new UserGameAttributes(((User) object[0]).getNickName(), ((int) object[1])));
      }
    }

    session.close();
    sf.close();

    return userGameAttributes;
  }

  /**
   * Method which hashes password and returns its hash form
   *
   * @param password which should be hashed
   * @return hashed password
   */
  public static String hashPassword(String password) {
    return BCrypt.hashpw(password, BCrypt.gensalt());
  }

  /**
   * Registers a new user
   *
   * @param user          - The registrable user
   * @param resultHandler - Result handler, getting the registered user, stored in database
   *                      or null if the registration process failed, because there is a user with the same nick
   *                      in the database already
   */
  public static void registerUser(User user, ErrorAcceptedConsumer<User> resultHandler) {
    findUser(user.getNickName(), (r) -> {
      if (r != null) {
        resultHandler.accept(null);
        return;
      }
      user.persist((r2) -> resultHandler.accept(r2 ? user : null));
    });
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
  public static void withRegisterUser(String nickName, String password, Consumer<User> resultHandler) {
    ThreadUtils.async(() -> {
      SessionFactory sf = new Configuration().configure("/META-INF/hibernate.cfg.xml").buildSessionFactory();
      try (Session session = sf.openSession()) {
        String hql = "FROM User WHERE nickName=?1";
        Query<User> query = session.createQuery(hql, User.class);
        query.setParameter(1, nickName);
        List<User> list = query.list();
        if (list.size() > 0) {
          User registeredUser = list.get(0);
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
