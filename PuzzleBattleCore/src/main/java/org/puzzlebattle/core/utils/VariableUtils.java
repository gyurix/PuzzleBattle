package org.puzzlebattle.core.utils;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

/**
 * Utilities for filling variables in Strings
 *
 * @author Juraj Barath
 * @version 1.0
 */

public class VariableUtils {

  /**
   * Dumps all the included variables after the given String prefix
   *
   * @param in   - The String prefix
   * @param vars - The dumpable variables in a key-value format
   * @return - The String prefixed with in and suffixed with the dumped variables
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
        out.append(' ').append(k).append(" = ").append(throwableToString(o)).append(',');
        k = null;
      }
    }
    out.setCharAt(out.length() - 1, ' ');
    out.append('}');
    return out.toString();
  }

  /**
   * Fills the entered variables in the given String
   *
   * @param in   - The input String
   * @param vars - The fillable variables
   * @return - The input String with the variables filled in it.
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

  /**
   * Converts a Throwable or any other object to a String
   *
   * @param o - The input Throwable or other object
   * @return - The whole stack trace if the given object is a Throwable, the objects toString method call result if it's not
   */
  public static String throwableToString(Object o) {
    if (o instanceof Throwable) {
      ByteArrayOutputStream bos = new ByteArrayOutputStream();
      PrintStream ps = new PrintStream(bos);
      ((Throwable) o).printStackTrace(ps);
      ps.close();
      try {
        return "\n" + bos.toString("UTF-8");
      } catch (UnsupportedEncodingException ignored) {
      }
    }
    return String.valueOf(o);
  }
}
