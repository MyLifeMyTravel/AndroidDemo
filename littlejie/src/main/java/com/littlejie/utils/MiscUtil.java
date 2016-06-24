package com.littlejie.utils;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.DisplayMetrics;

import com.littlejie.R;
import com.littlejie.base.Core;

import java.io.File;

/**
 * Created by Lion on 2016/6/16.
 */
public class MiscUtil {

    public static Context getApplicationContext() {
        return Core.getApplicationContext();
    }

    public static void showDefautToast(String s) {
        Core.showDefautToast(s);
    }

    public static void showDefautToast(int resId) {
        Core.showDefautToast(resId);
    }

    /*-------------------------------一些Intent------------------------------------------*/

    /**
     * 调用系统相机拍照
     *
     * @param activity
     * @param path
     * @param requestCode
     */
    public static void takePhotoBySysCamera(Activity activity, String path, int requestCode) {
        try {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(path)));
            activity.startActivityForResult(intent, requestCode);
        } catch (ActivityNotFoundException e) {
            showDefautToast(R.string.activity_not_found);
            e.printStackTrace();
        }
    }

    /**
     * 调用图库选择图片
     *
     * @param activity
     * @param requestCode
     */
    public static void openGallery(Activity activity, int requestCode) {
        try {
            Intent intent = new Intent(
                    Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            activity.startActivityForResult(intent, requestCode);
        } catch (ActivityNotFoundException e) {
            showDefautToast(R.string.activity_not_found);
            e.printStackTrace();
        }
    }

    /**
     * 打开应用市场
     *
     * @param context
     * @param packageName
     */
    public static void openMarket(Context context, String packageName) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        String url = "market://details?id=" + packageName;
        Uri uri = Uri.parse(url);
        intent.setData(uri);

        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException ex) {
            ex.printStackTrace();
            MiscUtil.showDefautToast(R.string.please_install_appstore_first);
        }
    }

    /**
     * 用浏览器打开url
     *
     * @param context
     * @param url
     */
    public static void openUrl(Context context, String url) {
        Uri uri = Uri.parse(url);
        Intent it = new Intent(Intent.ACTION_VIEW, uri);
        try {
            context.startActivity(it);
        } catch (ActivityNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    /*-------------------------------一些Intent------------------------------------------*/

    /**
     * 获取application中指定的meta-data
     *
     * @param key
     * @return 如果没有获取成功(没有对应值，或者异常)，则返回值为空
     */
    public static String getAppMetaData(String key) {
        Context ctx = getApplicationContext();
        if (TextUtils.isEmpty(key)) {
            return null;
        }
        String resultData = null;
        try {
            PackageManager packageManager = ctx.getPackageManager();
            if (packageManager != null) {
                ApplicationInfo applicationInfo = packageManager.getApplicationInfo(ctx.getPackageName(), PackageManager.GET_META_DATA);
                if (applicationInfo != null) {
                    if (applicationInfo.metaData != null) {
                        resultData = applicationInfo.metaData.getString(key);
                    }
                }

            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return resultData;
    }

    public static DisplayMetrics getDisplayMetrics() {
        return Core.getDisplayMetrics();
    }

    // 获取屏幕宽度，返回像素值
    public static int getDisplayWidth() {
        return Core.getDisplayMetrics().widthPixels;
    }

    // 获取屏幕高度，返回像素值
    public static int getDisplayHeight() {
        return Core.getDisplayMetrics().heightPixels;
    }

    public static int dip2px(float dpValue) {
        return Core.dip2px(dpValue);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(float pxValue) {
        return Core.px2dip(pxValue);
    }
}
