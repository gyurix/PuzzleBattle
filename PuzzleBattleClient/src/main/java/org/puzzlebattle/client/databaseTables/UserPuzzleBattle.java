package org.puzzlebattle.client.databaseTables;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;


/**
 * Table which stores data about user, his nickName and email address
 * Other additional information can be added, as name, surname, date of birth and avatar.
 *
 * @author Jakub Perdek
 * @version 1.0
 */
@Entity
@Table(name = "userPuzzleBattle")
@Data
public class UserPuzzleBattle {

  @Transient
  private int age;
  @Lob
  @Column(name = "image")
  private byte[] avatar;
  private Date dateOfBirth;
  @Column(unique = true)
  private String email;
  @Id
  @GeneratedValue
  private int id;
  private String name;
  @Column(unique = true)
  private String nickName;
  private String password;
  private String surname;

}
