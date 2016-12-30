package com.littlejie.fragment.lifecircle;

import android.os.Bundle;
import android.util.Log;

import com.littlejie.base.BaseActivity;
import com.littlejie.fragment.R;

public class LifeCircleActivity extends BaseActivity {

    @Override
    protected int getPageLayoutID() {
        return R.layout.activity_life_circle;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {

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
}
