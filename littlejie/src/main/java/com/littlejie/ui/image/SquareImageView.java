package com.littlejie.ui.image;

import android.content.Context;
import android.util.AttributeSet;

public class SquareImageView extends BaseImageView {

    public SquareImageView(Context context, int resId) {
        super(context, resId);
    }

    public SquareImageView(Context context, int resId, boolean stateful) {
        super(context, resId, stateful);
    }

    public SquareImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int small = Math.min(width, height);
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        super.onMeasure(MeasureSpec.makeMeasureSpec(small, mode), MeasureSpec.makeMeasureSpec(small, mode));
    }
}