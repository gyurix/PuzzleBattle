package org.puzzlebattle.core.utils;

import java.io.*;
import java.nio.charset.Charset;


/**
 * Write a description of class SkladPonuka here.
 *
 * @author (Juraj Barath)
 * @version (1.0)
 */

public class IOUtils {
  public static final Charset UTF8 = Charset.forName("UTF-8");


  /**
   * File to string, if can't convert, then returns null
   *
   * @param fileName name of the file
   * @return string in UTF8
   */

  public static String fileToString(String fileName) {
    try {
      return new String(readStreamFully(new FileInputStream(fileName)), UTF8);
    } catch (Throwable e) {
      System.err.println("Failed to convert file (" + fileName + ") to String:");
      e.printStackTrace();
    }
    return null;
  }


  /**
   * Reads and transloads stream
   *
   * @param is input stream
   * @return byte buffer
   */

  public static byte[] readStreamFully(InputStream is) {
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    transloadStream(is, bos);
    return bos.toByteArray();
  }


  /**
   * Saving resources
   *
   * @param names list of names
   */

  public static void saveResources(String... names) {
    for (String name : names) {
      try {
        File f = new File(name).getAbsoluteFile();
        f.getParentFile().mkdirs();
        transloadStream(IOUtils.class.getClassLoader().getResourceAsStream(name), new FileOutputStream(f));
      } catch (Throwable e) {
        System.err.println("Failed to save resource " + name);
        e.printStackTrace();
      }
    }
  }


  /**
   * Transloads of the stream
   *
   * @param is input stream
   * @param os output stream
   */

  public static void transloadStream(InputStream is, OutputStream os) {
    try {
      byte[] buf = new byte[2048];
      for (int read = is.read(buf); read > 0; read = is.read(buf))
        os.write(buf, 0, read);
    } catch (Throwable e) {
      e.printStackTrace();
    }
  }
}
