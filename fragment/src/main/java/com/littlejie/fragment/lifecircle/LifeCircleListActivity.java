package com.littlejie.fragment.lifecircle;

import com.littlejie.base.BaseListActivity;
import com.littlejie.entity.ItemInfo;

public class LifeCircleListActivity extends BaseListActivity {

    @Override
    protected void processData() {
        super.processData();
        mLstItem.add(new ItemInfo("Fragment 生命周期", LifeCircleActivity.class));
        mLstItem.add(new ItemInfo("与 ViewPager 使用时的生命周期", LifeCircleWithViewPagerActivity.class));
        notifyDataChanged();
    }
}
