package org.puzzlebattle.server.db.entity;

import io.netty.buffer.ByteBuf;
import lombok.Data;
import org.puzzlebattle.core.protocol.BufReadable;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

import static org.puzzlebattle.core.protocol.ByteBufUtils.readString;

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
public class User extends AbstractEntity implements BufReadable {

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
  private Timestamp lastLogin;
  private String name;
  @Column(unique = true)
  private String nickName;
  private String password;
  private Timestamp registered;
  private String surname;

  public void read(ByteBuf buf) {
    email = readString(buf).trim().toLowerCase();
    nickName = readString(buf);
    password = UserManager.hashPassword(readString(buf));
    name = "";
    surname = "";
    lastLogin = new Timestamp(System.currentTimeMillis());
    registered = new Timestamp(System.currentTimeMillis());
  }
}
