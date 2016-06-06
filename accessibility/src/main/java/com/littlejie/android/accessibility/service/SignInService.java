package com.littlejie.android.accessibility.service;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Intent;
import android.view.accessibility.AccessibilityEvent;

/**
 * Created by Lion on 2016/6/4.
 */
public class SignInService extends AccessibilityService {

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {

    }

    @Override
    public void onInterrupt() {

    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }
}
