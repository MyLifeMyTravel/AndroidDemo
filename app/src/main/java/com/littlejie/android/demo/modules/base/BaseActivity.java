package com.littlejie.android.demo.modules.base;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

/**
 * Created by Lion on 2016/4/6.
 */
public abstract class BaseActivity extends Activity {

    public static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getPageLayoutID());
        initData();
        initView();
        initViewListener();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 设置页面布局ID
     *
     * @return
     */
    protected abstract int getPageLayoutID();

    /**
     * 初始化页面控件
     */
    protected abstract void initView();

    /**
     * 初始化控件监听器
     */
    protected abstract void initViewListener();

    /**
     * 初始化数据
     */
    protected abstract void initData();

    protected void requestPermission(String permission) {
        // Verify that all required contact permissions have been granted.
        if (checkPermission(permission)) {
            //permissions have not been granted.
            Log.i(TAG, permission + " has NOT been granted. Requesting permissions.");
            // BEGIN_INCLUDE(permission_request)
            requestPermission(permission, 10);
            // END_INCLUDE(permission_request)

        } else {
            // Contact permissions have been granted. Show the contacts fragment.
            Log.i(TAG,
                    "Contact permissions have already been granted. Displaying contact details.");
            processWithPermission();
        }
    }

    private boolean checkPermission(String permission) {
        return ActivityCompat.checkSelfPermission(this, permission)
                != PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission(String permission, int requestCode) {
        requestPermission(new String[]{permission}, requestCode);
    }

    private void requestPermission(String[] permissions, int requestCode) {
        ActivityCompat.requestPermissions(this, permissions, requestCode);
    }

    /**
     * 拥有权限时处理
     */
    protected void processWithPermission() {

    }
}
