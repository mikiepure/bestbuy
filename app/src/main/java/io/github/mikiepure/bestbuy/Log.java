package io.github.mikiepure.bestbuy;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Provide logging features for the application.
 *
 * <ul>
 *   <li>Format of error/warning log will be:
 *       <pre>MSG ARG1 ARG2 ... (FILENAME:LINE)</pre>
 *   <li>Format of information/debug/verbose log will be:
 *       <pre>MSG ARG1 ARG2 ...</pre>
 * </ul>
 */
public class Log {
  private static final String TAG = "BestBuy";
  private static AtomicInteger counter = new AtomicInteger();

  /**
   * Output error log.
   *
   * <p>The log can have optional data, which will be space separated string.
   *
   * @param msg  log message
   * @param args optional data
   */
  public static void error(String msg, Object... args) {
    final StackTraceElement calledClass = Thread.currentThread().getStackTrace()[3];
    final String logMsgWithPos =
        getLogMsg(msg, args)
            + " ("
            + calledClass.getFileName()
            + ":"
            + calledClass.getLineNumber()
            + ")";
    android.util.Log.e(TAG, logMsgWithPos);
  }

  /**
   * Output warning log.
   *
   * <p>The log can have optional data, which will be space separated string.
   *
   * @param msg  log message
   * @param args optional data
   */
  public static void warn(String msg, Object... args) {
    final StackTraceElement calledClass = Thread.currentThread().getStackTrace()[3];
    final String logMsgWithPos =
        getLogMsg(msg, args)
            + " ("
            + calledClass.getFileName()
            + ":"
            + calledClass.getLineNumber()
            + ")";
    android.util.Log.w(TAG, logMsgWithPos);
  }

  /**
   * Output information log.
   *
   * <p>The log can have optional data, which will be space separated string.
   *
   * @param msg  log message
   * @param args optional data
   */
  public static void info(String msg, Object... args) {
    android.util.Log.i(TAG, getLogMsg(msg, args));
  }

  /**
   * Output debug log.
   *
   * <p>The log can have optional data, which will be space separated string.
   *
   * @param msg  log message
   * @param args optional data
   */
  public static void debug(String msg, Object... args) {
    android.util.Log.d(TAG, getLogMsg(msg, args));
  }

  /**
   * Output verbose log.
   *
   * <p>The log can have optional data, which will be space separated string.
   *
   * @param msg  log message
   * @param args optional data
   */
  public static void verbose(String msg, Object... args) {
    android.util.Log.v(TAG, getLogMsg(msg, args));
  }

  /**
   * Output class and function name to log.
   *
   * <p>The log can have optional data, which will be space separated string.
   *
   * @param args optional data
   */
  public static void trace(Object... args) {
    final StackTraceElement calledClass = Thread.currentThread().getStackTrace()[3];
    final String className = calledClass.getClassName();
    final String simpleClassName = className.substring(className.lastIndexOf('.') + 1);
    final String msg = simpleClassName + "." + calledClass.getMethodName();
    android.util.Log.i(TAG, getLogMsg(msg, args));
  }

  private static String getLogMsg(String msg, Object... args) {
    final String count = String.format("%02X", counter.getAndIncrement() & 0xFF);
    StringBuilder argsMsg = new StringBuilder();
    for (Object arg : args) {
      argsMsg.append(" ").append(arg.toString());
    }
    return count + " " + msg + argsMsg.toString();
  }
}
