package com.littlejie.android.demo.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.littlejie.android.demo.model.CardInfo;
import com.littlejie.android.demo.ui.widget.CardItem;

import java.util.List;

/**
 * Created by Lion on 2016/4/7.
 */
public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

    private List<CardInfo> lstCard = null;

    public CardAdapter() {
    }

    public CardAdapter(List<CardInfo> lstCard) {
        this.lstCard = lstCard;
    }

    public void setData(List<CardInfo> lstCard) {
        this.lstCard = lstCard;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = new CardItem(parent.getContext());
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CardInfo info = lstCard.get(position);
        holder.cardItem.setCardInfo(info);
    }

    @Override
    public int getItemCount() {
        return lstCard == null ? 0 : lstCard.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private CardItem cardItem;

        public ViewHolder(View itemView) {
            super(itemView);
            cardItem = (CardItem) itemView;
        }
    }
}
