package com.littlejie.android.demo;

import com.littlejie.base.BaseApplication;
import com.squareup.leakcanary.LeakCanary;

/**
 * Created by Lion on 2016/4/21.
 */
public class DemoApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);
    }
}
