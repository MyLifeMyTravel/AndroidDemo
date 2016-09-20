package com.littlejie.adapter.array;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.littlejie.adapter.R;
import com.littlejie.demoutil.ItemInfo;
import com.littlejie.demoutil.Util;

import java.util.ArrayList;
import java.util.List;

public class ArrayAdapterActivity extends Activity {

    private ListView mLv;
    private List<ItemInfo> mItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_array_adapter);

        mLv = (ListView) findViewById(R.id.lv);
        mItems = generateItems();
        mLv.setAdapter(new ArrayAdapter<ItemInfo>(this,android.R.layout.simple_list_item_1,mItems));
        mLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Util.startActivity(ArrayAdapterActivity.this, mItems.get(position).getClz());
            }
        });
    }

    private List<ItemInfo> generateItems() {
        List<ItemInfo> items = new ArrayList<>();
        items.add(new ItemInfo("最简单的ArrayAdapter", SimplestArrayAdapterActivity.class));
        items.add(new ItemInfo("稍微复杂的ArrayAdapter", MiddleArrayAdapterActivity.class));
        items.add(new ItemInfo("最复杂的ArrayAdapter", HardestArrayAdapterActivity.class));
        items.add(new ItemInfo("ArrayAdapter的List操作", ListActionActivity.class));
        return items;
    }
}
