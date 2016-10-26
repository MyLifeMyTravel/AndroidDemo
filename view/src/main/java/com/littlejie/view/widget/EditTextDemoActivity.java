package com.littlejie.view.widget;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.littlejie.demoutil.ItemInfo;
import com.littlejie.demoutil.Util;
import com.littlejie.view.R;
import com.littlejie.view.widget.edit.ImeOptionsActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * EditText 相关属性使用Demo
 */
public class EditTextDemoActivity extends Activity {

    private ListView mLv;
    private List<ItemInfo> mItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLv = (ListView) findViewById(R.id.lv);
        mItems = generateItems();
        mLv.setAdapter(new ArrayAdapter<ItemInfo>(this, android.R.layout.simple_list_item_1, generateItems()));
        mLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Util.startActivity(EditTextDemoActivity.this, mItems.get(position).getClz());
            }
        });
    }

    private List<ItemInfo> generateItems() {
        List<ItemInfo> items = new ArrayList<>();
        items.add(new ItemInfo("EditText ImeOptions Demo", ImeOptionsActivity.class));
        return items;
    }
}
