package com.littlejie.utils;

/**
 * Created by Lion on 2016/6/15.
 */
public class Logger {

    public static void init(String tag) {
        com.orhanobut.logger.Logger.init(tag);
    }

    public static void i(String message, Object... args) {
        com.orhanobut.logger.Logger.i(message, args);
    }

    public static void v(String message, Object... args) {
        com.orhanobut.logger.Logger.v(message, args);
    }

    public static void d(String message, Object... args) {
        com.orhanobut.logger.Logger.d(message, args);
    }

    public static void e(String message, Object... args) {
        com.orhanobut.logger.Logger.e(message, args);
    }

    public static void w(String message, Object... args) {
        com.orhanobut.logger.Logger.w(message, args);
    }

}
