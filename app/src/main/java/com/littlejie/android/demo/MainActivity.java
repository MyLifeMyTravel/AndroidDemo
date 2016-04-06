package com.littlejie.android.demo;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.view.MenuItem;

import com.littlejie.android.demo.manager.ActivityManager;
import com.littlejie.android.demo.modules.base.BaseActivity;
import com.littlejie.android.demo.modules.mixed.MixedItemActivity;

public class MainActivity extends BaseActivity {

    private NavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationView = (NavigationView) findViewById(R.id.navigation);
        //禁止系统对item的图标进行着色
        mNavigationView.setItemIconTintList(null);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                if (item.getItemId() == R.id.navigation_mixed) {
                    ActivityManager.startActivity(MainActivity.this, MixedItemActivity.class);
                    return true;
                }
                return false;
            }
        });
    }
}
