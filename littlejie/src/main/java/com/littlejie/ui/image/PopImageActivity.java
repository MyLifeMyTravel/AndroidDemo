package com.littlejie.ui.image;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.littlejie.R;
import com.littlejie.base.BaseActivity;
import com.littlejie.ui.image.entity.ImageInfo;
import com.littlejie.ui.image.entity.SelectImageInfo;
import com.littlejie.utils.Constants;
import com.littlejie.utils.Logger;

import java.util.List;

/**
 * Created by Lion on 2016/6/23.
 */
public class PopImageActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    private final String TAG = this.getClass().getSimpleName();

    private ViewPager mViewPager;
    private PagerAdapter mPagerAdapter;

    private int mIndex;
    private SelectImageInfo mSelectImageInfo;
    private List<ImageInfo> mLstImageInfo;

    @Override
    protected int getPageLayoutID() {
        return R.layout.activity_pop_image;
    }

    @Override
    protected void initView() {
        mViewPager = (ViewPager) findViewById(R.id.viewpage);
    }

    @Override
    protected void initViewListener() {

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
        mSelectImageInfo = (SelectImageInfo) intent.getSerializableExtra(Constants.PARAM_SELECT_IMAGE_INFO);
        mLstImageInfo = (List<ImageInfo>) intent.getSerializableExtra(Constants.PARAM_IMAGE_INFO_LIST);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//        moveView.setX(dip2px(5) + dip2px(10) * position + dip2px(10) * positionOffset);
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void slideImage() {

    }

    private void showImage() {
//        AnimatorSet showAnimatorSet = new AnimatorSet();
//        showAnimatorSet.playTogether(
//                ObjectAnimator.ofFloat(showimg, "translationX", tx).setDuration(200),
//                ObjectAnimator.ofFloat(showimg, "translationY", ty).setDuration(200),
//                ObjectAnimator.ofFloat(MainView, "alpha", 1).setDuration(200)
//        );
//        showAnimatorSet.addListener(new Animator.AnimatorListener() {
//            @Override
//            public void onAnimationStart(Animator animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                if (mSelectImageInfo.getUrl() == null) {
//                    showimg.setScaleType(ImageView.ScaleType.FIT_XY);
//                    mSpring.setEndValue(1);
//                }
//            }
//
//            @Override
//            public void onAnimationCancel(Animator animation) {
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animator animation) {
//
//            }
//        });
//        showAnimatorSet.start();
    }

    private void hideImage() {
//        AnimatorSet hideAnimatorSet = new AnimatorSet();
//        hideAnimatorSet.playTogether(
//                ObjectAnimator.ofFloat(showimg, "translationX", to_x).setDuration(200),
//                ObjectAnimator.ofFloat(showimg, "translationY", to_y).setDuration(200)
//        );
//        hideAnimatorSet.addListener(new Animator.AnimatorListener() {
//            @Override
//            public void onAnimationStart(Animator animator) {
//                showimg.setScaleType(ImageView.ScaleType.CENTER_CROP);
//            }
//
//            @Override
//            public void onAnimationEnd(Animator animator) {
//                EndMove();
//            }
//
//            @Override
//            public void onAnimationCancel(Animator animator) {
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animator animator) {
//
//            }
//        });
//        hideAnimatorSet.start();
    }

    private List<View> generateIndicator(int size, int color) {
//        for (int i = 0; i < size; i++) {
//            View addview = new View(this);
//            addview.setBackgroundColor(color);
//            LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(dip2px(5), dip2px(5));
//            addview.setLayoutParams(p);
//            p.setMargins(dip2px(5), 0, 0, 0);
//            AddLayout.addView(addview);
//        }
        return null;
    }
}
