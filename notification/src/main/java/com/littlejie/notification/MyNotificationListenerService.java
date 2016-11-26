package com.littlejie.notification;

import android.content.Intent;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

/**
 * Created by sli on 2016/11/24.
 */

public class MyNotificationListenerService extends NotificationListenerService {

    private static final String TAG = MyNotificationListenerService.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onListenerConnected() {
        super.onListenerConnected();
        //此方法应该是当系统启动Notification服务的时候调用
        //故一般来说，调用不到
    }

    @Override
    public void onListenerDisconnected() {
        super.onListenerDisconnected();
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        super.onNotificationPosted(sbn);
        Log.d(TAG, "onNotificationPosted=" + sbn.toString());
        Log.d(TAG, "all notification = " + getActiveNotifications().toString());
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        super.onNotificationRemoved(sbn);
        Log.d(TAG, "onNotificationRemoved=" + sbn.toString());
    }
}