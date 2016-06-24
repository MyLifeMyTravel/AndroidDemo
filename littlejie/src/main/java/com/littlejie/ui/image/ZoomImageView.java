package com.littlejie.ui.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.View;

import com.littlejie.base.Core;
import com.nostra13.universalimageloader.core.assist.FailReason;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by Lion on 2016/6/24.
 */
public class ZoomImageView extends BaseImageView {

    private PhotoViewAttacher attacher;

    public ZoomImageView(Context context, int resId) {
        super(context, resId);
        init(context,null);
    }

    public ZoomImageView(Context context, int resId, boolean stateful) {
        super(context, resId, stateful);
        init(context, null);
    }

    public ZoomImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,null);
    }

    private void init(Context context, AttributeSet attrs) {
        attacher = new PhotoViewAttacher(this);
    }

    @Override
    public void setImage(String url) {
        super.setImage(url, new Core.OnImageLoadListener() {
            @Override
            public void onLoadindComplete(String s, View v, Bitmap bitmap) {
                attacher.update();
            }

            @Override
            public void onLoadingFailed(String s, View view, FailReason failReason) {

            }
        });
    }
}
