package com.littlejie.popupfabmenu;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by littlejie on 16/8/25.
 */
public class FABMenu extends LinearLayout {

    private static final String TAG = FABMenu.class.getSimpleName();

    public FABMenu(Context context) {
        super(context);
        init(context, null);
    }

    public FABMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        View view = LayoutInflater.from(context).inflate(R.layout.widget_popup_fab_menu, this);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.d(TAG, "手势="+ev.getAction());
        if (ev.getAction() == MotionEvent.ACTION_UP) {
            Log.d(TAG, "手势抬起");
            setVisibility(GONE);
            return true;
        }
        return super.onInterceptTouchEvent(ev);
    }

}
