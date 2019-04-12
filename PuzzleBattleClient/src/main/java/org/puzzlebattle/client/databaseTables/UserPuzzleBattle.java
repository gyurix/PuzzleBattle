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
 /* public String getNickName(){return nickName;}

  public void setNickName(String nickName) {this.nickName= nickName;}

  public String getEmail() { return email; }

  public void setEmail(String email) {this.email= email; }

  public String getPassword() {return this.password;}

  public void setPassword(String password) {this.password=password;}

  public
  */
}
