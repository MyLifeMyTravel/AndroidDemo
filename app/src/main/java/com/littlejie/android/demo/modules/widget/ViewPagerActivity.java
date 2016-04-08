package com.littlejie.android.demo.modules.widget;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.littlejie.android.demo.R;
import com.littlejie.android.demo.modules.base.BaseActivity;
import com.littlejie.android.demo.ui.widget.viewpager.LoopViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lion on 2016/4/8.
 */
public class ViewPagerActivity extends BaseActivity {

    private LoopViewPager mViewPager;
    private List<View> mLstView;

    @Override
    protected int getPageLayoutID() {
        return R.layout.activity_viewpager;
    }

    @Override
    protected void initView() {
        mViewPager = (LoopViewPager) findViewById(R.id.viewpager);
        LoopPagerAdapter adapter = new LoopPagerAdapter();
        mViewPager.setAdapter(adapter);
    }

    @Override
    protected void initViewListener() {

    }

    @Override
    protected void initData() {
        mLstView = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            TextView textView = new TextView(this);
            textView.setText("ViewPager " + i);
            mLstView.add(textView);
        }
    }

    class LoopPagerAdapter extends PagerAdapter {

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mLstView.get(position));
            return mLstView.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mLstView.get(position));
        }

        @Override
        public int getCount() {
            return mLstView.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}
