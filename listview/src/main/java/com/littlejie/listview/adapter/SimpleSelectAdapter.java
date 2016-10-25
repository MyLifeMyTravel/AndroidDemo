package com.littlejie.listview.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.littlejie.listview.widget.SimpleSelectItem;

import java.util.List;

/**
 * Created by littlejie on 2016/10/21.
 */

public class SimpleSelectAdapter extends BaseAdapter {

    private static final String TAG = SimpleSelectAdapter.class.getSimpleName();

    private Context mContext;
    private List<String> mLstItem;

    private int mSelectItem = -1;

    public SimpleSelectAdapter(Context context) {
        mContext = context;
    }

    public void setData(List<String> lstItem) {
        mLstItem = lstItem;
        notifyDataSetChanged();
    }

    public void setSelectItem(int position) {
        mSelectItem = position;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mLstItem == null ? 0 : mLstItem.size();
    }

    @Override
    public Object getItem(int position) {
        return mLstItem == null ? null : mLstItem.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = new SimpleSelectItem(mContext);
        }

        String item = mLstItem.get(position);
        SimpleSelectItem simpleSelectItem = (SimpleSelectItem) convertView;
        simpleSelectItem.setItem(item);
        simpleSelectItem.setSelected(mSelectItem == position ? true : false);
        return convertView;
    }
}
