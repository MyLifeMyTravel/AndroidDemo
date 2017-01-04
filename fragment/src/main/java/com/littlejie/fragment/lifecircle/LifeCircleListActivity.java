package com.littlejie.fragment.lifecircle;

import com.littlejie.base.BaseListActivity;
import com.littlejie.entity.ItemInfo;

public class LifeCircleListActivity extends BaseListActivity {

    @Override
    protected void processData() {
        super.processData();
        mLstItem.add(new ItemInfo("Fragment 生命周期", SimpleLifeCircleActivity.class));
        mLstItem.add(new ItemInfo("与 ViewPager 使用时的生命周期", LifeCircleWithViewPagerActivity.class));
        mLstItem.add(new ItemInfo("Fragment 添加进回退栈时的生命周期", LifeCircleWithBackStackActivity.class));
        mLstItem.add(new ItemInfo("Fragment hide()、show()时的生命周期", LifeCircleWithHideShowActivity.class));
        notifyDataChanged();
    }
}
