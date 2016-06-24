package com.littlejie.base;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.Toast;

import com.littlejie.crash.CrashHandler;
import com.littlejie.utils.Constants;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

/**
 * Created by Lion on 2016/6/14.
 */
public class Core {

    private static final String TAG = "Core";

    private static DisplayImageOptions.Builder mDIODiskCache;
    private static DisplayImageOptions.Builder mDIOMemCache;
    private static DisplayImageOptions.Builder mDIODiskCacheRec;
    private static DisplayImageOptions.Builder mDIOMemCacheRec;
    private static Typeface mTypeface;
    private static Context mApplicationContext;

    private Core() {
    }

    public static Context getApplicationContext() {
        return mApplicationContext;
    }

    public static void initBase() {
        mApplicationContext = BaseApplication.getInstance();
        CrashHandler.getInstance().init(mApplicationContext
                , Constants.PATH_CRASH_FOLDER);
        mDIODiskCache = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .imageScaleType(ImageScaleType.EXACTLY);

        mDIOMemCache = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(false)
                .imageScaleType(ImageScaleType.EXACTLY);

        mDIODiskCacheRec = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .imageScaleType(ImageScaleType.EXACTLY);

        mDIOMemCacheRec = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(false)
                .imageScaleType(ImageScaleType.EXACTLY);
    }

    public static Typeface getTypeface() {
        return mTypeface;
    }

