package com.littlejie.base;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.littlejie.R;
import com.littlejie.entity.ItemInfo;
import com.littlejie.utils.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * 简单 ListView Activity 封装
 * Created by littlejie on 2016/12/30.
 */

public class BaseListActivity extends BaseActivity {

    private ListView mLv;
    private ArrayAdapter<ItemInfo> mArrayAdapter;
    protected List<ItemInfo> mLstItem;

    @Override
    protected int getPageLayoutID() {
        return R.layout.activity_list;
    }

    @Override
    protected void initData() {
        mLstItem = new ArrayList<>();
    }

    @Override
    protected void initView() {
        mLv = (ListView) findViewById(R.id.lv);
        mArrayAdapter = new ArrayAdapter<ItemInfo>(this, android.R.layout.simple_list_item_1);
        mLv.setAdapter(mArrayAdapter);
    }

    @Override
    protected void initViewListener() {
        mLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mLstItem == null || mLstItem.size() == 0) {
                    return;
                }
                Util.startActivity(BaseListActivity.this, mLstItem.get(position).getClz());
            }
        });
    }

    @Override
    protected void processData() {

    }

    protected void notifyDataChanged() {
        mArrayAdapter.addAll(mLstItem);
        mArrayAdapter.notifyDataSetChanged();
    }

}
