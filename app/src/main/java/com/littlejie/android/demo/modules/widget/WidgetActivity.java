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
    private Button mBtnTextView;

    @Override
    protected int getPageLayoutID() {
        return R.layout.activity_widget;
    }

    @Override
    protected void initView() {
        mBtnViewPager = (Button) findViewById(R.id.btn_viewpager);
        mBtnTextView = (Button) findViewById(R.id.btn_textview);
    }

    @Override
    protected void initViewListener() {
        mBtnViewPager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityManager.startActivity(WidgetActivity.this, GridImageActivity.class);
            }
        });
        mBtnTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityManager.startActivity(WidgetActivity.this, TextViewActivity.class);
            }
        });
    }

    @Override
    protected void processData() {

    }

    @Override
    protected void initData() {

    }
}
