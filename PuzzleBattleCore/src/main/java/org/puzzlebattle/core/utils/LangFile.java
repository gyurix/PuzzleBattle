package org.puzzlebattle.core.utils;

import com.google.gson.JsonObject;
import org.puzzlebattle.core.utils.reflection.Reflection;

import java.util.HashMap;

/**
 * Language files are ordered here.
 *
 * @author (Juraj Barath)
 * @version (1.0)
 */

public class LangFile {
  private HashMap<String, String> mapping = new HashMap<String, String>();
  private LangFile parent;


  /**
   * Language file
   */

  public LangFile(String language) {
    String fileName = "lang/" + language + ".json";
    IOUtils.saveResources(fileName);
    JsonObject json = Reflection.GSON.fromJson(IOUtils.fileToString("lang/" + language + ".json"), JsonObject.class);
    load(json);
  }


  /**
   * Language file, additionally parent can be specified
   */

  public LangFile(LangFile parent, String language) {
    this(language);
    this.parent = parent;
  }


  /**
   * Get
   *
   * @param  key  key
   * @param  vars  list of variables
   * @return    string from variables
   */

  public String get(String key, Object... vars) {
    String value = mapping.get(key);

    if (value == null && parent != null)
      return parent.get(key, vars);

    if (value == null)
      return VariableUtils.dump("Invalid key (" + key + ")", vars);

    return VariableUtils.fill(value, vars);
  }


  /**
   * Loading objects using Json
   *
   * @param  json  json to load objects
   */

  private void load(JsonObject json) {
    json.entrySet().forEach((e) -> {
      if (e.getValue() instanceof JsonObject)
        load(e.getKey(), (JsonObject) e.getValue());
      else
        mapping.put(e.getKey(), e.getValue().getAsString());
    });
  }


  /**
   * Loading values using a key
   *
   * @param  key  key
   * @param  json  json to load objects
   */

  private void load(String key, JsonObject json) {
    json.entrySet().forEach(e -> {
      if (e.getValue() instanceof JsonObject)
        load(key + "." + e.getKey(), (JsonObject) e.getValue());
      else
        mapping.put(key + "." + e.getKey(), e.getValue().getAsString());
    });
  }


  /**
   * Prints a message
   *
   * @param  key  key
   * @param  vars list of variables
   */

  public void msg(String key, Object... vars) {
    System.out.println(get(key, vars));
  }
}
