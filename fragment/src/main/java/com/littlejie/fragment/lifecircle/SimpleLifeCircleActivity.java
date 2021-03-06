package com.littlejie.fragment.lifecircle;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

import com.littlejie.base.BaseActivity;
import com.littlejie.fragment.R;

public class SimpleLifeCircleActivity extends BaseActivity {

    private TextView mTvActivityCreated;

    @Override
    protected int getPageLayoutID() {
        return R.layout.activity_life_circle;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        mTvActivityCreated = (TextView) findViewById(R.id.tv_activity_created);
    }

    @Override
    protected void initViewListener() {

    }

    @Override
    protected void processData() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, TAG + " onCreate");
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        Log.i(TAG, TAG + " onStart");
        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.i(TAG, TAG + " onResume");
        super.onResume();
    }

    @Override
    protected void onRestart() {
        Log.i(TAG, TAG + " onRestart");
        super.onRestart();
    }

    @Override
    protected void onPause() {
        Log.i(TAG, TAG + " onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.i(TAG, TAG + " onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.i(TAG, TAG + " onDestroy");
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_fragment1, menu);
        return true;
    }

    /**
     * 用于测试在 Fragment 的 onCreate() 方法中能否对 Activity 的 UI 进行操作
     *
     * @param text
     */
    public void setActivityCreated(String text) {
        mTvActivityCreated.setText(text);
    }
}
