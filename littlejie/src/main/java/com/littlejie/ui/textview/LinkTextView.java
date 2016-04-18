package com.littlejie.ui.textview;

import android.content.Context;
import android.text.method.LinkMovementMethod;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Lion on 2016/4/15.
 */
public class LinkTextView extends TextView {

    public LinkTextView(Context context) {
        super(context);
        init(context);
    }

    public LinkTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        setMovementMethod(LinkMovementMethod.getInstance());
    }
}
