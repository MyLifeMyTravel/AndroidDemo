package com.littlejie.android.demo.ui.widget;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.littlejie.android.demo.R;
import com.littlejie.android.demo.model.CardInfo;

/**
 * Created by Lion on 2016/4/7.
 */
public class CardItem extends LinearLayout {

    private CardView mCardView;
    private TextView mTvName;

    public CardItem(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_base_card, this);
        mCardView = (CardView) view.findViewById(R.id.cardview);
        mTvName = (TextView) view.findViewById(R.id.tv_name);
        setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    public void setCardInfo(CardInfo info) {
        if (info == null) {
            return;
        }
        mTvName.setText(info.getName());
    }
}
