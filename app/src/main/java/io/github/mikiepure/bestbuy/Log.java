package io.github.mikiepure.bestbuy;

import java.util.concurrent.atomic.AtomicInteger;

public class Log {
  private static final String TAG = "BestBuy";
  private static AtomicInteger counter = new AtomicInteger();

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

  public static void info(String msg, Object... args) {
    android.util.Log.i(TAG, getLogMsg(msg, args));
  }

  public static void debug(String msg, Object... args) {
    android.util.Log.d(TAG, getLogMsg(msg, args));
  }

  public static void verbose(String msg, Object... args) {
    android.util.Log.v(TAG, getLogMsg(msg, args));
  }

  private static String getLogMsg(String msg, Object... args) {
    final String count = String.format("%02X", counter.getAndIncrement() & 0xFF);
    StringBuilder argsMsg = new StringBuilder();
    for (Object arg : args) argsMsg.append(" ").append(arg.toString());
    return count + " " + msg + argsMsg.toString();
  }
}
