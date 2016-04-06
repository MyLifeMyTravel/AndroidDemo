package com.littlejie.android.demo.manager;

import android.content.Context;
import android.content.Intent;

/**
 * Created by Lion on 2016/4/6.
 */
public class ActivityManager {

    public static void startActivity(Context context, Class cls) {
        Intent intent = new Intent(context, cls);
        context.startActivity(intent);
    }
}
