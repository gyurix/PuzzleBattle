package org.puzzlebattle;

import org.junit.Before;
import org.junit.Test;

import java.io.UnsupportedEncodingException;

import static org.puzzlebattle.core.utils.Logging.*;

/**
 * Tester class for the Logging system
 */
public class LoggingTest {
  private String out;

  @Before
  public void init() {
    System.setProperty("java.util.logging.SimpleFormatter.format", "{%3$s} [%2$s] [%4$s]: %5$s");
  }

  @Test
  public void testConfig() {
    logConfig("Example config message", "varA", 15, "varB", 10.78);
  }

  @Test
  public void testFine() {
    logFine("Example fine message", "varA", 15, "varB", 10.78);
  }

  @Test
  public void testFiner() {
    logFiner("Example finer message", "varA", 15, "varB", 10.78);
  }

  @Test
  public void testFinest() {
    logFinest("Example finest message", "varA", 15, "varB", 10.78);
  }

  @Test
  public void testInfo() {
    logInfo("Example info message", "varA", 15, "varB", 10.78);
  }

  @Test
  public void testSevere() {
    logSevere("Example severe message", "varA", 15, "varB", 10.78);
  }

  @Test
  public void testWarning() {
    logWarning("Example warning message", "varA", 15, "varB", 10.78);
  }
}
