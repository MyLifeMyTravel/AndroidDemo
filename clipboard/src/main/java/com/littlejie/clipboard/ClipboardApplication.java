package com.littlejie.clipboard;

import android.app.Application;

/**
 * Created by littlejie on 2016/12/16.
 */

public class ClipboardApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ClipboardUtil.init(this);
    }
}
