package com.littlejie.base;

import android.app.Application;
import android.content.Context;

/**
 * Created by Lion on 2016/6/14.
 */
public class BaseApplication extends Application {

    private static Context instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static Context getInstance() {
        return instance;
    }
}
