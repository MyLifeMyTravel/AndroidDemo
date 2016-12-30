package com.littlejie.mediascan;

import com.littlejie.base.BaseListActivity;
import com.littlejie.entity.ItemInfo;

public class MainActivity extends BaseListActivity {

    @Override
    protected void processData() {
        mLstItem.add(new ItemInfo("根据后缀或者MimeType过滤", ScanFileActivity.class));
        mLstItem.add(new ItemInfo("创建txt文件监听多媒体数据库变化", MediaObserverActivity.class));
        mLstItem.add(new ItemInfo("选择文件", SelectActivity.class));
        notifyDataChanged();
    }

}
