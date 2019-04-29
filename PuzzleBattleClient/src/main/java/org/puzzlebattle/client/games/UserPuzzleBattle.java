package org.puzzlebattle.client.games;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UserPuzzleBattle {
  private int age = 0;
  private byte[] avatar;
  private String dateOfBirth;
  private String email;
  private String name;
  private String password;
  private String surname;
  private String userName;

  public UserPuzzleBattle(String userName, String password) {
    this.userName = userName;
    this.password = password;
  }
}
