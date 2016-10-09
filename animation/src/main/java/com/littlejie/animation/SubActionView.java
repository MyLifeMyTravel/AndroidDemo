package com.littlejie.animation;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by littlejie on 2016/9/29.
 */

public class SubActionView extends LinearLayout {

    private ImageView mIvIcon;
    private TextView mTvTitle, mTvSubTitle;

    public SubActionView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_sub_action, this);
        mIvIcon = (ImageView) view.findViewById(R.id.icon);
        mTvTitle = (TextView) view.findViewById(R.id.title);
        mTvSubTitle = (TextView) view.findViewById(R.id.subTitle);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SubActionView);
        int resId = typedArray.getResourceId(R.styleable.SubActionView_icon, 0);
        if (resId != 0) {
            mIvIcon.setImageResource(resId);
        }
        resId = typedArray.getResourceId(R.styleable.SubActionView_title, 0);
        if (resId != 0) {
            mTvTitle.setText(resId);
        }
        resId = typedArray.getResourceId(R.styleable.SubActionView_subTitle, 0);
        if (resId != 0) {
            mTvSubTitle.setText(resId);
        }
    }
}
