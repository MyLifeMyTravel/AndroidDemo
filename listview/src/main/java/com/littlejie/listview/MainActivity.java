package com.littlejie.listview;

import com.littlejie.base.BaseListActivity;
import com.littlejie.entity.ItemInfo;

public class MainActivity extends BaseListActivity {

    @Override
    protected void processData() {
        super.processData();
        mLstItem.add(new ItemInfo("简单的emptyView", EmptyListViewActivity.class));
        mLstItem.add(new ItemInfo("复杂的emptyView", ComplexEmptyListViewActivity.class));
        mLstItem.add(new ItemInfo("关于ListView Item中ImageView选中状态的疑问", ItemSelectStateActivity.class));
        notifyDataChanged();
    }

}
