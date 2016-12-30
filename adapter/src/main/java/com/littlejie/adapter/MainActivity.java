package com.littlejie.adapter;

import com.littlejie.adapter.array.ArrayAdapterActivity;
import com.littlejie.adapter.cursor.CursorAdapterActivity;
import com.littlejie.base.BaseListActivity;
import com.littlejie.entity.ItemInfo;

public class MainActivity extends BaseListActivity {

    @Override
    protected void processData() {
        super.processData();
        mLstItem.add(new ItemInfo("ArrayAdapter Demo", ArrayAdapterActivity.class));
        mLstItem.add(new ItemInfo("CursorAdapter Demo", CursorAdapterActivity.class));
        notifyDataChanged();
    }

}
