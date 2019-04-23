package org.puzzlebattle.client.games;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@AllArgsConstructor
@Data
public class UserPuzzleBattle {
  private String userName;
  private String password;
  private int age;
  private byte[] avatar;
  private Date dateOfBirth;
  private String email;
  private int id;
  private String name;
  private String nickName;
  private String surname;
}
