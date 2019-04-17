package org.puzzlebattle.server.db.entity;

import io.netty.buffer.ByteBuf;
import org.puzzlebattle.core.protocol.BufReadable;

import javax.persistence.Entity;

import static org.puzzlebattle.core.protocol.ByteBufUtils.readString;
import static org.puzzlebattle.core.utils.HashUtils.HashType.MD5;
import static org.puzzlebattle.core.utils.HashUtils.HashType.SHA_256;
import static org.puzzlebattle.core.utils.HashUtils.hash;

@Entity
public class User extends Identificable implements BufReadable {
  private String avatar;
  private String email;
  private String firstName;
  private long lastLogin;
  private String lastName;
  private String login;
  private String pwdHash;
  private long registered;

  public void read(ByteBuf buf) {
    email = readString(buf).trim().toLowerCase();
    login = readString(buf);
    pwdHash = hash(readString(buf), SHA_256);
    firstName = "";
    lastName = "";
    avatar = "https://www.gravatar.com/avatar/" + hash(email, MD5);
    lastLogin = System.currentTimeMillis();
    registered = System.currentTimeMillis();
  }
}
