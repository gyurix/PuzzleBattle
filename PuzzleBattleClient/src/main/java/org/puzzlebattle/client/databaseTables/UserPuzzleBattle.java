package org.puzzlebattle.client.databaseTables;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "userPuzzleBattle")
@Data
public class UserPuzzleBattle {

  @Id
  @GeneratedValue//(strategy=GenerationType.IDENTITY)
  private int id;

 // @Column(name = "nickName")
  private String nickName;

 // @Column(name = "email")
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
