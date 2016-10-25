package com.littlejie.listview.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.littlejie.listview.R;

/**
 * 简单的选择item，左侧文字，右侧选中的时候显示图标
 * Created by littlejie on 2016/10/21.
 */

public class SimpleSelectItem extends LinearLayout {

    private TextView mTvItem;
    private ImageView mIvSelect;

    public SimpleSelectItem(Context context) {
        super(context);
        init(context, null);
    }

    public SimpleSelectItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_simple_select, this);
        mTvItem = (TextView) view.findViewById(R.id.tv_item);
        mIvSelect = (ImageView) view.findViewById(R.id.iv_select);

        if (attrs != null) {
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.SimpleSelectItem);
            CharSequence text = array.getText(R.styleable.SimpleSelectItem_title);
            if (!TextUtils.isEmpty(text)) {
                mTvItem.setText(text);
            }
        }
    }

    public void setItem(String item) {
        mTvItem.setText(item);
    }

    public void setItemColor(int color) {
        mTvItem.setTextColor(color);
    }

    public void setImageSelected(boolean selected) {
        mIvSelect.setSelected(selected);
    }

    @Override
    public void setSelected(boolean selected) {
        //注释掉此句，就能正确调用ImageView的setSelected
        super.setSelected(selected);
        mIvSelect.setSelected(selected);
    }
}
