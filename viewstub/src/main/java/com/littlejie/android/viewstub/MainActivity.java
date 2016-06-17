package com.littlejie.android.viewstub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.littlejie.scanner.ScanActivity;

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
                startActivityForResult(new Intent(MainActivity.this, ScanActivity.class), 0);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Toast.makeText(this, data.getStringExtra("result"), Toast.LENGTH_SHORT).show();
    }
}
