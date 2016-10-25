package com.littlejie.listview;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.littlejie.listview.adapter.SimpleSelectAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 最近在项目里发现一个奇怪的现象：
 * ListView的Item中有ImageView，ImageView selected状态下显示不同图片
 * 如果调用复写item的setSelected()方法实现imageView的选中，则会无效；
 * 而如果直接调用imageView的setSelected()则正常
 */
public class ItemSelectStateActivity extends Activity {

    private ListView mLv;
    private SimpleSelectAdapter mAdapter;

    private List<String> mLstData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_select_state);
        initData();
        mLv = (ListView) findViewById(R.id.lv);
        mAdapter = new SimpleSelectAdapter(this);
        mAdapter.setData(mLstData);

        mLv.setAdapter(mAdapter);
        mLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mAdapter.setSelectItem(position);
            }
        });
    }

    private void initData() {
        mLstData = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            mLstData.add("item " + i);
        }
    }
}
