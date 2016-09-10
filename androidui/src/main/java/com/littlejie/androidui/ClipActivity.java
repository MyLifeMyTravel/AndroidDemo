package com.littlejie.androidui;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class ClipActivity extends Activity implements View.OnClickListener {

    private ImageView mIv1, mIv2, mIv3, mIv4, mIv5;
    private List<ImageView> mLstIv;
    private int mNormalHeight, mSpecailHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clip);

        mLstIv = new ArrayList<>();
        mIv1 = (ImageView) findViewById(R.id.iv1);
        mIv2 = (ImageView) findViewById(R.id.iv2);
        mIv3 = (ImageView) findViewById(R.id.iv3);
        mIv4 = (ImageView) findViewById(R.id.iv4);
        mIv5 = (ImageView) findViewById(R.id.iv5);
        mIv1.setOnClickListener(this);
        mIv2.setOnClickListener(this);
        mIv3.setOnClickListener(this);
        mIv4.setOnClickListener(this);
        mIv5.setOnClickListener(this);
        mLstIv.add(mIv1);
        mLstIv.add(mIv2);
        mLstIv.add(mIv3);
        mLstIv.add(mIv4);
        mLstIv.add(mIv5);
        mNormalHeight = getResources().getDimensionPixelSize(R.dimen.normal_height);
        mSpecailHeight = getResources().getDimensionPixelSize(R.dimen.special_height);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv1:
                clipChange(0);
                break;
            case R.id.iv2:
                clipChange(1);
                break;
            case R.id.iv3:
                clipChange(2);
                break;
            case R.id.iv4:
                clipChange(3);
                break;
            case R.id.iv5:
                clipChange(4);
                break;
        }
    }

    private void clipChange(int position) {
        int size = mLstIv.size();
        for (int i = 0; i < size; i++) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f);
            if (i == position) {
                params.height = mSpecailHeight;
                params.gravity = Gravity.BOTTOM;
            } else {
                params.height = mNormalHeight;
                params.gravity = Gravity.NO_GRAVITY;
            }
            mLstIv.get(i).setLayoutParams(params);
        }
    }
}
