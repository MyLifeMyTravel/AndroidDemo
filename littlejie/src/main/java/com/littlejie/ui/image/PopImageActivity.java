package com.littlejie.ui.image;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringConfig;
import com.facebook.rebound.SpringListener;
import com.facebook.rebound.SpringSystem;
import com.facebook.rebound.SpringUtil;
import com.littlejie.R;
import com.littlejie.base.BaseActivity;
import com.littlejie.base.Core;
import com.littlejie.utils.Constants;
import com.littlejie.utils.Logger;
import com.littlejie.utils.MiscUtil;
import com.nostra13.universalimageloader.core.assist.FailReason;

import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by Lion on 2016/6/23.
 */
public class PopImageActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    private final String TAG = this.getClass().getSimpleName();

    private RelativeLayout mViewContainer;
    private ViewPager mViewPager;
    private PagerAdapter mPagerAdapter;
    //模拟传入者中对应ImageView
    private BaseImageView mIvTemp;

    private float mScaleWidth, mScaleHeight;
    private int mScreenWidth, mScreenHeight;
    private int mIndex;
    private int mNumColums;
    //图片宽高
    private int mImageWidth, mImageHeight;
    private float x, y;
    private int mScaleImageWidth, mScaleImageHeight;
    private List<String> mLstImageUrl;
    private List<View> mLstImage;

    private Spring mSpring = null;

    @Override
    protected int getPageLayoutID() {
        return R.layout.activity_pop_image;
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        if (intent == null) {
            Logger.w(TAG, "intent is null.");
            finish();
            return;
        }
        mIndex = intent.getIntExtra(Constants.PARAM_INDEX, 0);
        mNumColums = intent.getIntExtra(Constants.PARAM_NUM_COLUMS, 3);
        mLstImageUrl = intent.getStringArrayListExtra(Constants.PARAM_IMAGE_INFO_LIST);
        x = intent.getFloatExtra("x", 0);
        y = intent.getFloatExtra("y", 0);
        mScaleImageWidth = intent.getIntExtra("width", 1);
        mScaleImageHeight = intent.getIntExtra("height", 1);
        mLstImage = generateImageList();
        mScreenWidth = MiscUtil.getDisplayWidth();
        mScreenHeight = MiscUtil.getDisplayHeight() - MiscUtil.getStatusBarHeight();
        mSpring = SpringSystem
                .create()
                .createSpring()
                .addListener(new MySpringListener());
    }

    private void getSelectImageInfo() {
        BaseImageView imageView = new BaseImageView(this, 0);
        imageView.setImage(mLstImageUrl.get(mIndex), new Core.OnImageLoadListener() {
            @Override
            public void onLoadindComplete(String s, View v, Bitmap bitmap) {
                mImageWidth = bitmap.getWidth();
                mImageHeight = bitmap.getHeight();
                showImage();
            }

            @Override
            public void onLoadingFailed(String s, View view, FailReason failReason) {

            }
        });
    }

    @Override
    protected void initView() {
        mViewContainer = (RelativeLayout) findViewById(R.id.container);
        mViewPager = (ViewPager) findViewById(R.id.viewpage);
        mPagerAdapter = new PopImagePageAdapter();
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setCurrentItem(mIndex);
        mViewPager.setVisibility(View.GONE);
    }

    @Override
    protected void initViewListener() {
        mViewPager.setOnPageChangeListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getSelectImageInfo();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//        moveView.setX(dip2px(5) + dip2px(10) * position + dip2px(10) * positionOffset);
    }

    @Override
    public void onPageSelected(int position) {
        if (mIvTemp == null) {
            return;
        }
        mIvTemp.setImage(mLstImageUrl.get(position));
        int py = mIndex / mNumColums;
        int px = mIndex % mNumColums;
        int originOffsetX = px * mScaleImageWidth;
        int originOffsetY = py * mScaleImageHeight;
        int py1 = position / mNumColums;
        int px1 = position % mNumColums;
        y = (py1 - py) * mScaleImageHeight + originOffsetY;
        //position从0开始计数
        x = (px1 - px) * mScaleImageWidth + originOffsetX;
        Log.d(TAG, "传入点坐标：(" + px + "," + py + "),当前点坐标：(" + px1 + ";" + py1 + ")");
        Log.d(TAG, "偏移量：(" + x + "," + y + ")");
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void slideImage() {

    }


    private void showImage() {
        mIvTemp = generateTempImageView(mLstImageUrl.get(mIndex));
        mViewContainer.addView(mIvTemp);
        AnimatorSet showAnimatorSet = new AnimatorSet();

        mSpring.setSpringConfig(SpringConfig.fromOrigamiTensionAndFriction(5, 5));
        float translationX = mScreenWidth / 2 - (x + mScaleImageWidth / 2);
        float translationY = mScreenHeight / 2 - (y + mScaleImageHeight / 2);
        showAnimatorSet.playTogether(
                ObjectAnimator.ofFloat(mIvTemp, "translationX", translationX).setDuration(200),
                ObjectAnimator.ofFloat(mIvTemp, "translationY", translationY).setDuration(200)
        );
        showAnimatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mIvTemp.setScaleType(ImageView.ScaleType.FIT_XY);
                mSpring.setEndValue(1);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        showAnimatorSet.start();
    }

    private void hideImage() {
        AnimatorSet hideAnimatiorSet = new AnimatorSet();
        hideAnimatiorSet.playTogether(
                ObjectAnimator.ofFloat(mIvTemp, "translationX", x).setDuration(200),
                ObjectAnimator.ofFloat(mIvTemp, "translationY", y).setDuration(200)
        );
        hideAnimatiorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                mIvTemp.setScaleType(ImageView.ScaleType.CENTER_CROP);
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                finish();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        hideAnimatiorSet.start();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mViewPager.getVisibility() == View.VISIBLE) {
                startHide();
            }
        }
        return true;
    }

    private void startHide() {
        mViewPager.setVisibility(View.GONE);
        mIvTemp.setVisibility(View.VISIBLE);
        mSpring.setSpringConfig(SpringConfig.fromOrigamiTensionAndFriction(1, 5));
        mSpring.setEndValue(0);
        MiscUtil.runOnUIThreadDelayed(300, new Runnable() {
            @Override
            public void run() {
                hideImage();
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }

    private List<View> generateImageList() {
        List<View> lstImage = new ArrayList<>();
        for (String url : mLstImageUrl) {
            ZoomImageView imageView = new ZoomImageView(this, 0);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            imageView.setLayoutParams(lp);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setAdjustViewBounds(true);
            imageView.setImage(url);
            imageView.setOnViewTapClickListener(new PhotoViewAttacher.OnViewTapListener() {
                @Override
                public void onViewTap(View view, float v, float v1) {
                    startHide();
                }
            });
            lstImage.add(imageView);
        }
        return lstImage;
    }

    private BaseImageView generateTempImageView(String url) {
        BaseImageView imageView = new BaseImageView(this, 0);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLeft((int) x);
        imageView.setTop((int) y);

        mScaleWidth = 1.0f * mScreenWidth / mScaleImageWidth;
        int imageHeight = (int) 1.0f * mImageHeight * mScreenWidth / mImageWidth;
        mScaleHeight = 1.0f * imageHeight / mScaleImageHeight;
        if (imageHeight > mScreenHeight) {
            mScaleHeight = 1.0f * mScreenHeight / mScaleImageHeight;
            int imageWidth = (int) 1.0f * mImageWidth * mScreenHeight / mImageHeight;
            mScaleWidth = 1.0f * imageWidth / mScaleImageWidth;
        }
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(mScaleImageWidth, mScaleImageHeight);
        lp.setMargins((int) x, (int) y, (mScreenWidth - ((int) x + mScaleImageWidth)), (mScreenHeight - ((int) y + mScaleImageHeight)));
        imageView.setLayoutParams(lp);
        imageView.setImage(url);
        return imageView;
    }

    public class PopImagePageAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mLstImage.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mLstImage.get(position));
            return mLstImage.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mLstImage.get(position));
        }
    }

    private class MySpringListener implements SpringListener {

        @Override
        public void onSpringUpdate(Spring spring) {
            double currentValue = spring.getCurrentValue();
            float x = (float) SpringUtil.mapValueFromRangeToRange(currentValue, 0, 1, 1, mScaleWidth);
            float y = (float) SpringUtil.mapValueFromRangeToRange(currentValue, 0, 1, 1, mScaleHeight);
            Log.d(TAG, "onSpringUpdate.currentValue=" + currentValue + ";x=" + x + ";y=" + y);
            mIvTemp.setScaleX(x);
            mIvTemp.setScaleY(y);
            if (currentValue == 1) {
                Log.d(TAG, "mIvTemp:width=" + mIvTemp.getWidth() + ";height=" + mIvTemp.getHeight());
                Log.d(TAG, "mViewPager Item:width=" + mLstImage.get(mIndex).getWidth() + ";height=" + mLstImage.get(mIndex).getHeight());
                mViewPager.setVisibility(View.VISIBLE);
                MiscUtil.runOnUIThreadDelayed(300, new Runnable() {
                    @Override
                    public void run() {
                        mIvTemp.setVisibility(View.GONE);
                    }
                });
            }
        }

        @Override
        public void onSpringAtRest(Spring spring) {

        }

        @Override
        public void onSpringActivate(Spring spring) {

        }

        @Override
        public void onSpringEndStateChange(Spring spring) {

        }
    }
}
