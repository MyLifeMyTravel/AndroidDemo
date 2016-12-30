package com.littlejie.drawerlayout;

import com.littlejie.base.BaseListActivity;
import com.littlejie.entity.ItemInfo;

public class MainActivity extends BaseListActivity {

    @Override
    protected void processData() {
        super.processData();
        mLstItem.add(new ItemInfo("DrawerLayout Demo", DrawerLayoutActivity.class));
        mLstItem.add(new ItemInfo("DrawerLayout 搭配 NavigationView Demo", NavigationActivity.class));
        notifyDataChanged();
    }

}
