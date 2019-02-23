package org.puzzlebattle.core.utils;

import org.puzzlebattle.core.utils.reflection.MethodInvoker;
import org.puzzlebattle.core.utils.reflection.Reflection;

import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import static java.util.logging.Level.*;

public class Logging {
  private static final MethodInvoker doLog = Reflection.getMethod(Logger.class, "doLog", LogRecord.class);

  private static void log(Level level, String msg, Object[] vars) {
    LogRecord record = new LogRecord(level, VariableUtils.dump(msg, vars));
    StackTraceElement caller = Thread.currentThread().getStackTrace()[3];
    record.setSourceClassName(caller.getClassName() + ":" + caller.getLineNumber());
    record.setSourceMethodName(caller.getMethodName());
    Logger logger = Logger.getLogger(caller.getClassName());
    doLog.call(logger, record);
  }

  public static void logConfig(String msg, Object... vars) {
    log(CONFIG, msg, vars);
  }

  public static void logFine(String msg, Object... vars) {
    log(FINE, msg, vars);
  }

  public static void logFiner(String msg, Object... vars) {
    log(FINER, msg, vars);
  }

  public static void logFinest(String msg, Object... vars) {
    log(FINEST, msg, vars);
  }

  public static void logInfo(String msg, Object... vars) {
    log(INFO, msg, vars);
  }

  public static void logSevere(String msg, Object... vars) {
    log(SEVERE, msg, vars);
  }

  public static void logWarning(String msg, Object... vars) {
    log(WARNING, msg, vars);
  }
}
