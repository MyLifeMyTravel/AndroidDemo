package com.littlejie.animation;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class DemoFloatSubActionActivity extends Activity {

    private WindowManager.LayoutParams mParamsFab, mParamsShadow, mParamsView;
    private LinearLayout mShadowView;
    private ImageView mIvFab;
    private FloatSubActionView mFlaot;

    private boolean state = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_float_sub_action);
        initParams();
        mIvFab = new ImageView(this);
        mIvFab.setBackgroundResource(R.mipmap.ic_launcher);
        mShadowView = new LinearLayout(this);
        mShadowView.setBackgroundColor(Color.parseColor("#88000000"));
        mFlaot = new FloatSubActionView(DemoFloatSubActionActivity.this);

        getWindowManager().addView(mIvFab, mParamsFab);
        mIvFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DemoFloatSubActionActivity.this, "hahah", Toast.LENGTH_SHORT).show();
                float from = state ? 45 : 0;
                float to = state ? 0 : 45;
                state = !state;
                rotateFAB(mIvFab, from, to);
            }
        });

    }

    private void rotateFAB(View view, float from, float to) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "rotation", from, to);
        animator.setDuration(300);
        animator.start();
        if (state) {
            getWindowManager().addView(mShadowView, mParamsShadow);
            getWindowManager().removeView(mIvFab);
            getWindowManager().addView(mIvFab, mParamsFab);
            getWindowManager().addView(mFlaot, mParamsView);
            mFlaot.show(true, true, true, true, null);
        } else {
            mFlaot.hide(new FloatSubActionView.OnFinishListener() {
                @Override
                public void onFinish() {
                    getWindowManager().removeView(mShadowView);
                    getWindowManager().removeView(mFlaot);
                }
            });
        }
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    private void initParams() {
        mParamsShadow = new WindowManager.LayoutParams();
        mParamsShadow.format = PixelFormat.RGBA_8888;
        mParamsShadow.gravity = Gravity.TOP | Gravity.LEFT;
        mParamsShadow.width = WindowManager.LayoutParams.MATCH_PARENT;
        mParamsShadow.height = WindowManager.LayoutParams.MATCH_PARENT;
        mParamsShadow.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        mParamsShadow.format = PixelFormat.TRANSLUCENT;
        mParamsShadow.windowAnimations = 0;

        mParamsFab = new WindowManager.LayoutParams();
        mParamsFab.format = PixelFormat.RGBA_8888;
        mParamsFab.gravity = Gravity.TOP | Gravity.LEFT;
        mParamsFab.width = WindowManager.LayoutParams.WRAP_CONTENT;
        mParamsFab.height = WindowManager.LayoutParams.WRAP_CONTENT;
        mParamsFab.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        mParamsFab.format = PixelFormat.TRANSLUCENT;
        mParamsFab.windowAnimations = 0;
        mParamsFab.x = 800;
        mParamsFab.y = 1920;

        mParamsView = new WindowManager.LayoutParams();
        mParamsView.format = PixelFormat.RGBA_8888;
        mParamsView.gravity = Gravity.TOP | Gravity.LEFT;
        mParamsView.width = WindowManager.LayoutParams.MATCH_PARENT;
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        mParamsView.height = dm.heightPixels - bitmap.getHeight() * 3;
        mParamsView.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        mParamsView.format = PixelFormat.TRANSLUCENT;
        mParamsView.windowAnimations = 0;
    }
}
