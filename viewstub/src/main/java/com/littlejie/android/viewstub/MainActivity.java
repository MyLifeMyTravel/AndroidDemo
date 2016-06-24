package com.littlejie.android.viewstub;

import android.os.Bundle;
import android.util.Log;

import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringConfig;
import com.facebook.rebound.SpringListener;
import com.facebook.rebound.SpringSystem;
import com.littlejie.base.BaseActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected int getPageLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        SpringSystem springSystem = SpringSystem.create();
        Spring spring = springSystem.createSpring();
        spring.setSpringConfig(SpringConfig.fromOrigamiTensionAndFriction(170, 5));
        spring.addListener(new MySpringListener());
        spring.setCurrentValue(0.0);
        spring.setEndValue(1);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initViewListener() {

    }

    private class MySpringListener implements SpringListener {

        @Override
        public void onSpringUpdate(Spring spring) {
            Log.d(TAG, "onSpringUpdate");
        }

        @Override
        public void onSpringAtRest(Spring spring) {

        }

        @Override
        public void onSpringActivate(Spring spring) {

        }

        @Override
        public void onSpringEndStateChange(Spring spring) {

        }
    }

}
