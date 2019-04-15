package org.puzzlebattle.client.screen;

import lombok.Data;

@Data
public class UserGameAttributes {

  private String nickName;
  private Integer score;

  public UserGameAttributes(String nickName,int score){
    System.out.println(nickName+" "+score);
    this.score =score;
    this.nickName= nickName;
  }


}
