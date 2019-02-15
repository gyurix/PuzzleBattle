package org.puzzlebattle.core.utils;

import com.google.gson.JsonObject;
import org.puzzlebattle.core.utils.reflection.Reflection;

import java.util.HashMap;

public class LangFile {
  private HashMap<String, String> mapping = new HashMap<String, String>();
  private LangFile parent;

  public LangFile(String language) {
    String fileName = "lang/" + language + ".json";
    IOUtils.saveResources(fileName);
    JsonObject json = Reflection.GSON.fromJson(IOUtils.fileToString("lang/" + language + ".json"), JsonObject.class);
    load(json);
  }

  public LangFile(LangFile parent, String language) {
    this(language);
    this.parent = parent;
  }

  public String get(String key, Object... vars) {
    String value = mapping.get(key);

    if (value == null && parent != null)
      return parent.get(key, vars);

    if (value == null)
      return VariableUtils.dump("Invalid key (" + key + ")", vars);

    return VariableUtils.fill(value, vars);
  }

  private void load(JsonObject json) {
    json.entrySet().forEach((e) -> {
      if (e.getValue() instanceof JsonObject)
        load(e.getKey(), (JsonObject) e.getValue());
      else
        mapping.put(e.getKey(), e.getValue().getAsString());
    });
  }

  private void load(String key, JsonObject json) {
    json.entrySet().forEach(e -> {
      if (e.getValue() instanceof JsonObject)
        load(key + "." + e.getKey(), (JsonObject) e.getValue());
      else
        mapping.put(key + "." + e.getKey(), e.getValue().getAsString());
    });
  }

  public void msg(String key, Object... vars) {
    System.out.println(get(key, vars));
  }
}
