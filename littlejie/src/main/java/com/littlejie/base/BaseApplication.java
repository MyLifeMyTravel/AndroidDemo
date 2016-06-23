package com.littlejie.base;

import android.app.Application;
import android.content.Context;

import com.littlejie.utils.Constants;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.decode.BaseImageDecoder;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

import org.acra.ACRA;
import org.acra.annotation.ReportsCrashes;

import java.io.File;

/**
 * Created by Lion on 2016/6/14.
 */
@ReportsCrashes(
        formUri = "http://www.backendofyourchoice.com/reportpath"
)
public class BaseApplication extends Application {

    private static Context instance;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        ACRA.init(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
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
}
