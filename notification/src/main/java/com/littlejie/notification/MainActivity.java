package com.littlejie.notification;

import com.littlejie.base.BaseListActivity;
import com.littlejie.entity.ItemInfo;

public class MainActivity extends BaseListActivity {

    @Override
    protected void processData() {
        super.processData();
        mLstItem.add(new ItemInfo("一个简单的Demo", SimplestNotificationActivity.class));
        mLstItem.add(new ItemInfo("Notification 简单 Demo", SimpleNotificationActivity.class));
        mLstItem.add(new ItemInfo("Notification 提示形式", NotificationEffectActivity.class));
        mLstItem.add(new ItemInfo("Notification 样式", NotificationStyleActivity.class));
        mLstItem.add(new ItemInfo("TaskStackBuilder 简单测试", TaskStackBuilderActivity.class));
        mLstItem.add(new ItemInfo("启动 NotificationListenerService", NotificationListenerServiceActivity.class));
        notifyDataChanged();
    }

}
