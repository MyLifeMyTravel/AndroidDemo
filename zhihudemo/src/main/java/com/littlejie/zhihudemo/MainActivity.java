package com.littlejie.zhihudemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.tool_bar);

        setSupportActionBar(mToolbar);

        getSupportFragmentManager().beginTransaction().add(R.id.content_frame, new HomeFragment()).commit();
    }
}
