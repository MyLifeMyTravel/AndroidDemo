package com.littlejie.base;

import android.app.Application;
import android.content.Context;
import android.os.Environment;

import com.littlejie.crash.CrashHandler;

import org.acra.ACRA;
import org.acra.annotation.ReportsCrashes;

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
        CrashHandler.getInstance().init(this, Environment.getExternalStorageDirectory().getAbsolutePath());
    }

    public static Context getInstance() {
        return instance;
    }
}
