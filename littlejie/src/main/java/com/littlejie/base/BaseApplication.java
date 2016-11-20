package com.littlejie.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Handler;

import com.littlejie.utils.Constants;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.decode.BaseImageDecoder;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Lion on 2016/6/14.
 */
public class BaseApplication extends Application {

    private static List<Activity> mLstActivities = new ArrayList<Activity>();

    // 用于在测试环境下显示当前activity的log
    private static Map<Activity, StringBuffer> mMapActivityLog = new HashMap<>();

    /**
     * uiThreadHandler
     */
    private static Handler uiThreadHandler = null;

    /**
     * uiThread
     */
    private static Thread uiThread = null;

    public static void runOnUIThread(Runnable work) {
        if (Thread.currentThread() != uiThread) {
            uiThreadHandler.post(work);
        } else {
            work.run();
        }
    }

    public static void runDelayOnUIThread(Runnable work, long time) {
        uiThreadHandler.postDelayed(work, time);
    }

    public static void removeRunnable(Runnable work) {
        uiThreadHandler.removeCallbacks(work);
    }

    private static Context instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        uiThreadHandler = new Handler();
        Core.initBase();
        initUIL(this);
    }

    public static Context getInstance() {
        return instance;
    }

    private void initUIL(Context context) {
        String cacheDir = Constants.PATH_CACHE_FOLDER;
        if (cacheDir != null) {
            File file = new File(cacheDir);

            ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
//                    .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
                    .memoryCache(new WeakMemoryCache())
                    .memoryCacheSize(5 * 1024 * 1024)
                    .memoryCacheSizePercentage(13) // default
                    .diskCache(new UnlimitedDiskCache(file)) // default
                    .diskCacheSize(100 * 1024 * 1024)
                    .diskCacheFileCount(100)
                    .diskCacheFileNameGenerator(new HashCodeFileNameGenerator()) // default
                    .imageDownloader(new BaseImageDownloader(context)) // default
                    .imageDecoder(new BaseImageDecoder(true)) // default
                    .defaultDisplayImageOptions(DisplayImageOptions.createSimple()) // default
                    .writeDebugLogs()
                    .build();

            ImageLoader.getInstance().init(config);
        }
    }

    public static void addActivity(Activity activity) {
        mLstActivities.add(activity);
    }

    public static void removeActivity(Activity activity) {
        mLstActivities.remove(activity);
        mMapActivityLog.remove(activity);
    }

    public static void removeAllActivities() {
        mLstActivities.clear();
        mMapActivityLog.clear();
    }

    public static void finishAllActivities() {
        for (Activity activity : mLstActivities) {
            if (activity != null && !activity.isFinishing()) {
                activity.finish();
            }
        }
        removeAllActivities();
    }

    public static void finishActivities(List<Class> lstClass) {
        Iterator it = mLstActivities.iterator();
        while (it.hasNext()) {
            Activity activity = (Activity) it.next();
            if (lstClass.contains(activity.getClass())) {
                activity.finish();
                it.remove();
                if (mMapActivityLog.containsKey(activity)) {
                    mMapActivityLog.remove(activity);
                }
            }
        }
    }

    public static boolean hasActivitiesExcept(List<Class> lstCls) {
        Iterator it = mLstActivities.iterator();
        while (it.hasNext()) {
            boolean exist = false;
            Activity activity = (Activity) it.next();
            for (Class cls : lstCls) {
                if (cls == activity.getClass()) {
                    exist = true;
                    break;
                }
            }
            if (exist) {
                continue;
            } else {
                return true;
            }
        }
        return false;
    }

    public static void finishActivitiesExcept(Class cls) {
        Iterator it = mLstActivities.iterator();
        while (it.hasNext()) {
            Activity activity = (Activity) it.next();
            if (activity.getClass() != cls) {
                activity.finish();
                it.remove();
                if (mMapActivityLog.containsKey(activity)) {
                    mMapActivityLog.remove(activity);
                }
            }
        }
    }

    public static boolean hasActivityVisible() {
        return mLstActivities.size() > 0;
    }

    public static void finishActivitiesExcept(Activity activity) {
        Iterator it = mLstActivities.iterator();
        while (it.hasNext()) {
            Activity a = (Activity) it.next();
            if (a != activity) {
                a.finish();
                it.remove();
                if (mMapActivityLog.containsKey(a)) {
                    mMapActivityLog.remove(a);
                }
            }
        }
    }

    public static Activity getActivity(Class cls) {
        for (Activity activity : mLstActivities) {
            if (activity.getClass() == cls) {
                return activity;
            }
        }
        return null;
    }

    public static Activity getLatestActivity() {
        if (mLstActivities.size() <= 0) {
            return null;
        }
        return mLstActivities.get(mLstActivities.size() - 1);
    }

    public static List<Activity> getRunActivities() {
        return mLstActivities;
    }

    public static String getActivityLog(Context context) {
        if (context != null &&
                context instanceof Activity &&
                mMapActivityLog.containsKey(context)) {
            return mMapActivityLog.get(context).toString();
        }
        return null;
    }

    public static void addActivityLog(Context context, String text) {
        if (context != null && context instanceof Activity) {
            if (!mMapActivityLog.containsKey(context)) {
                mMapActivityLog.put((Activity) context, new StringBuffer(text));
            } else {
                StringBuffer sb = mMapActivityLog.get(context);
                sb.append("<br><br>").append(text);
            }
        }
    }

    @Override
    public void onTerminate() {
        removeAllActivities();
    }
}
