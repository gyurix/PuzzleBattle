package org.puzzlebattle.server.db.entity;


import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Entity;

/**
 * Abstract class for storing game settings
 *
 * @author Jakub Perdek
 * @version 1.0
 */
@AllArgsConstructor
@Entity
@Data
public class GameSettings extends AbstractEntity {
  protected int gameType;
  protected String profileName;
}

