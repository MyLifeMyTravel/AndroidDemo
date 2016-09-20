package com.littlejie.adapter.array;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.littlejie.adapter.R;
import com.littlejie.demoutil.Util;

public class MiddleArrayAdapterActivity extends Activity {

    private ListView mLv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_middle_array_adapter);

        mLv = (ListView) findViewById(R.id.lv);
        mLv.setAdapter(new ArrayAdapter<String>(this,R.layout.custom_list_item,R.id.tv_i_am_textview, Util.generateString(10)));
    }
}
