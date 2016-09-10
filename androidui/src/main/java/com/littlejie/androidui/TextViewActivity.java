package com.littlejie.androidui;

import android.app.Activity;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.TextView;

public class TextViewActivity extends Activity {

    private TextView mTvXML, mTvCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_view);

        mTvXML = (TextView) findViewById(R.id.tv_xml);
        mTvCode = (TextView) findViewById(R.id.tv_code);
        mTvCode.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
    }
}
