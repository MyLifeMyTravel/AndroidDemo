package com.littlejie.manager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.littlejie.base.BaseApplication;
import com.littlejie.base.IntentParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Lion on 2016/4/6.
 */
public class ActivityManager {

    private ActivityManager() {
    }

    // 打开某个activity
    public static void startActivity(Context context, Class cls) {
        Intent intent = new Intent(context, cls);
        context.startActivity(intent);
    }

    public static void startActivityForResult(Activity activity, Class cls, int requestCode) {
        Intent intent = new Intent(activity, cls);
        activity.startActivityForResult(intent, requestCode);
    }

    public static void startActivity(Context context, Class cls, IntentParam ip) {
        Intent intent = new Intent(context, cls);
        addIntentExtra(intent, ip.getMap());
        context.startActivity(intent);
    }

    public static void startActivityForResult(Activity activity, Class cls, IntentParam ip, int requestCode) {
        Intent intent = new Intent(activity, cls);
        addIntentExtra(intent, ip.getMap());
        activity.startActivityForResult(intent, requestCode);
    }

    public static void startActivityByAction(Context context, Intent intent) {
        context.startActivity(intent);
    }

    private static void addIntentExtra(Intent intent, Map<String, Object> mapParam) {
        if (mapParam.size() <= 0) {
            return;
        }

        Set<String> setKey = mapParam.keySet();
        for (String key : setKey) {
            Object object = mapParam.get(key);
            if (object instanceof String) {
                intent.putExtra(key, (String) object);
            } else if (object instanceof ArrayList) {
                if (!((ArrayList) object).isEmpty()
                        && ((ArrayList) object).get(0) instanceof String) {
                    intent.putStringArrayListExtra(key, (ArrayList<String>) object);
                }
            }
            else {
                intent.putExtra(key, String.valueOf(object));
            }
        }
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

    public static List<String> getStringList(Intent intent, String key) {
        return intent.getStringArrayListExtra(key);
    }


    // 对app当前打开的activity进行操作（增加，减少，关闭等）
    public static void addActivity(Activity activity) {
        BaseApplication.addActivity(activity);
    }

    public static boolean hasActivityVisible() {
        return BaseApplication.hasActivityVisible();
    }

    public static void removeActivity(Activity activity) {
        BaseApplication.removeActivity(activity);
    }

    public static Activity getActivity(Class cls) {
        return BaseApplication.getActivity(cls);
    }

    public static void removeAllActivities() {
        BaseApplication.removeAllActivities();
    }

    public static void finishAllActivities() {
        BaseApplication.finishAllActivities();
    }

    public static void finishActivities(List<Class> lstClass) {
        BaseApplication.finishActivities(lstClass);
    }

    public static void finishActivitiesExcept(Class cls) {
        BaseApplication.finishActivitiesExcept(cls);
    }

    public static void finishActvitiesExcept(Activity activity) {
        BaseApplication.finishActivitiesExcept(activity);
    }

    public static String getActivityLog(Context context) {
        return BaseApplication.getActivityLog(context);
    }

    public static void addActivityLog(Context context, String text) {
        BaseApplication.addActivityLog(context, text);
    }

}
