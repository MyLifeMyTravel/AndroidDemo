package com.littlejie.android.viewstub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.littlejie.ui.image.BaseImageView;

public class MainActivity extends AppCompatActivity {

    private Button mTvShowStub;
    private BaseImageView mIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTvShowStub = (Button) findViewById(R.id.btn_show_viewstub);
        mTvShowStub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent("com.littlejie.android.scanner"), 0);
            }
        });
        mIv = (BaseImageView) findViewById(R.id.iv);
        mIv.setImage("http://att.bbs.duowan.com/forum/201210/20/210446opy9p5pghu015p9u.jpg");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        Toast.makeText(this, data.getStringExtra("result"), Toast.LENGTH_SHORT).show();
    }
}