    public static void loadDiskCachedImage(String url, int resId) {
        ImageLoader.getInstance().loadImage(url, mDIODiskCacheRec.showImageOnLoading(resId).showImageOnFail(resId).build(), new SimpleImageLoadingListener() {
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                super.onLoadingComplete(imageUri, view, loadedImage);
            }
        });
    }

    public static void loadDiskCachedImage(String url, int resId, int cornerRadius) {

        ImageLoader.getInstance().loadImage(url, mDIODiskCacheRec.showImageOnLoading(resId).showImageOnFail(resId).displayer(new RoundedBitmapDisplayer(cornerRadius)).build(), new SimpleImageLoadingListener() {
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                super.onLoadingComplete(imageUri, view, loadedImage);
            }
        });
    }

    public static void setMemCachedImage(String url, ImageView iv, int resId, int cornerRadius) {
        try {
            ImageLoader.getInstance().displayImage(url, iv,
                    mDIOMemCacheRec.showImageOnLoading(resId).showImageOnFail(resId)
                            .displayer(new RoundedBitmapDisplayer(cornerRadius)).build());
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        }
    }

    public static void setMemCachedImage(String url, ImageView iv, int resId) {
        try {
            ImageLoader.getInstance().displayImage(url, iv,
                    mDIOMemCache.showImageOnLoading(resId).showImageOnFail(resId).build());
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        }
    }

    public static void setDiskCachedImage(String url, ImageView iv) {
        try {
            ImageLoader.getInstance().displayImage(url, iv, mDIODiskCache.build());
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        }
    }

    public static void setDiskCachedImage(String url, ImageView iv, int resId) {
        try {
            if (iv.getDrawable() == null) {
                ImageLoader.getInstance().displayImage(url, iv,
                        mDIODiskCache.showImageOnLoading(resId).showImageOnFail(resId).build());
            } else {
                ImageLoader.getInstance().displayImage(url, iv,
                        mDIODiskCache.showImageOnFail(resId).build());
            }
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        }
    }

    public static void setDiskCachedImage(String url, ImageView iv, int resId, int cornerRadius) {
        try {
            if (iv.getDrawable() == null) {
                ImageLoader.getInstance().displayImage(url, iv,
                        mDIODiskCacheRec.showImageOnLoading(resId).showImageOnFail(resId)
                                .displayer(new RoundedBitmapDisplayer(cornerRadius)).build());
            } else {
                ImageLoader.getInstance().displayImage(url, iv,
                        mDIODiskCacheRec.showImageOnFail(resId)
                                .displayer(new RoundedBitmapDisplayer(cornerRadius)).build());
            }
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        }
    }

    public static void setDiskCachedImage(String url, ImageView iv, DisplayImageOptions options) {
        try {
            ImageLoader.getInstance().displayImage(url, iv, options);
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        }
    }

    public static void setDiskCachedImage(String url, ImageView iv, int resId, final OnImageLoadListener listener) {
        try {
            ImageLoader.getInstance().displayImage(url, iv,
                    iv.getDrawable() == null ?
                            mDIODiskCache.showImageOnLoading(resId).showImageOnFail(resId).build() :
                            mDIODiskCacheRec.showImageOnFail(resId).build(), new ImageLoadingListener() {
                        @Override
                        public void onLoadingStarted(String s, View view) {

                        }

                        @Override
                        public void onLoadingFailed(String s, View view, FailReason failReason) {
                            if (listener != null) {
                                listener.onLoadingFailed(s, view, failReason);
                            }
                        }

                        @Override
                        public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                            if (listener != null) {
                                listener.onLoadindComplete(s, view, bitmap);
                            }
                        }

                        @Override
                        public void onLoadingCancelled(String s, View view) {

                        }
                    });
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        }
    }

    public static void setDiskCachedImage(String url, ImageView iv, int resId, int cornerRadius, final OnImageLoadListener listener) {
        try {
            ImageLoader.getInstance().displayImage(url, iv,
                    iv.getDrawable() == null ?
                            mDIODiskCacheRec.showImageOnLoading(resId).showImageOnFail(resId)
                                    .displayer(new RoundedBitmapDisplayer(cornerRadius)).build() :
                            mDIODiskCacheRec.showImageOnFail(resId)
                                    .displayer(new RoundedBitmapDisplayer(cornerRadius)).build(), new ImageLoadingListener() {
                        @Override
                        public void onLoadingStarted(String s, View view) {

                        }

                        @Override
                        public void onLoadingFailed(String s, View view, FailReason failReason) {
                            if (listener != null) {
                                listener.onLoadingFailed(s, view, failReason);
                            }
                        }

                        @Override
                        public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                            if (listener != null) {
                                listener.onLoadindComplete(s, view, bitmap);
                            }
                        }

                        @Override
                        public void onLoadingCancelled(String s, View view) {

                        }
                    });
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        }
    }

    public interface OnImageLoadListener {
        public void onLoadindComplete(String s, View v, Bitmap bitmap);

        public void onLoadingFailed(String s, View view, FailReason failReason);
    }

    public static void setMemCachedImage(String url, ImageView iv) {
        try {
            ImageLoader.getInstance().displayImage(url, iv, mDIOMemCache.build());
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        }
    }

    public static DisplayMetrics getDisplayMetrics() {
        DisplayMetrics dm = mApplicationContext.getResources().getDisplayMetrics();
        return dm;
    }

    public static void setViewSize(View v, int width, int height) {
        if (v.getLayoutParams() instanceof ViewGroup.LayoutParams) {
            ViewGroup.LayoutParams lp = v.getLayoutParams();
            if (lp == null) {
                lp = new ViewGroup.LayoutParams(width, height);
            } else {
                lp.width = width;
                lp.height = height;
            }
            v.setLayoutParams(lp);
        } else if (v.getLayoutParams() instanceof AbsListView.LayoutParams) {
            AbsListView.LayoutParams lp = (AbsListView.LayoutParams) v.getLayoutParams();
            if (lp == null) {
                lp = new AbsListView.LayoutParams(width, height);
            } else {
                lp.width = width;
                lp.height = height;
            }
            v.setLayoutParams(lp);
        }
    }

    public static int getPixel(int resId) {
        return mApplicationContext.getResources().getDimensionPixelSize(resId);
    }

    public static int dip2px(float dpValue) {
        final float scale = getApplicationContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(float pxValue) {
        final float scale = getApplicationContext().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static String getString(int resId) {
        return mApplicationContext.getResources().getString(resId);
    }

    public static String getString(int resId, String arg1) {
        return mApplicationContext.getResources().getString(resId, arg1);
    }

    public static String getString(int resId, String arg1, String arg2) {
        return mApplicationContext.getResources().getString(resId, arg1, arg2);
    }

    public static void showDefautToast(String s) {
        Toast.makeText(mApplicationContext, s, Toast.LENGTH_SHORT).show();
    }

    public static void showDefautToast(int resId) {
        Toast.makeText(mApplicationContext,
                getString(resId),
                Toast.LENGTH_SHORT).show();
    }

    public static void showCustomToast(Context context, int layout, int gravity, int duration) {
        View customView = LayoutInflater.from(context).inflate(layout, null);
        showCustomToast(customView, gravity, duration);
    }

    public static void showCustomToast(Context context, int layout) {
        showCustomToast(context, layout, Gravity.CENTER, Toast.LENGTH_SHORT);
    }

    public static void showCustomToast(View view) {
        showCustomToast(view, Gravity.CENTER, Toast.LENGTH_SHORT);
    }

    public static void showCustomToast(View view, int gravity, int duration) {
        Toast toast = new Toast(view.getContext());
        toast.setView(view);
        toast.setGravity(gravity, 0, 0);
        toast.setDuration(duration);
        toast.show();
    }
}
