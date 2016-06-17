/*
 * Copyright (C) 2008 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.littlejie.scanner.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import com.google.zxing.ResultPoint;
import com.littlejie.scanner.R;
import com.littlejie.scanner.camera.CameraManager;

import java.util.Collection;
import java.util.HashSet;

/**
 * This view is overlaid on top of the camera preview. It adds the viewfinder
 * rectangle and partial transparency outside it, as well as the laser scanner
 * animation and result points.
 */
public final class ViewfinderView extends View {

    private static final long ANIMATION_DELAY = 30L;
    private static final int OPAQUE = 0xFF;
    private static final int RECT_CORNER_WIDTH = 10;
    private static final int RECT_CORNER_LENGTH = 20;

    private final Paint paint;
    private final int resultPointColor;
    private Bitmap mScanBar;
    private Rect frame;
    private Collection<ResultPoint> possibleResultPoints;
    private Collection<ResultPoint> lastPossibleResultPoints;
    private int mCornerLength;
    private int mSlideTop = -1;

    // This constructor is used when the class is built from an XML resource.
    public ViewfinderView(Context context, AttributeSet attrs) {
        super(context, attrs);

        // Initialize these once for performance rather than calling them every time in onDraw().
        paint = new Paint();
        Resources resources = getResources();
        resultPointColor = resources.getColor(R.color.possible_result_points);
        possibleResultPoints = new HashSet<ResultPoint>(5);
        mScanBar = BitmapFactory.decodeResource(getResources(), R.drawable.scan_bar);

        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);

        mCornerLength = (int) dm.density * RECT_CORNER_LENGTH;
    }

    private void scaleBar() {
        int barWidth = frame.right - frame.left - 2 * mCornerLength;
        int width = mScanBar.getWidth();

        float scaleWidth = ((float) barWidth) / width;

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, 1.0f);

        mScanBar = Bitmap.createBitmap(mScanBar, 0, 0, width, mScanBar.getHeight(), matrix, true);
    }

    @Override
    public void onDraw(Canvas canvas) {
        frame = CameraManager.get().getFramingRect();
        if (frame == null) {
            return;
        }
        int width = canvas.getWidth();
        int height = canvas.getHeight();
        if (mSlideTop == -1) {
            mSlideTop = frame.top;
        }

        // Draw the exterior (i.e. outside the framing rect) darkened
        drawShadowRect(canvas, frame, width, height);

        // Draw a two pixel solid black border inside the framing rect
//        drawInnerRect(canvas, frame);
        drawRectCorner(canvas, frame.left, frame.top, frame.right, frame.bottom);
        scaleBar();
        drawScanningBar(canvas, frame.left, mSlideTop);

        drawText(canvas, frame.left, frame.bottom, frame.right);

        Collection<ResultPoint> currentPossible = possibleResultPoints;
        Collection<ResultPoint> currentLast = lastPossibleResultPoints;
        if (currentPossible.isEmpty()) {
            lastPossibleResultPoints = null;
        } else {
            possibleResultPoints = new HashSet<ResultPoint>(5);
            lastPossibleResultPoints = currentPossible;
            paint.setAlpha(OPAQUE);
            paint.setColor(resultPointColor);
            for (ResultPoint point : currentPossible) {
                canvas.drawCircle(frame.left + point.getX(), frame.top + point.getY(), 6.0f, paint);
            }
        }
        if (currentLast != null) {
            paint.setAlpha(OPAQUE / 2);
            paint.setColor(resultPointColor);
            for (ResultPoint point : currentLast) {
                canvas.drawCircle(frame.left + point.getX(), frame.top + point.getY(), 3.0f, paint);
            }
        }

        mSlideTop += 16;
        if (mSlideTop >= frame.bottom) {
            mSlideTop = frame.top;
        }
        // Request another update at the animation interval, but only repaint the laser line,
        // not the entire viewfinder mask.
        postInvalidateDelayed(ANIMATION_DELAY, frame.left, frame.top, frame.right, frame.bottom);
    }

    public void drawViewfinder() {
        invalidate();
    }

    public void addPossibleResultPoint(ResultPoint point) {
        possibleResultPoints.add(point);
    }

    private void drawShadowRect(Canvas canvas, Rect frame, int width, int height) {
        paint.reset();
        paint.setAntiAlias(true);
        paint.setColor(getResources().getColor(R.color.shadow));

        //上
        canvas.drawRect(0, 0, width, frame.top, paint);
        //左
        canvas.drawRect(0, frame.top, frame.left, frame.bottom, paint);
        //右
        canvas.drawRect(frame.right, frame.top, width, frame.bottom, paint);
        //下
        canvas.drawRect(0, frame.bottom, width, height, paint);
    }

    private void drawInnerRect(Canvas canvas, Rect frame) {
        int rectWidth = 10;
        paint.reset();
        paint.setAntiAlias(true);
        paint.setColor(getResources().getColor(R.color.white));

        //左
        canvas.drawRect(frame.left, frame.top, frame.left + rectWidth, frame.bottom, paint);
        //上
        canvas.drawRect(frame.left, frame.top, frame.right, frame.top + rectWidth, paint);
        //右
        canvas.drawRect(frame.right - rectWidth, frame.top, frame.right, frame.bottom, paint);
        //下
        canvas.drawRect(frame.left, frame.bottom - rectWidth, frame.right, frame.bottom, paint);
    }

    private void drawRectCorner(Canvas canvas, int left, int top, int right, int bottom) {
        paint.reset();
        paint.setAntiAlias(true);
        paint.setColor(getResources().getColor(R.color.corner));

        //左上
        canvas.drawRect(left, top, left + RECT_CORNER_WIDTH, top + mCornerLength, paint);
        canvas.drawRect(left, top, left + mCornerLength, top + RECT_CORNER_WIDTH, paint);

        //右上
        canvas.drawRect(right - mCornerLength, top, right, top + RECT_CORNER_WIDTH, paint);
        canvas.drawRect(right - RECT_CORNER_WIDTH, top, right, top + mCornerLength, paint);

        //左下
        canvas.drawRect(left, bottom - mCornerLength, left + RECT_CORNER_WIDTH, bottom, paint);
        canvas.drawRect(left, bottom - RECT_CORNER_WIDTH, left + mCornerLength, bottom, paint);

        //右下
        canvas.drawRect(right - mCornerLength, bottom - RECT_CORNER_WIDTH, right, bottom, paint);
        canvas.drawRect(right - RECT_CORNER_WIDTH, bottom - mCornerLength, right, bottom, paint);
    }

    private void drawScanningBar(final Canvas canvas, float left, float top) {
        paint.reset();
        canvas.drawBitmap(mScanBar, left + mCornerLength, top + mCornerLength, paint);
    }

    private void drawText(Canvas canvas, float left, float bottom, int right) {
        paint.reset();
        paint.setColor(Color.WHITE);
        String scanTip = getResources().getString(R.string.scan_tip);
        float textSize = (right - left) / scanTip.length();
        paint.setTextSize(textSize);

        canvas.drawText(scanTip, left, bottom + getFontHeight() + mCornerLength, paint);
    }

    private float getFontHeight() {
        Paint.FontMetrics metrics = paint.getFontMetrics();
        return metrics.descent - metrics.ascent;
    }

    private float getFontWidth(String text) {
        return paint.measureText(text);
    }

}