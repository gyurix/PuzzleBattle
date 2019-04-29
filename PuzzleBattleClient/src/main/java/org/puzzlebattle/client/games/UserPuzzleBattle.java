package org.puzzlebattle.client.games;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UserPuzzleBattle {
  private String userName;
  private String password;
  private int age = 0;
  private byte[] avatar;
  private String dateOfBirth;
  private String email;
  private String name;
  private String surname;

  public UserPuzzleBattle(String userName,String password) {
    this.userName = userName;
    this.password = password;
  }
}
