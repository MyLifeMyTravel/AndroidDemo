package com.littlejie.view;

import com.littlejie.base.BaseListActivity;
import com.littlejie.entity.ItemInfo;
import com.littlejie.view.widget.EditTextDemoActivity;
import com.littlejie.view.widget.TextViewDemoActivity;

public class MainActivity extends BaseListActivity {

    @Override
    protected void processData() {
        super.processData();
        mLstItem.add(new ItemInfo("TextView Demo", TextViewDemoActivity.class));
        mLstItem.add(new ItemInfo("EditText Demo", EditTextDemoActivity.class));
        notifyDataChanged();
    }

}