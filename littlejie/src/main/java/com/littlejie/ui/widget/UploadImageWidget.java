package com.littlejie.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.littlejie.R;
import com.littlejie.ui.image.BaseImageView;

import java.util.List;

/**
 * Created by Lion on 2016/6/27.
 */
public class UploadImageWidget extends LinearLayout {

    private Context mContext;
    private GridView mGvUploadImage;
    private UploadImageItemAdapter mAdapter;

    //实际已选择的图片张数
    private int mRealSize;
    //能上传的最大张数
    private int mMaxSize = 9;
    private int mNumColums = 3;
    private List<String> mLstUri;

    private OnImageListener mOnImageListener;

    public UploadImageWidget(Context context) {
        super(context);
        init(context, null);
    }

    public UploadImageWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mContext = context;
        View view = LayoutInflater.from(mContext).inflate(R.layout.widget_upload_image, this);
        mGvUploadImage = (GridView) view.findViewById(R.id.gv_upload_image);
        mAdapter = new UploadImageItemAdapter();
        initAttrs(context, attrs);
        mGvUploadImage.setNumColumns(mNumColums);
        mGvUploadImage.setAdapter(mAdapter);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.UploadImageWidget);
            mNumColums = typedArray.getInt(R.styleable.UploadImageWidget_numColums, 3);
            mMaxSize = typedArray.getInt(R.styleable.UploadImageWidget_maxSize, 9);
        }
    }

    public void setData(List<String> lstData) {
        mLstUri = lstData;
        mAdapter.notifyDataSetChanged();
    }

    public int getNumColums() {
        return mGvUploadImage.getNumColumns();
    }

    public void setOnImageListener(OnImageListener listener) {
        mOnImageListener = listener;
    }

    public interface OnImageListener {

        void onAddImage();

        void onDeleteImage(int position);

        void onImageClick(View v, int position);
    }

    private class UploadImageItemAdapter extends BaseAdapter {

        private static final int TYPE_SELECT = 0;
        private static final int TYPE_IMAGE = 1;

        @Override
        public int getCount() {
            if (mLstUri == null) {
                mRealSize = 0;
                return 1;
            } else if (mLstUri.size() < mMaxSize) {
                mRealSize = mLstUri.size();
                return mLstUri.size() + 1;
            } else {
                mRealSize = mMaxSize;
                return mMaxSize;
            }
        }

        @Override
        public int getItemViewType(int position) {
            //排除当uri size=1的情况
            if (mRealSize == mMaxSize) {
                return TYPE_IMAGE;
            }
            if (position == getCount() - 1 && position < mMaxSize) {
                return TYPE_SELECT;
            } else {
                return TYPE_IMAGE;
            }
        }

        @Override
        public int getViewTypeCount() {
            return 2;
        }

        @Override
        public Object getItem(int position) {
            return mLstUri == null ? null : mLstUri.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                ViewHolder vh = new ViewHolder();
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_upload_image, null);
                vh.ivImage = (BaseImageView) convertView.findViewById(R.id.iv_upload_image);
                vh.ivDelete = (ImageView) convertView.findViewById(R.id.iv_delete);
                convertView.setTag(vh);
            }
            ViewHolder vh = (ViewHolder) convertView.getTag();
            int type = getItemViewType(position);
            if (type == TYPE_SELECT) {
                vh.ivImage.setImage(R.mipmap.add_picture);
                vh.ivDelete.setVisibility(GONE);
                vh.ivImage.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnImageListener != null) {
                            mOnImageListener.onAddImage();
                        }
                    }
                });
            } else {
                vh.ivImage.setImage(mLstUri.get(position));
                vh.ivImage.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnImageListener != null) {
                            mOnImageListener.onImageClick(v, position);
                        }
                    }
                });
                vh.ivDelete.setVisibility(VISIBLE);
                vh.ivDelete.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnImageListener != null) {
                            mOnImageListener.onDeleteImage(position);
                        }
                    }
                });
            }
            return convertView;
        }

        class ViewHolder {
            BaseImageView ivImage;
            ImageView ivDelete;
        }
    }
}