package com.littlejie.mediascan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.littlejie.mediascan.R;
import com.littlejie.mediascan.entity.FileInfo;

import java.util.List;

/**
 * Created by littlejie on 2016/12/28.
 */

public class SimpleAdapter extends BaseAdapter {

    private Context mContext;
    private List<FileInfo> mLstFile;

    public SimpleAdapter(Context context) {
        mContext = context;
    }

    public void setData(List<FileInfo> lstFile) {
        mLstFile = lstFile;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mLstFile == null ? 0 : mLstFile.size();
    }

    @Override
    public Object getItem(int position) {
        return mLstFile == null ? null : mLstFile.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh = null;
        if (convertView == null) {
            vh = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_custom, null);
            vh.tvConetnt = (TextView) convertView.findViewById(R.id.tv_content);
            convertView.setTag(vh);
        }
        vh = (ViewHolder) convertView.getTag();
        FileInfo info = (FileInfo) getItem(position);
        vh.tvConetnt.setText(info.toString());
        return convertView;
    }

    private class ViewHolder {
        TextView tvConetnt;
    }
}
