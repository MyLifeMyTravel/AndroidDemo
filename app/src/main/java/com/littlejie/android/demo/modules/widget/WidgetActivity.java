package com.littlejie.android.demo.modules.widget;

import android.view.View;
import android.widget.Button;

import com.littlejie.android.demo.R;
import com.littlejie.manager.ActivityManager;
import com.littlejie.base.BaseActivity;

/**
 * Created by Lion on 2016/4/8.
 */
public class WidgetActivity extends BaseActivity {

    private Button mBtnViewPager;

    @Override
    protected int getPageLayoutID() {
        return R.layout.activity_widget;
    }

    @Override
    protected void initView() {
        mBtnViewPager = (Button) findViewById(R.id.btn_viewpager);
    }

    @Override
    protected void initViewListener() {
        mBtnViewPager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityManager.startActivity(WidgetActivity.this, ViewPagerActivity.class);
            }
        });
    }

    @Override
    protected void initData() {

    }
}
