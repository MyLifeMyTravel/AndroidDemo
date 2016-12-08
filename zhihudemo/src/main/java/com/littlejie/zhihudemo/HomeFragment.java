package com.littlejie.zhihudemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by sli on 2016/12/8.
 */

public class HomeFragment extends Fragment implements ViewPager.OnPageChangeListener, TabLayout.OnTabSelectedListener {

    public static final String TAG = HomeFragment.class.getSimpleName();
    private static final String[] TITLES = {"热门推荐", "热门收藏", "本月热门", "今日热榜", "热门推荐", "热门收藏", "本月热门", "今日热榜"};

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private TabAdapter mAdapter;

    private List<Fragment> mLstTab;
    private List<String> mLstTitle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mTabLayout = (TabLayout) view.findViewById(R.id.tab);
        mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
        initTab();
        mAdapter = new TabAdapter(getFragmentManager(), mLstTab, mLstTitle);
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        return view;
    }

    private void initTab() {
        mLstTab = new ArrayList<>();
        mLstTitle = Arrays.asList(TITLES);
        for (int i = 0; i < TITLES.length; i++) {
            HomeTabFragment fragment = new HomeTabFragment();
            mLstTab.add(fragment);
            mTabLayout.addTab(mTabLayout.newTab().setText(TITLES[i]));
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewPager.setOnPageChangeListener(this);
        mTabLayout.addOnTabSelectedListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        Log.d(TAG, "onPageSelected = " + position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        Log.d(TAG, "onTabSelected = " + tab.getPosition());
        updateContent(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    private void updateContent(int position) {
        ((HomeTabFragment) mLstTab.get(position)).setContent(mLstTitle.get(position) + " " + position);
    }
}
