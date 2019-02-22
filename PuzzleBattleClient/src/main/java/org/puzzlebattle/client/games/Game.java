package org.puzzlebattle.client.games;

import com.google.gson.JsonObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.puzzlebattle.core.utils.reflection.Reflection;

/**
 * Abstract class which represents game.
 *
 * @author (Juraj Barath)
 * @version (1.0)
 */

@AllArgsConstructor
@Getter
public abstract class Game {
  private Object serverConnection;


  /**
   * on message
   *
   * @param  o  Json object
   */

  public void onMessage(JsonObject o) {
    Reflection.applyJson(this, o);
  }
}
