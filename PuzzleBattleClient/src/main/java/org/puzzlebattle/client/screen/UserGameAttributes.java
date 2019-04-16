package org.puzzlebattle.client.screen;

import javafx.util.converter.TimeStringConverter;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class UserGameAttributes {

  private String nickName;
  private Integer score;
  private String startTimestamp;
  private String endTimestamp;

  public UserGameAttributes(String nickName,int score){
    System.out.println(nickName+" "+score);
    this.score =score;
    this.nickName= nickName;
  }

  public UserGameAttributes(String nickName,int score, String startTimestamp, String endTimestamp){
    System.out.println(nickName+" "+score);
    this.score =score;
    this.nickName= nickName;
    this.startTimestamp = startTimestamp;
    this.endTimestamp = endTimestamp;
  }
}
