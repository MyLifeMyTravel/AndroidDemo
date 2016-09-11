package com.littlejie.adapter;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.littlejie.demoutil.Util;

public class SimplestArrayAdapterActivity extends Activity {

    private ListView mLv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simplest_array_adapter);

        mLv = (ListView) findViewById(R.id.lv);
        mLv.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, Util.generateString(10)));
        //此处为自定义的simple_list_item
        //mLv.setAdapter(new ArrayAdapter<String>(this, R.layout.custom_simplest_list_item, Util.generateString(10)));
    }
}
