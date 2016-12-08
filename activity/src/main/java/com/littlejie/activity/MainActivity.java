package com.littlejie.activity;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends Activity {

    public static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Configuration configuration = getResources().getConfiguration();
        Log.d(TAG, "OS Version = " + Build.VERSION.RELEASE);
        Log.d(TAG, "Country = " + configuration.locale.getCountry());
        Log.d(TAG, "Language = " + configuration.locale.getLanguage());
        Log.d(TAG, "Device Brand = " + Build.BRAND);

        Log.d(TAG,getSysInfo());
    }

    public static String getSysInfo() {
        StringBuffer sysInfo = new StringBuffer();
        sysInfo.append("ID " + Build.ID + ";");
        sysInfo.append("DISPLAY " + Build.DISPLAY + ";");
        sysInfo.append("PRODUCT " + Build.PRODUCT + ";");
        sysInfo.append("DEVICE " + Build.DEVICE + ";");
        sysInfo.append("BOARD " + Build.BOARD + ";");
        sysInfo.append("MODEL " + Build.MODEL + ";");
        sysInfo.append("BRAND " + Build.BRAND + ";");
        sysInfo.append("MANUFACTURER " + Build.MANUFACTURER + ";");

        sysInfo.append("HARDWARE " + Build.HARDWARE + ";");
        sysInfo.append("SERIAL " + Build.SERIAL + ";");

        sysInfo.append("VERSION.INCREMENTAL " + Build.VERSION.INCREMENTAL + ";");
        sysInfo.append("VERSION.RELEASE " + Build.VERSION.RELEASE + ";");
        sysInfo.append("VERSION.SDK " + Build.VERSION.SDK_INT + ";");
        sysInfo.append("VERSION.CODENAME " + Build.VERSION.CODENAME + ";");

        sysInfo.append("TYPE " + Build.TYPE + ";");
        sysInfo.append("TAGS " + Build.TAGS + ";");
        sysInfo.append("FINGERPRINT " + Build.FINGERPRINT + ";");

        //用于内部开发
        sysInfo.append("USER " + Build.USER + ";");
        sysInfo.append("HOST " + Build.HOST);
        return sysInfo.toString();
    }
}
