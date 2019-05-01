package org.puzzlebattle.core.utils.reflection;

import com.google.gson.Gson;

import java.util.HashMap;

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

  public static FieldAccessor getField(Class cl, String name) {
    return new FieldAccessor(cl, name);
  }

  public static MethodInvoker getMethod(Class cl, String name, Class... classes) {
    return new MethodInvoker(cl, name, classes);
  }
}
