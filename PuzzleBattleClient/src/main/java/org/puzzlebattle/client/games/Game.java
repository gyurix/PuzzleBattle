package org.puzzlebattle.client.games;

import com.google.gson.JsonObject;
import org.puzzlebattle.core.utils.reflection.Reflection;

public abstract class Game {
  public void onMessage(JsonObject o) {
    Reflection.applyJson(this, o);
  }
}
