package com.littlejie.zhihudemo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by sli on 2016/12/8.
 */

public class TabAdapter extends FragmentPagerAdapter {

    private List<Fragment> mLstTab;
    private List<String> mLstTitle;

    public TabAdapter(FragmentManager fm, List<Fragment> lstTab, List<String> lstTitle) {
        super(fm);
        mLstTab = lstTab;
        mLstTitle = lstTitle;
    }

    @Override
    public Fragment getItem(int position) {
        return mLstTab == null ? null : mLstTab.get(position);
    }

    @Override
    public int getCount() {
        return mLstTab == null ? 0 : mLstTab.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mLstTitle.get(position);
    }
}
