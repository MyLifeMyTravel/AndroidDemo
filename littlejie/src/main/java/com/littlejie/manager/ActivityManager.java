package com.littlejie.manager;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

/**
 * Created by Lion on 2016/4/6.
 */
public class ActivityManager {

    public static void startActivity(Context context, Class cls) {
        Intent intent = new Intent(context, cls);
        context.startActivity(intent);
    }

    public static int getInt(Intent intent, String key, int defaultValue) {
        String value = intent.getStringExtra(key);
        try {
            if (!TextUtils.isEmpty(value)) {
                return Integer.parseInt(value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defaultValue;
    }

    public static long getLong(Intent intent, String key, long defaultValue) {
        String value = intent.getStringExtra(key);
        try {
            return Long.parseLong(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defaultValue;
    }

    public static float getFloat(Intent intent, String key, float defaultValue) {
        String value = intent.getStringExtra(key);
        try {
            return Float.parseFloat(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defaultValue;
    }

    public static double getDouble(Intent intent, String key, double defaultValue) {
        String value = intent.getStringExtra(key);
        try {
            return Double.parseDouble(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defaultValue;
    }

    public static String getString(Intent intent, String key) {
        return intent.getStringExtra(key);
    }

    public static boolean getBoolean(Intent intent, String key, boolean defaultValue) {
        String value = intent.getStringExtra(key);
        try {
            return Boolean.parseBoolean(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defaultValue;
    }

}
