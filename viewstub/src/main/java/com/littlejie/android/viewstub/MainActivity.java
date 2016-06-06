package com.littlejie.android.viewstub;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewStub;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mTvShowStub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTvShowStub = (TextView) findViewById(R.id.btn_show_viewstub);
        mTvShowStub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.viewstub).setVisibility(View.VISIBLE);
//                ((ViewStub) findViewById(R.id.viewstub)).inflate();
            }
        });
    }
}
