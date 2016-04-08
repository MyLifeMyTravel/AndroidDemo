package com.littlejie.android.demo.modules.base;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by Lion on 2016/4/6.
 */
public abstract class BaseActivity extends Activity {

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
}
