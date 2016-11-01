package com.littlejie.shortcuts;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import java.net.URL;
import java.util.Arrays;

public class MainActivity extends Activity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private ShortcutManager mShortcutManager;
    private ShortcutInfo[] mShortcutInfos;
    private int mAddCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //这里常量Context.SHORTCUT_SERVICE会报错，不用管，可正常编译。看着烦的话把minSdkVersion改为25即可
        mShortcutManager = (ShortcutManager) getSystemService(Context.SHORTCUT_SERVICE);

        mShortcutInfos = new ShortcutInfo[]{getShoppingShortcut(), getDateShortcut()};
        findViewById(R.id.btn_set).setOnClickListener(this);
        findViewById(R.id.btn_add).setOnClickListener(this);
        findViewById(R.id.btn_update).setOnClickListener(this);
        findViewById(R.id.btn_disabled).setOnClickListener(this);
        findViewById(R.id.btn_remove).setOnClickListener(this);
        findViewById(R.id.btn_removeAll).setOnClickListener(this);
        findViewById(R.id.btn_print_max_shortcut_per_activity).setOnClickListener(this);
        findViewById(R.id.btn_print_dynamic_shortcut).setOnClickListener(this);
        findViewById(R.id.btn_print_static_shortcut).setOnClickListener(this);
    }

    private ShortcutInfo getAlarmShortcut(String shortLabel) {
        if (TextUtils.isEmpty(shortLabel)) {
            shortLabel = "Python";
        }
        ShortcutInfo alarm = new ShortcutInfo.Builder(this, "alarm" + mAddCount++)
                .setIntent(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.baidu.org/")))
                .setShortLabel(shortLabel)
                .setLongLabel("不正经的闹钟")
                .setIcon(Icon.createWithResource(this, R.mipmap.ic_alarm))
                .build();
        return alarm;
    }

    private ShortcutInfo getShoppingShortcut() {
        ShortcutInfo shopping = new ShortcutInfo.Builder(this, "shopping")
                .setIntent(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.taobao.com")))
                .setShortLabel("双十一剁手")
                .setLongLabel("一本正经的购物")
                .setIcon(Icon.createWithResource(this, R.mipmap.ic_shopping))
                .build();
        return shopping;
    }

    private ShortcutInfo getDateShortcut() {
        ShortcutInfo date = new ShortcutInfo.Builder(this, "date")
                .setIntent(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com")))
                .setShortLabel("被强了")
                .setLongLabel("日程")
                .setIcon(Icon.createWithResource(this, R.mipmap.ic_today))
                .build();
        return date;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_set:
                setDynamicShortcuts();
                break;
            case R.id.btn_add:
                addDynamicShortcuts();
                break;
            case R.id.btn_update:
                updateDynamicShortcuts();
                break;
            case R.id.btn_disabled:
                disableDynamicShortcuts();
                break;
            case R.id.btn_remove:
                removeDynamicShortcuts();
                break;
            case R.id.btn_removeAll:
                removeAllDynamicShortcuts();
                break;
            case R.id.btn_print_max_shortcut_per_activity:
                printMaxShortcutsPerActivity();
                break;
            case R.id.btn_print_dynamic_shortcut:
                printDynamicShortcuts();
                break;
            case R.id.btn_print_static_shortcut:
                printStaticShortcuts();
                break;
        }
    }

    /**
     * 动态设置快捷方式
     */
    private void setDynamicShortcuts() {
        mShortcutManager.setDynamicShortcuts(Arrays.asList(mShortcutInfos));
    }

    /**
     * 添加快捷方式
     */
    private void addDynamicShortcuts() {
        mShortcutManager.addDynamicShortcuts(Arrays.asList(getAlarmShortcut(null)));
    }

    /**
     * 更新快捷方式，类似于Notification，根据id进行更新
     */
    private void updateDynamicShortcuts() {
        mShortcutManager.updateShortcuts(Arrays.asList(getAlarmShortcut("Java")));
    }

    /**
     * 禁用动态快捷方式
     */
    private void disableDynamicShortcuts() {
        mShortcutManager.disableShortcuts(Arrays.asList("alarm0"));
        //因为shortcutId=compose2的快捷方式为静态，所以不能实现动态修改
        //mShortcutManager.disableShortcuts(Arrays.asList("compose2"));
    }

    /**
     * 移除快捷方式
     * <p>
     * 已被用户拖拽到桌面的快捷方式还是回继续存在，如果不在支持的话，最好将其置为disable
     */
    private void removeDynamicShortcuts() {
        mShortcutManager.removeDynamicShortcuts(Arrays.asList("alarm0"));
    }

    /**
     * 移除所有动态快捷方式
     */
    private void removeAllDynamicShortcuts() {
        mShortcutManager.removeAllDynamicShortcuts();
    }

    /**
     * 只要 intent-filter 的 action = android.intent.action.MAIN
     * 和 category = android.intent.category.LAUNCHER 的 activity 都可以设置 Shortcuts
     */
    private void printMaxShortcutsPerActivity() {
        Log.d(TAG, "每个 Launcher Activity 能显示的最多快捷方式个数：" + mShortcutManager.getMaxShortcutCountPerActivity());
    }

    /**
     * 打印动态快捷方式信息
     */
    private void printDynamicShortcuts() {
        Log.d(TAG, "动态快捷方式列表数量：" + mShortcutManager.getDynamicShortcuts().size() + "信息：" + mShortcutManager.getDynamicShortcuts());
    }

    /**
     * 打印静态快捷方式信息
     */
    private void printStaticShortcuts() {
        Log.d(TAG, "静态快捷方式列表数量：" + mShortcutManager.getManifestShortcuts().size() + "信息：" + mShortcutManager.getManifestShortcuts());
    }

}
