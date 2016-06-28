package com.littlejie.android.viewstub;

import android.view.View;

import com.littlejie.base.BaseActivity;
import com.littlejie.ui.widget.UploadImageWidget;
import com.littlejie.utils.MiscUtil;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private UploadImageWidget uploadImageWidget;
    private List<String> lst;

    @Override
    protected int getPageLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        lst = new ArrayList<>();
    }

    @Override
    protected void initView() {
        uploadImageWidget = (UploadImageWidget) findViewById(R.id.up);
        uploadImageWidget.setOnImageListener(new UploadImageWidget.OnImageListener() {
            @Override
            public void onTakePhoto() {
                lst.add("0");
                uploadImageWidget.setData(lst);
            }

            @Override
            public void onDeleteImage(int position) {
                lst.remove(position);
                uploadImageWidget.setData(lst);
            }

            @Override
            public void onImageClick(View v, int position) {
                MiscUtil.showDefautToast("替换图片" + position);
            }
        });
    }

    @Override
    protected void initViewListener() {

    }

    @Override
    protected void processData() {

    }

}
