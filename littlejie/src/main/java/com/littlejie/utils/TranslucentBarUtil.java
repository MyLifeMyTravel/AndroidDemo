package com.littlejie.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.littlejie.manager.SystemBarTintManager;

/**
 * Created by Lion on 2016/7/15.
 */
public class TranslucentBarUtil {

    /**
     * 设置透明栏(Translucent)颜色，当color=0时，透明
     *
     * @param activity
     * @param color
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static void setTranslucentBar(@NonNull Activity activity, int color) {
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        ViewGroup rootView = (ViewGroup) activity.getWindow().getDecorView();
        rootView.setFitsSystemWindows(true);
        rootView.setClipToPadding(true);
        SystemBarTintManager tintManager = new SystemBarTintManager(activity);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintColor(color);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static void setTranslucentBar(@NonNull Activity activity, String color) {
        int parseColor = Color.parseColor(color);
        setTranslucentBar(activity, parseColor);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static void setNavigationBar(@NonNull Activity activity, int color) {
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        ViewGroup rootView = (ViewGroup) activity.getWindow().getDecorView();
        rootView.setFitsSystemWindows(true);
        rootView.setClipToPadding(true);
        SystemBarTintManager tintManager = new SystemBarTintManager(activity);
        tintManager.setNavigationBarTintEnabled(true);
        tintManager.setNavigationBarTintColor(color);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static void setNavigationBar(@NonNull Activity activity, String color) {
        int parseColor = Color.parseColor(color);
        setTranslucentBar(activity, parseColor);
    }
}
