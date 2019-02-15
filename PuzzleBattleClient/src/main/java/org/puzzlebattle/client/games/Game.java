package org.puzzlebattle.client.games;

import com.google.gson.JsonObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.puzzlebattle.core.utils.reflection.Reflection;

@AllArgsConstructor
@Getter
public abstract class Game {
  private Object serverConnection;

  public void onMessage(JsonObject o) {
    Reflection.applyJson(this, o);
  }
}
