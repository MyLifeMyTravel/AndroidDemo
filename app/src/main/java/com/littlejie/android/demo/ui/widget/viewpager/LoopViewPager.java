package com.littlejie.android.demo.ui.widget.viewpager;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Lion on 2016/4/8.
 */
public class LoopViewPager extends ViewPager {

    public static final String TAG = "LoopViewPager";

    private boolean mBoundaryCaching = true;
    private Map<Integer, ToDestroy> mToDestroy = new HashMap<>();
    private LoopPagerAdapterWrapper mWrapper = null;
    private OnPageChangeListener mOnLoopPageChangeListener;

    public LoopViewPager(Context context) {
        super(context);
        super.setOnPageChangeListener(mOnPageChangeListener);
    }

    public LoopViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        super.setOnPageChangeListener(mOnPageChangeListener);
    }

    public void setBoundaryCaching(boolean flag) {
        mBoundaryCaching = flag;
    }

    @Override
    public void setAdapter(PagerAdapter adapter) {
        mWrapper = new LoopPagerAdapterWrapper(adapter);
        super.setAdapter(mWrapper);
        setCurrentItem(0, false);
    }

    @Override
    public int getCurrentItem() {
        return mWrapper == null ? 0 : mWrapper.covert2RealPosition(super.getCurrentItem());
    }

    public void setCurrentItem(int item, boolean smoothScroll) {
        int realItem = mWrapper.covert2InnerPosition(item);
        super.setCurrentItem(realItem, smoothScroll);
    }

    @Override
    public void setCurrentItem(int item) {
        if (getCurrentItem() != item) {
            setCurrentItem(item, true);
        }
    }

    public void setOnLoopPageChangeListener(OnPageChangeListener onPageChangeListener) {
        this.mOnLoopPageChangeListener = onPageChangeListener;
    }

    private OnPageChangeListener mOnPageChangeListener = new OnPageChangeListener() {
        private float mPreviousOffset = -1;
        private float mPreviousPosition = -1;

        @Override
        public void onPageSelected(int position) {
            int realPosition = mWrapper.covert2RealPosition(position);
            if (mPreviousPosition != realPosition) {
                mPreviousPosition = realPosition;
                if (mOnLoopPageChangeListener != null) {
                    mOnLoopPageChangeListener.onPageSelected(realPosition);
                }
            }
        }

        @Override
        public void onPageScrolled(int position, float positionOffset,
                                   int positionOffsetPixels) {
            int realPosition = position;
            if (mWrapper != null) {
                realPosition = mWrapper.covert2RealPosition(position);

                if (positionOffset == 0
                        && mPreviousOffset == 0
                        && (position == 0 || position == mWrapper.getCount() - 1)) {
                    setCurrentItem(realPosition, false);
                }
            }

            mPreviousOffset = positionOffset;
            if (mOnLoopPageChangeListener != null) {
                if (realPosition != mWrapper.getRealCount() - 1) {
                    mOnLoopPageChangeListener.onPageScrolled(realPosition,
                            positionOffset, positionOffsetPixels);
                } else {
                    if (positionOffset > .5) {
                        mOnLoopPageChangeListener.onPageScrolled(0, 0, 0);
                    } else {
                        mOnLoopPageChangeListener.onPageScrolled(realPosition,
                                0, 0);
                    }
                }
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if (mWrapper != null) {
                int position = LoopViewPager.super.getCurrentItem();
                int realPosition = mWrapper.covert2RealPosition(position);
                if (state == ViewPager.SCROLL_STATE_IDLE
                        && (position == 0 || position == mWrapper.getCount() - 1)) {
                    setCurrentItem(realPosition, false);
                }
            }
            if (mOnLoopPageChangeListener != null) {
                mOnLoopPageChangeListener.onPageScrollStateChanged(state);
            }
        }
    };

    public class LoopPagerAdapterWrapper extends PagerAdapter {

        private PagerAdapter mAdapter;

        public LoopPagerAdapterWrapper(PagerAdapter mAdapter) {
            this.mAdapter = mAdapter;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            int realPosition = covert2RealPosition(position);
            Log.d(TAG, "destroyItem:inner position=" + position + ";real position=" + realPosition);
            mAdapter.destroyItem(container, realPosition, object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            int realFirst = getRealFirstPostion();
            int realLast = getRealLastPosition();
            int realPosition = covert2RealPosition(position);
            Log.d(TAG, "instantiateItem:inner position=" + position + ";real position=" + realPosition);
            if (mBoundaryCaching
                    && (position == 0 || position == 1
                    || position == getRealCount() || position == getRealCount() + 1)) {
                ToDestroy toDestroy = mToDestroy.get(position);
                if (toDestroy != null) {
                    return toDestroy.object;
                } else {
                    Object object = mAdapter == null
                            ? super.instantiateItem(container, realPosition)
                            : mAdapter.instantiateItem(container, realPosition);
                    mToDestroy.put(position, new ToDestroy(container, realPosition, object));
                    return object;
                }
            }
            return mAdapter == null
                    ? super.instantiateItem(container, realPosition)
                    : mAdapter.instantiateItem(container, realPosition);
        }

        @Override
        public int getCount() {
            return mAdapter == null ? 0 : mAdapter.getCount() + 2;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return mAdapter.isViewFromObject(view, object);
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
            if (realCount == 0)
                return 0;
            int realPosition = (innerPosition - 1) % realCount;
            if (realPosition < 0)
                realPosition += realCount;

            return realPosition;
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

        private int getRealFirstPostion() {
            return 0;
        }

        private int getRealLastPosition() {
            return mAdapter.getCount() + 1;
        }

        public int getRealCount() {
            return mAdapter == null ? 0 : mAdapter.getCount();
        }

        public PagerAdapter getRealAdapter() {
            return mAdapter;
        }

    }

    /**
     * Container class for caching the boundary views
     */
    static class ToDestroy {
        ViewGroup container;
        int position;
        Object object;

        public ToDestroy(ViewGroup container, int position, Object object) {
            this.container = container;
            this.position = position;
            this.object = object;
        }
    }

}
