package com.littlejie.ui.image;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Handler;
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
import com.littlejie.ui.image.entity.ImageInfo;
import com.littlejie.ui.image.entity.SelectImageInfo;
import com.littlejie.utils.Constants;
import com.littlejie.utils.Logger;
import com.littlejie.utils.MiscUtil;

import java.util.ArrayList;
import java.util.List;

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
    private ImageInfo mImageInfo;
    private SelectImageInfo mSelectImageInfo;
    private List<String> mLstImageUrl;
    private List<View> mLstImage;

    private Handler mHandler;

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
        mSelectImageInfo = (SelectImageInfo) intent.getSerializableExtra(Constants.PARAM_SELECT_IMAGE_INFO);
        mLstImageUrl = intent.getStringArrayListExtra(Constants.PARAM_IMAGE_INFO_LIST);
        mImageInfo = new ImageInfo();
        mImageInfo.setWidth(1280);
        mImageInfo.setHeight(720);
        mScreenWidth = MiscUtil.getDisplayWidth();
        mScreenHeight = MiscUtil.getDisplayHeight();
        mLstImage = generateImageList();
        mIvTemp = generateTempImageView(mSelectImageInfo, mLstImageUrl.get(mIndex));
        mHandler = new Handler();
        mSpring = SpringSystem
                .create()
                .createSpring()
                .addListener(new MySpringListener());
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
        showImage();
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
        int a = mIndex / mNumColums;
        int b = mIndex % mNumColums;
        int a1 = position / mNumColums;
        int b1 = position % mNumColums;
        mSelectImageInfo.setY((a1 - a) * mSelectImageInfo.getHeight());
        mSelectImageInfo.setX((b1 - b) * mSelectImageInfo.getHeight());
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void slideImage() {

    }


    private void showImage() {
        mViewContainer.addView(mIvTemp);
        AnimatorSet showAnimatorSet = new AnimatorSet();

        mSpring.setSpringConfig(SpringConfig.fromOrigamiTensionAndFriction(5, 5));
        float translationX = mScreenWidth / 2 - (mSelectImageInfo.getX() + mSelectImageInfo.getWidth() / 2);
        float translationY = mScreenHeight / 2 - (mSelectImageInfo.getY() + mSelectImageInfo.getHeight() / 2);
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
                ObjectAnimator.ofFloat(mIvTemp, "translationX", mSelectImageInfo.getX()).setDuration(200),
                ObjectAnimator.ofFloat(mIvTemp, "translationY", mSelectImageInfo.getY()).setDuration(200)
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
                mViewPager.setVisibility(View.GONE);
                mIvTemp.setVisibility(View.VISIBLE);
                mSpring.setSpringConfig(SpringConfig.fromOrigamiTensionAndFriction(1, 5));
                mSpring.setEndValue(0);
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        hideImage();
                    }
                }, 300);
            }
        }
        return true;
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }

    private List<View> generateImageList() {
        List<View> lstImage = new ArrayList<>();
        for (String url : mLstImageUrl) {
            BaseImageView imageView = new BaseImageView(this, 0);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            imageView.setLayoutParams(lp);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setAdjustViewBounds(true);
            imageView.setImage(url);
            lstImage.add(imageView);
        }
        return lstImage;
    }

    private BaseImageView generateTempImageView(SelectImageInfo info, String url) {
        BaseImageView imageView = new BaseImageView(this, 0);
        imageView.setLeft((int) info.getX());
        imageView.setTop((int) info.getY());

        float x = mSelectImageInfo.getX();
        float y = mSelectImageInfo.getY();
        int width = mSelectImageInfo.getWidth();
        int height = mSelectImageInfo.getHeight();
        mScaleWidth = 1.0f * mScreenWidth / width;
        int imageHeight = (int) 1.0f * mImageInfo.getHeight() * mScreenWidth / mImageInfo.getWidth();
        mScaleHeight = 1.0f * imageHeight / height;
        if (imageHeight > mScreenHeight) {
            mScaleHeight = 1.0f * mScreenHeight / height;
            int imageWidth = (int) 1.0f * mImageInfo.getWidth() * mScreenHeight / mImageInfo.getHeight();
            mScaleWidth = 1.0f * imageWidth / width;
        }
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(width, height);
        lp.setMargins((int) x, (int) y, (mScreenWidth - ((int) x + width)), (mScreenHeight - ((int) y + height)));
        imageView.setLayoutParams(lp);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
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
                mViewPager.setVisibility(View.VISIBLE);
                mHandler.postDelayed(new Runnable() {
                    public void run() {
                        //execute the task
                        mIvTemp.setVisibility(View.GONE);
                    }
                }, 300);
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
