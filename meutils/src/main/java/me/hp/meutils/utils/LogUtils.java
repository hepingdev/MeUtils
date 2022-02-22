package me.hp.meutils.utils;

import android.util.Log;

/**
 * @author: HePing
 * @created: 2019/8/15.
 * @desc: 日志打印
 */
public class LogUtils {
    private static String methodName;            //所在的方法名
    private static int lineNumber;                //所在行号

    private static boolean isDebug = true;//debug模式才打印日志
    public static void setEnable(boolean debug) {
        isDebug = debug;
    }

    public static void i(String tag, String msg) {
        if (!isDebug) return;
        Log.i(tag, msg);
    }

    public static void e(String tag, String msg) {
        Log.e(tag, msg);
    }

    public static void e(String tag, String msg, Throwable t) {
        if (t == null) {
            Log.e(tag, msg);
        } else {
            Log.e(tag, msg, t);
        }
    }

    public static void w(String tag, String msg) {
        if (!isDebug) return;
        Log.w(tag, msg);
    }

    /**
     * 重要的调试参数信息显示，release不显示
     *
     * @param tag
     * @param msg
     */
    public static void d(String tag, String msg) {
        if (!isDebug) return;
//            getMethodNames(new Throwable().getStackTrace());
        Log.d(tag, msg);
    }

    public static void v(String tag, String msg) {
        if (!isDebug) return;
        Log.v(tag, msg);
    }

    private static String createLog(String log) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("[");
        buffer.append(methodName);
        buffer.append(":");
        buffer.append(lineNumber);
        buffer.append("]");
        buffer.append(log);

        return buffer.toString();
    }

    private static void getMethodNames(StackTraceElement[] sElements) {
        methodName = sElements[1].getMethodName();
        lineNumber = sElements[1].getLineNumber();
    }
}
