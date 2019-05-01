package org.puzzlebattle.client.games;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
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

  public UserPuzzleBattle(String userName, String password, String email) {
    this.userName = userName;
    this.password = password;
    this.email = email;
  }
}
