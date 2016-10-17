package com.littlejie.animation.problem;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.littlejie.animation.R;

/**
 * 验证 View 的 Visiblity = GONE 时的动画效果。
 */
public class GoneViewAnimationActivity extends Activity {

    private ImageView mIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gone_view_animation);

        mIv = (ImageView) findViewById(R.id.iv);
        mIv.setVisibility(View.GONE);
        mIv.startAnimation(AnimationUtils.loadAnimation(this, R.anim.sub_action_fade_in));
    }
}
