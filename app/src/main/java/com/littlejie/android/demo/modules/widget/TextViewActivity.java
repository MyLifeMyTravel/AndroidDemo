package com.littlejie.android.demo.modules.widget;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.littlejie.android.demo.R;
import com.littlejie.base.BaseActivity;

/**
 * Created by Lion on 2016/4/15.
 */
public class TextViewActivity extends BaseActivity {

    private TextView mTvLink;

    @Override
    protected int getPageLayoutID() {
        return R.layout.activity_textview;
    }

    @Override
    protected void initView() {
        mTvLink = (TextView) findViewById(R.id.tv_link);
    }

    @Override
    protected void initViewListener() {
        mTvLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TextViewActivity.this, "这是TextView的点击事件", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void initData() {

    }
}
