package com.littlejie.view.widget;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.TextView;

import com.littlejie.view.R;

public class TextViewDemoActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_view_demo);
    }

    private void setTextDrawable(int resId) {
        TextView textView = new TextView(this);
        //获取Drawable对象
        Drawable drawable = getResources().getDrawable(resId);
        //指定Drawable的绘制区域
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        //设置TextView中Drawable出现的位置
        textView.setCompoundDrawables(drawable, drawable, drawable, drawable);
    }
}
