package com.littlejie.android.demo.modules.widget;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.littlejie.android.demo.R;
import com.littlejie.android.demo.ui.adapter.ImageGridAdapter;
import com.littlejie.base.BaseActivity;
import com.littlejie.ui.image.PopImageActivity;
import com.littlejie.ui.image.entity.SelectImageInfo;
import com.littlejie.utils.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lion on 2016/6/23.
 */
public class GridImageActivity extends BaseActivity {

    private static final String[] urls = new String[]{"http://img4q.duitang.com/uploads/item/201408/11/20140811141753_iNtAF.jpeg", "http://imgsrc.baidu.com/forum/pic/item/8b82b9014a90f603fa18d50f3912b31bb151edca.jpg", "http://imgsrc.baidu.com/forum/pic/item/8e230cf3d7ca7bcb3acde5a2be096b63f724a8b2.jpg", "http://att.bbs.duowan.com/forum/201210/20/210446opy9p5pghu015p9u.jpg", "http://img5.duitang.com/uploads/item/201404/11/20140411214939_XswXa.jpeg","http://pic2.ooopic.com/11/07/15/30bOOOPICd7.jpg"};
    private GridView mGridImage;
    private ImageGridAdapter mImageGridAdapter;

    private SelectImageInfo mSelectImageInfo;
    private List<String> mLstImage;

    @Override
    protected int getPageLayoutID() {
        return R.layout.activity_grid_image;
    }

    @Override
    protected void initData() {
        mSelectImageInfo = new SelectImageInfo();
        mLstImage = generateImageUrlList();
    }

    @Override
    protected void initView() {
        mGridImage = (GridView) findViewById(R.id.grid_image);
        mImageGridAdapter = new ImageGridAdapter(this);
        mImageGridAdapter.setLstImg(mLstImage);
        mGridImage.setAdapter(mImageGridAdapter);
    }

    @Override
    protected void initViewListener() {
        mGridImage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mSelectImageInfo.setX(view.getLeft());
                mSelectImageInfo.setY(view.getTop());
                mSelectImageInfo.setHeight(view.getHeight());
                mSelectImageInfo.setWidth(view.getWidth());
                startToPopImageActivity(position);
            }
        });
    }

    private void startToPopImageActivity(int position) {
        Intent intent = new Intent(this, PopImageActivity.class);
        intent.putExtra(Constants.PARAM_INDEX, position);
        intent.putExtra(Constants.PARAM_NUM_COLUMS, mGridImage.getNumColumns());
        intent.putExtra(Constants.PARAM_SELECT_IMAGE_INFO, mSelectImageInfo);
        intent.putStringArrayListExtra(Constants.PARAM_IMAGE_INFO_LIST, (ArrayList<String>) mLstImage);
        startActivity(intent);
    }

    private List<String> generateImageUrlList() {
        List<String> lstImage = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < urls.length; j++) {
                lstImage.add(urls[j]);
            }
        }
        return lstImage;
    }
}
