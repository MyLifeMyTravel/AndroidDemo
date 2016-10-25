package com.littlejie.listview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.littlejie.demoutil.ItemInfo;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private ListView mLv;
    private List<ItemInfo> mItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLv = (ListView) findViewById(R.id.lv);
        mItems = generateItem();
        mLv.setAdapter(new ArrayAdapter<ItemInfo>(this, android.R.layout.simple_list_item_1, mItems));
        mLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(MainActivity.this, mItems.get(position).getClz()));
            }
        });

    }

    private List<ItemInfo> generateItem() {
        List<ItemInfo> infos = new ArrayList<>();
        infos.add(new ItemInfo("简单的emptyView", EmptyListViewActivity.class));
        infos.add(new ItemInfo("复杂的emptyView", ComplexEmptyListViewActivity.class));
        infos.add(new ItemInfo("关于ListView Item中ImageView选中状态的疑问", ItemSelectStateActivity.class));
        return infos;
    }

}
