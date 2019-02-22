package org.puzzlebattle.core.utils;

/**
 * Variable units
 *
 * @author (Juraj Barath)
 * @version (1.0)
 */

public class VariableUtils {


  /**
   * Adump
   *
   * @param  in  input
   * @param  vars  list of variables
   * @return    string from variables
   */

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


  /**
   * fill
   *
   * @param  in  input
   * @param  vars  list of variables
   * @return    string from variables
   */

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
