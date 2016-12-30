package com.littlejie.fragment;

import com.littlejie.base.BaseListActivity;
import com.littlejie.entity.ItemInfo;
import com.littlejie.fragment.lifecircle.LifeCircleListActivity;
import com.littlejie.fragment.subclass.SubClassListActivity;

public class MainActivity extends BaseListActivity {

    @Override
    protected void processData() {
        super.processData();
        mLstItem.add(new ItemInfo("Fragment 直接子类", SubClassListActivity.class));
        mLstItem.add(new ItemInfo("Fragment 生命周期", LifeCircleListActivity.class));
        notifyDataChanged();
    }
}
