package com.littlejie.animation;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * Created by littlejie on 2016/9/29.
 */

public class FloatSubActionView extends LinearLayout implements View.OnClickListener {

    private SubActionView mSubRepair, mSubNeighbor, mSubSecondHand, mSubPosts;
    private Animation mFadeIn, mFadeOut;

    public FloatSubActionView(Context context) {
        super(context);
        init(context, null);
    }

    public FloatSubActionView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_float_sub_action, this);
        mSubRepair = (SubActionView) view.findViewById(R.id.sub_repair);
        mSubNeighbor = (SubActionView) view.findViewById(R.id.sub_neighbor);
        mSubSecondHand = (SubActionView) view.findViewById(R.id.sub_second_hand);
        mSubPosts = (SubActionView) view.findViewById(R.id.sub_posts);

        mSubRepair.setOnClickListener(this);
        mSubNeighbor.setOnClickListener(this);
        mSubSecondHand.setOnClickListener(this);
        mSubPosts.setOnClickListener(this);

        mFadeIn = AnimationUtils.loadAnimation(getContext(), R.anim.sub_action_fade_in);
        mFadeIn.setInterpolator(new DecelerateInterpolator());
        mFadeOut = AnimationUtils.loadAnimation(getContext(), R.anim.sub_action_fade_out);
        mFadeOut.setInterpolator(new AccelerateInterpolator());
    }

    public void show(boolean one, boolean two, boolean three, boolean four, final OnFinishListener listener) {
        mFadeIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (listener != null) {
                    listener.onFinish();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mSubPosts.setVisibility(GONE);
        mSubPosts.startAnimation(mFadeIn);
        mSubSecondHand.startAnimation(mFadeIn);
        mSubNeighbor.startAnimation(mFadeIn);
        mSubRepair.startAnimation(mFadeIn);
    }

    public void hide(final OnFinishListener listener) {
        mFadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (listener != null) {
                    listener.onFinish();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mSubPosts.startAnimation(mFadeOut);
        mSubSecondHand.startAnimation(mFadeOut);
        mSubNeighbor.startAnimation(mFadeOut);
        mSubRepair.startAnimation(mFadeOut);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sub_repair:
                Toast.makeText(getContext(), R.string.sub_repair_title, Toast.LENGTH_LONG).show();
                break;
            case R.id.sub_neighbor:
                Toast.makeText(getContext(), R.string.sub_neighbor_title, Toast.LENGTH_LONG).show();
                break;
            case R.id.sub_second_hand:
                Toast.makeText(getContext(), R.string.sub_second_hand_title, Toast.LENGTH_LONG).show();
                break;
            case R.id.sub_posts:
                Toast.makeText(getContext(), R.string.sub_posts_title, Toast.LENGTH_LONG).show();
                break;
        }
    }

    public interface OnFinishListener {
        void onFinish();
    }
}
