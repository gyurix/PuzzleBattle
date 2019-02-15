package org.puzzlebattle.core.utils;

public class VariableUtils {
  public static String dump(String in, Object... vars) {
    if (vars.length < 2)
      return in;
    StringBuilder out = new StringBuilder(in).append(" {");
    String k = null;
    for (Object o : vars) {
      if (k == null)
        k = String.valueOf(o);
      else {
        out.append(' ').append(k).append(" = ").append(o).append(',');
        k = null;
      }
    }
    out.setCharAt(out.length() - 1, '}');
    return out.toString();
  }

  public static String fill(String in, Object... vars) {
    String k = null;
    for (Object o : vars) {
      if (k == null)
        k = String.valueOf(o);
      else {
        in = in.replace("<" + k + ">", String.valueOf(o));
        k = null;
      }
    }
    return in;
  }
}
