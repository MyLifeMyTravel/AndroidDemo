package com.littlejie.android.demo.ui.widget.viewpager;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Lion on 2016/4/8.
 */
public class LoopViewPager extends ViewPager implements ViewPager.OnPageChangeListener {

    public static final String TAG = "LoopViewPager";

    private LoopPagerAdapterWrapper mWrapper = null;

    public LoopViewPager(Context context) {
        super(context);
    }

    public LoopViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setAdapter(PagerAdapter adapter) {
        mWrapper = new LoopPagerAdapterWrapper(adapter);
        super.setAdapter(mWrapper);
        setCurrentItem(1);
    }

    @Override
    public int getCurrentItem() {
        return mWrapper == null ? 0 : mWrapper.covert2RealPosition(super.getCurrentItem());
    }

    @Override
    public void onPageSelected(int position) {
        int realPosition = mWrapper.covert2RealPosition(position);
        setCurrentItem(realPosition, true);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        switch (state) {
            case ViewPager.SCROLL_STATE_IDLE:
                if (getCurrentItem() == mWrapper.getCount() - 1) {
                    setCurrentItem(0);
                } else if (getCurrentItem() == 0) {
                    setCurrentItem(mWrapper.getCount() - 1);
                }
                break;
            case ViewPager.SCROLL_STATE_DRAGGING:
//                isAutoPlay = false;
                break;
            case ViewPager.SCROLL_STATE_SETTLING:
//                isAutoPlay = true;
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float offset, int offsetPixels) {
        super.onPageScrolled(position, offset, offsetPixels);
    }

    public class LoopPagerAdapterWrapper extends PagerAdapter {

        private PagerAdapter mAdapter;

        public LoopPagerAdapterWrapper(PagerAdapter mAdapter) {
            this.mAdapter = mAdapter;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            mAdapter.destroyItem(container, covert2RealPosition(position), object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            int realPosition = covert2RealPosition(position);
            Log.d(TAG, "inner position=" + position + ";real position=" + realPosition);
            return mAdapter == null
                    ? super.instantiateItem(container, position)
                    : mAdapter.instantiateItem(container, covert2RealPosition(position));
        }

        @Override
        public int getCount() {
            return mAdapter == null ? 0 : mAdapter.getCount() + 2;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void startUpdate(ViewGroup container) {
            mAdapter.startUpdate(container);
        }

        @Override
        public void finishUpdate(ViewGroup container) {
            mAdapter.finishUpdate(container);
        }

        /**
         * inner----real
         * 0        4
         * 1        0
         * 2        1
         * 3        2
         * 4        3
         * 5        4
         * 6        0
         *
         * @param innerPosition
         * @return
         */
        public int covert2RealPosition(int innerPosition) {
            int realCount = getRealCount();
            if (realCount == 0) {
                return 0;
            }
            if (innerPosition == 0) {
                return realCount - 1;
            } else {
                return (innerPosition - 1) % realCount;
            }

        }

        /**
         * 不考虑多出来的首尾
         * real----inner
         * 0        1
         * 1        2
         * 2        3
         * 3        4
         * 4        5
         *
         * @param realPosition
         * @return
         */
        public int covert2InnerPosition(int realPosition) {
            return realPosition + 1;
        }

        public int getRealFirstPostion() {
            return 1;
        }

        public int getRealLastPosition() {
            return mAdapter.getCount();
        }

        public int getRealCount() {
            return mAdapter == null ? 0 : mAdapter.getCount();
        }

        public PagerAdapter getRealAdapter() {
            return mAdapter;
        }

    }

}
