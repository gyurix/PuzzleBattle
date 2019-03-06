package org.puzzlebattle.server.db.entity;

import javax.persistence.Entity;

@Entity
public class User extends Identificable {
  private String avatar;
  private String email;
  private String firstName;
  private long lastLogin;
  private String lastName;
  private String login;
  private String pwdHash;
  private long registered;
}
