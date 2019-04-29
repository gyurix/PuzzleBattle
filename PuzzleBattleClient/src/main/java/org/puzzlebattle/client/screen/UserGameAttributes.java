package org.puzzlebattle.client.screen;

import lombok.Data;


/**
 * User game attributes to store attributes about game players
 *
 * @author Jakub Perdek
 * @version 1.0
 */
@Data
public class UserGameAttributes {

  private String endTimestamp;
  private String nickName;
  private Integer score;
  private String startTimestamp;

  /**
   * User game Attributes
   *
   * @param nickName - nickname of user
   * @param score    - score which he gained
   */
  public UserGameAttributes(String nickName, int score) {
    this.score = score;
    this.nickName = nickName;
  }

  /**
   * User game attributes
   *
   * @param nickName       - nickname of user
   * @param score          - score which he gained
   * @param startTimestamp - time stamp of start of the game
   * @param endTimestamp   - end timestamp of the game
   */
  public UserGameAttributes(String nickName, int score, String startTimestamp, String endTimestamp) {
    this.score = score;
    this.nickName = nickName;
    this.startTimestamp = startTimestamp;
    this.endTimestamp = endTimestamp;
  }

}
