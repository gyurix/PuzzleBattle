package org.puzzlebattle.client.screen;

import lombok.Data;

@Data
public class UserGameAttributes {

  private String endTimestamp;
  private String nickName;
  private Integer score;
  private String startTimestamp;

  public UserGameAttributes(String nickName, int score) {
    System.out.println(nickName + " " + score);
    this.score = score;
    this.nickName = nickName;
  }

  public UserGameAttributes(String nickName, int score, String startTimestamp, String endTimestamp) {
    System.out.println(nickName + " " + score);
    this.score = score;
    this.nickName = nickName;
    this.startTimestamp = startTimestamp;
    this.endTimestamp = endTimestamp;
  }
}
