package com.littlejie.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by littlejie on 2016/11/22.
 */

public class MyCanvas extends View {

    private Paint mTextPaint;

    public MyCanvas(Context context) {
        super(context);
        init(context, null);
    }

    public MyCanvas(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mTextPaint = new Paint();
        //设置 Paint 抗锯齿
        mTextPaint.setAntiAlias(true);
        //setARGB()是setColor和setAlpha的合体
        //设置 Paint 颜色
        mTextPaint.setColor(Color.BLUE);
        //设置透明度
        mTextPaint.setAlpha(128);
        //设置字体大小，此方法摘自TextView的实现
        mTextPaint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 20, getResources().getDisplayMetrics()));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawText("测试", 0, 0, mTextPaint);
        //index：起始位置,count：绘制个数
        canvas.drawText(new char[]{'a', 'b', 'c', 'd'}, 1, 2, 0, 100, mTextPaint);
        canvas.drawText("测试12345", 1, 3, 0, 150, mTextPaint);
        Path path = new Path();
        RectF rectF = new RectF(0, 200, 300, 500);
        path.addOval(rectF, Path.Direction.CCW);
        //hOffset参数指定水平偏移、vOffset指定垂直偏移
        canvas.drawTextOnPath("弯成了椭圆", path, 20, 20, mTextPaint);
    }

}
