package com.littlejie.mediascan;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.littlejie.mediascan.adapter.SimpleAdapter;
import com.littlejie.mediascan.entity.FileInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试文件选择
 */
public class SelectActivity extends AppCompatActivity {

    private static final Uri CONTENT_URI = MediaStore.Files.getContentUri("external");

    private ListView mLvFile;
    private SimpleAdapter mAdapter;
    private List<FileInfo> mLstFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        mLvFile = (ListView) findViewById(R.id.lv_file_item);
        mAdapter = new SimpleAdapter(this);
        mLvFile.setAdapter(mAdapter);
        listFolderItem(getIntent().getLongExtra("parent", 0));

        mLvFile.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FileInfo info = mLstFile.get(position);
                Intent intent = new Intent(SelectActivity.this, SelectActivity.class);
                intent.putExtra("parent", info.getId());
                startActivity(intent);
//                listFolderItem(info.getParent());
            }
        });
    }


    private void listFolderItem(long parent) {
        Cursor cursor = getContentResolver().query(CONTENT_URI,
                new String[]{BaseColumns._ID, MediaStore.MediaColumns.TITLE, MediaStore.MediaColumns.DATA,
                        MediaStore.MediaColumns.DATE_MODIFIED, MediaStore.Files.FileColumns.PARENT},
                "parent = ?",
                new String[]{String.valueOf(parent)},
                null);
        if (cursor == null) {
            return;
        }
        mLstFile = new ArrayList<>();
        long start = System.currentTimeMillis();
        while (cursor.moveToNext()) {
            FileInfo file = new FileInfo();
            //此处 cursor.getString(index) 写法不正规，但却不失简便
            file.setId(cursor.getLong(0));
            file.setName(cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.TITLE)));
            //这样写可以减少10ms左右的时间
            String path = cursor.getString(2);
            file.setPath(path);
            //如果把new File放到FileInfo中执行，可以减少5ms左右，但是效果不稳定
//            file.setFile(new File(path));
            file.setModify(cursor.getLong(3));
            file.setParent(cursor.getInt(4));
            mLstFile.add(file);
        }
        Log.d("ListFile", "spend time = " + (System.currentTimeMillis() - start));
        mAdapter.setData(mLstFile);
    }
}
