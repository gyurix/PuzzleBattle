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

  @Id
  @GeneratedValue
  private int id;

  @Column(unique = true)
  private String nickName;

  @Column(unique = true)
  private String email;

  private String password;

  private String name;

  private String surname;

  @Transient
  private int age;

   private Date dateOfBirth;

   @Lob
   @Column(name="image")
   private byte[] avatar;

}
