package com.littlejie.android.demo.modules.main;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.widget.Toast;

import com.littlejie.android.demo.R;
import com.littlejie.android.demo.modules.widget.GridImageActivity;
import com.littlejie.android.demo.modules.widget.RecyclerViewActivity;
import com.littlejie.android.demo.modules.widget.TextViewActivity;
import com.littlejie.android.demo.modules.widget.ViewPagerActivity;
import com.littlejie.android.demo.modules.widget.WidgetActivity;
import com.littlejie.base.BaseActivity;
import com.littlejie.manager.ActivityManager;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends BaseActivity {

    public static final String TAG = "MainActivity";
    private NavigationView mNavigationView;
    private DrawerLayout mDrawerLayout;
    private static Map<Integer, Class[]> mNavMap = new HashMap<>();

    static {
        Class[] otherActivities = {};
        Class[] widgetActivities = {ViewPagerActivity.class, RecyclerViewActivity.class,
                TextViewActivity.class, GridImageActivity.class};

        mNavMap.put(R.id.navigation_other, otherActivities);
        mNavMap.put(R.id.navigation_widget, widgetActivities);
    }

    @Override
    protected int getPageLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        mNavigationView = (NavigationView) findViewById(R.id.navigation);
        //禁止系统对item的图标进行着色
        mNavigationView.setItemIconTintList(null);
    }

    @Override
    protected void initViewListener() {
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                if (R.id.navigation_widget == item.getItemId()) {
                    Intent intent = new Intent();
                    intent.putExtra("test", mNavMap.get(item.getItemId()));
                    ActivityManager.startActivity(MainActivity.this, WidgetActivity.class);
                }
                if (mNavMap.containsKey(item.getItemId())) {
                    item.setChecked(true);
                    mDrawerLayout.closeDrawers();
                }
                return false;
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void processWithPermission() {
        super.processWithPermission();
        Toast.makeText(this, "我拥有权限", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
