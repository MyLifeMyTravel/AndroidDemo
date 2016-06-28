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
import com.littlejie.utils.Logger;
import com.littlejie.utils.MiscUtil;
import com.nostra13.universalimageloader.core.assist.FailReason;

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

    private ImageRelevantInfo mImageRelevantInfo;
    //宽高拉伸比例
    private float mScaleWidth, mScaleHeight;
    //屏幕宽度、高度(需要减去状态栏)
    private int mScreenWidth, mScreenHeight;
    //当前选中图片的下标
    private int mIndex;
    //GridView的列数
    private int mNumColums;
    //图片宽高
    private int mImageWidth, mImageHeight;
    //ViewPager当前页与起始页的偏移
    private float offsetX, offsetY;
    //起始图片的大小，即传入图片的大小
    private int mCurImageWidth, mCurImageHeight;
    //显示图片的uri数组
    private List<String> mLstImageUrl;

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
        mImageRelevantInfo = (ImageRelevantInfo) intent.getSerializableExtra("image");
        mIndex = mImageRelevantInfo.getIndex();
        mNumColums = mImageRelevantInfo.getNumColums();
        mLstImageUrl = mImageRelevantInfo.getLstUri();
        mCurImageWidth = mImageRelevantInfo.getWidth();
        mCurImageHeight = mImageRelevantInfo.getHeight();

        mScreenWidth = MiscUtil.getDisplayWidth();
        //出去状态栏高度，否则缩放的时候会导致比例不正确
        mScreenHeight = MiscUtil.getDisplayHeight() - MiscUtil.getStatusBarHeight();
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
    }

    @Override
    protected void processData() {
        initCurrentImageInfo();
    }

    private void initCurrentImageInfo() {
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
        int py1 = position / mNumColums;
        int px1 = position % mNumColums;
        offsetY = (py1 - py) * mCurImageHeight;
        //position从0开始计数
        offsetX = (px1 - px) * mCurImageWidth;
        Log.d(TAG, "传入点坐标：(" + px + "," + py + "),当前点坐标：(" + px1 + ";" + py1 + ")");
        Log.d(TAG, "偏移量：(" + offsetX + "," + offsetY + ")");
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    private void showImage() {
        mIvTemp = generateTempImageView(mLstImageUrl.get(mIndex));
        mViewContainer.addView(mIvTemp);
        AnimatorSet showAnimatorSet = new AnimatorSet();

        mSpring.setSpringConfig(SpringConfig.fromOrigamiTensionAndFriction(5, 5));
        float translationX = mScreenWidth / 2 - (mImageRelevantInfo.getX() + mCurImageWidth / 2);
        float translationY = mScreenHeight / 2 - (mImageRelevantInfo.getY() + mCurImageHeight / 2);
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
                ObjectAnimator.ofFloat(mIvTemp, "translationX", offsetX).setDuration(200),
                ObjectAnimator.ofFloat(mIvTemp, "translationY", offsetY).setDuration(200)
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

    private View generateImage(String uri) {
        ZoomImageView imageView = new ZoomImageView(this, 0);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        imageView.setLayoutParams(lp);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setAdjustViewBounds(true);
        imageView.setImage(uri);
        imageView.setOnViewTapClickListener(new PhotoViewAttacher.OnViewTapListener() {
            @Override
            public void onViewTap(View view, float v, float v1) {
                startHide();
            }
        });
        return imageView;
    }

    private BaseImageView generateTempImageView(String url) {
        BaseImageView imageView = new BaseImageView(this, 0);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        int x = mImageRelevantInfo.getX();
        int y = mImageRelevantInfo.getY();
        imageView.setLeft(x);
        imageView.setTop(y);

        mScaleWidth = 1.0f * mScreenWidth / mCurImageWidth;
        int imageHeight = (int) 1.0f * mImageHeight * mScreenWidth / mImageWidth;
        mScaleHeight = 1.0f * imageHeight / mCurImageHeight;
        if (imageHeight > mScreenHeight) {
            mScaleHeight = 1.0f * mScreenHeight / mCurImageHeight;
            int imageWidth = (int) 1.0f * mImageWidth * mScreenHeight / mImageHeight;
            mScaleWidth = 1.0f * imageWidth / mCurImageWidth;
        }
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(mCurImageWidth, mCurImageHeight);
        lp.setMargins(x, y, (mScreenWidth - x - mCurImageWidth), (mScreenHeight - y - mCurImageHeight));
        imageView.setLayoutParams(lp);
        imageView.setImage(url);
        return imageView;
    }

    public class PopImagePageAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mLstImageUrl.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = generateImage(mLstImageUrl.get(position));
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
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
                Log.d(TAG, "onSpringUpdateFinish.");
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
