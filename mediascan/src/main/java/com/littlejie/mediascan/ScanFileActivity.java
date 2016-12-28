package com.littlejie.mediascan;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.littlejie.mediascan.adapter.SimpleAdapter;
import com.littlejie.mediascan.entity.FileInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * 扫描指定类型文件，这里简单扫描 MimeType = text/plain 或者 image/jpeg 的文件
 * Created by littlejie on 2016/12/28.
 */

public class ScanFileActivity extends AppCompatActivity implements View.OnClickListener {

    //CONTENT_URI 具体使用见 MediaObserverActivity
    private static final Uri CONTENT_URI = MediaStore.Files.getContentUri("external");

    private Button mBtnFilter;
    private ListView mLv;
    private SimpleAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        mBtnFilter = (Button) findViewById(R.id.btn_filter);
        mLv = (ListView) findViewById(R.id.lv);
        mAdapter = new SimpleAdapter(this);
        mLv.setAdapter(mAdapter);

        mBtnFilter.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_filter:
                startFilter();
                break;
        }
    }

    private void startFilter() {
        //此处代码可封装
        String selection = MediaStore.Files.FileColumns.MIME_TYPE + " = ? or "
                + MediaStore.Files.FileColumns.MIME_TYPE + " = ?";
        Cursor cursor = getContentResolver().query(CONTENT_URI, new String[]{MediaStore.MediaColumns.TITLE, MediaStore.MediaColumns.DATA,
                        MediaStore.MediaColumns.DATE_MODIFIED, MediaStore.Files.FileColumns.PARENT},
                selection, new String[]{"text/plain", "image/jpeg"}, null);
        if (cursor == null) {
            return;
        }
        List<FileInfo> lstFile = new ArrayList<>();
        while (cursor.moveToNext()) {
            FileInfo file = new FileInfo();
            //此处 cursor.getString(index) 写法不正规，但却不失简便
            file.setName(cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.TITLE)));
            file.setPath(cursor.getString(1));
            file.setModify(cursor.getLong(2));
            file.setParent(cursor.getInt(3));
            lstFile.add(file);
        }
        mAdapter.setData(lstFile);
    }
}
