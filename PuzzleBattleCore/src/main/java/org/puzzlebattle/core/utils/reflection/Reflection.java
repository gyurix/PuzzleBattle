package org.puzzlebattle.core.utils.reflection;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Write a description of class SkladPonuka here.
 *
 * @author (Juraj Barath)
 * @version (1.0)
 */

public class Reflection {
  public static final Gson GSON = new Gson();
  static HashMap<FieldAccessor, FieldAccessor> fields = new HashMap<FieldAccessor, FieldAccessor>();
  static HashMap<MethodInvoker, MethodInvoker> methods = new HashMap<MethodInvoker, MethodInvoker>();

  public static void applyJson(Object o, JsonObject json) {
    Class cl = o.getClass();
    for (Map.Entry<String, JsonElement> e : json.entrySet()) {
      FieldAccessor field = getField(cl, e.getKey());
      if (field.getField() == null) {
        System.err.println("Error, field " + e.getKey() + " was not found in class " + cl.getSimpleName());
        continue;
      }
      JsonElement el = e.getValue();
      if (el instanceof JsonObject) {
        Object obj = field.get(o);
        if (obj != null) {
          applyJson(obj, (JsonObject) el);
          continue;
        }
      }
      field.set(o, GSON.fromJson(el, field.getField().getGenericType()));
    }
  }

  public static FieldAccessor getField(Class cl, String name) {
    return new FieldAccessor(cl, name);
  }

  public static MethodInvoker getMethod(Class cl, String name, Class... classes) {
    return new MethodInvoker(cl, name, classes);
  }
}
