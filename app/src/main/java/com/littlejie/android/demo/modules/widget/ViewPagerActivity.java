package com.littlejie.android.demo.modules.widget;

import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.littlejie.android.demo.R;
import com.littlejie.android.demo.modules.base.BaseActivity;
import com.littlejie.ui.viewpager.InfinitePagerAdapter;
import com.littlejie.ui.viewpager.InfiniteViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lion on 2016/4/8.
 */
public class ViewPagerActivity extends BaseActivity {

    public static final String TAG = "ViewPagerActivity";
    private int[] color = new int[]{Color.BLUE, Color.GREEN, Color.RED, Color.CYAN, Color.GRAY};
    private InfiniteViewPager mViewPager;
    private List<View> mLstView;

    @Override
    protected int getPageLayoutID() {
        return R.layout.activity_viewpager;
    }

    @Override
    protected void initView() {
        mViewPager = (InfiniteViewPager) findViewById(R.id.viewpager);
        LoopPagerAdapter adapter = new LoopPagerAdapter();
        mViewPager.setAdapter(new InfinitePagerAdapter(adapter));
    }

    @Override
    protected void initViewListener() {
    }

    @Override
    protected void initData() {
        mLstView = new ArrayList<>();
        for (int i = 0; i < color.length; i++) {
            TextView textView = new TextView(this);
            textView.setText("ViewPager " + i);
            textView.setGravity(Gravity.CENTER);
            textView.setBackgroundColor(color[i]);
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
