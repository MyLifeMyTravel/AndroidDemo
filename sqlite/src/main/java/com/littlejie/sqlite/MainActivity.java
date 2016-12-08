package com.littlejie.sqlite;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private DBManager mManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mManager = new DBManager(this);

        findViewById(R.id.btn_open_database).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mManager.openDatabase();
            }
        });
        findViewById(R.id.btn_query_all).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mManager.queryAll();
            }
        });
    }
}
