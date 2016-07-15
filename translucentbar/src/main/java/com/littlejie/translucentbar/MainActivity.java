package com.littlejie.translucentbar;

import android.app.Activity;
import android.os.Bundle;

import com.littlejie.utils.TranslucentBarUtil;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TranslucentBarUtil.setTranslucentBar(this, "#883F51B5");
        setContentView(R.layout.activity_main);

    }
}
