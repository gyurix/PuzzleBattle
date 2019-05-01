package org.puzzlebattle.server.db.entity;

import lombok.Data;
import org.hibernate.query.Query;
import org.puzzlebattle.core.entity.GameType;
import org.puzzlebattle.core.utils.ErrorAcceptedConsumer;
import org.puzzlebattle.server.db.DB;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.List;


/**
 * Player who plays certain type of game and collecting score while playing
 *
 * @author Jakub Perdek
 * @version 1.0
 */
@Data
@Entity
@Table
public class GamePlayer extends AbstractEntity {

  private int gameType;
  @ManyToOne
  @JoinColumn
  private PBUser player;
  private int score = 0;

  public GamePlayer(PBUser player, GameType gameType) {
    this.player = player;
    this.gameType = gameType.ordinal();
  }

  /**
   * Gets the GamePlayer connected to the given user and GameType from the database.
   * If it does not exists yet, creates it.
   *
   * @param user     - The PBUser to which the GamePlayer should be connected
   * @param gameType - The GameType to which the GamePlayer should be connected
   * @param result   - The result handler accepting the GamePlayer we obtained or created
   */
  public static void withGamePlayer(PBUser user, GameType gameType, ErrorAcceptedConsumer<GamePlayer> result) {
    DB.INSTANCE.withSession((s) -> {
      String hql = "FROM GamePlayer WHERE player = ?1 AND gameType = ?2 LIMIT 1";
      Query<GamePlayer> query = s.createQuery(hql);
      query.setParameter(1, user);
      query.setParameter(2, gameType);
      List<GamePlayer> list = query.list();
      if (!list.isEmpty()) {
        result.accept(list.get(0));
        return;
      }
      GamePlayer gamePlayer = new GamePlayer(user, gameType);
    });
  }

  /**
   * Adds the given amount of score to this GamePlayer
   *
   * @param score - The addable score amount
   */
  public void addScore(int score) {
    this.score = this.score + score;
    update();
  }
}
