package org.puzzlebattle.client.databaseTables;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "userPuzzleBattle")
public class UserPuzzleBattle {

  @Id
  @GeneratedValue//(strategy=GenerationType.IDENTITY)
  private int id;


 // @Column(name = "nickName")
  private String nickName;

 // @Column(name = "email")
   private String email;

   private String password;
 // @Column(name = "firstName")
 // String firstName;

 // public int getId() {return id;}

  //public void setId(int id) {this.id = id;}

  public String getNickName(){return nickName;}

  public void setNickName(String nickName) {this.nickName= nickName;}

  public String getEmail() { return email; }

  public void setEmail(String email) {this.email= email; }

  public String getPassword() {return this.password;}

  public void setPassword(String password) {this.password=password;}

}
