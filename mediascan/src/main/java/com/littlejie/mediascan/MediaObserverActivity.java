package com.littlejie.mediascan;

import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.littlejie.mediascan.adapter.SimpleAdapter;
import com.littlejie.mediascan.entity.FileInfo;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * 多媒体文件观察
 */
public class MediaObserverActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = MediaObserverActivity.class.getSimpleName();
    private static final String ROOT = Environment.getExternalStorageDirectory().getAbsolutePath();
    private static final String TMP_FILE = ROOT + "/tmp1.txt";
    //获取外部存储上图片的Uri，相当于 content://media/external/images/media
    //MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
    //获取外部存储上音频的Uri，相当于 content://media/external/audio/media
    //MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
    //获取外部存储上视频的Uri，相当于 content://media/external/video/media
    //MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
    //获取外部存储上的文件Uri，一般用于上述几种类型处理不了的情况，相当于 content://media/external/files/
    private static final Uri CONTENT_URI = MediaStore.Files.getContentUri("external");
    private Handler mHandler = new Handler();
    private MediaStoreObserver mMediaStoreObserver = new MediaStoreObserver(mHandler);

    private ListView mLv;
    private SimpleAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_observer);

        mLv = (ListView) findViewById(R.id.lv);
        mAdapter = new SimpleAdapter(this);
        mLv.setAdapter(mAdapter);

        findViewById(R.id.btn_create_file).setOnClickListener(this);
        findViewById(R.id.btn_delete_file).setOnClickListener(this);
        getContentResolver().registerContentObserver(CONTENT_URI, false, mMediaStoreObserver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getContentResolver().unregisterContentObserver(mMediaStoreObserver);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_create_file:
                try {
                    //记得添加文件读写权限，此处使用 Apache commons-io 库来实现文件读写
                    FileUtils.write(new File(TMP_FILE), "test", Charset.forName("UTF-8"));
                    Toast.makeText(this, "创建文件成功", Toast.LENGTH_SHORT).show();
                    sendScanFileBroadcast(TMP_FILE);
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "创建文件失败", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_delete_file:
                try {
                    FileUtils.forceDelete(new File(TMP_FILE));
                    Toast.makeText(this, "删除文件成功", Toast.LENGTH_SHORT).show();
                    sendScanFileBroadcast(TMP_FILE);
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "删除文件失败", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    /**
     * 发送扫描文件的广播
     *
     * @param file
     */
    private void sendScanFileBroadcast(String file) {
        //Uri 必须为 file://+文件路径
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                Uri.parse("file://" + file));
        sendBroadcast(intent);
    }

    /**
     * 多媒体文件内容观察者，当注册的uri发生改变时进行回调
     * 需要传递 Handler，用于 UI 更新，建议传递主线程的 Handler
     */
    private class MediaStoreObserver extends ContentObserver {

        /**
         * Creates a content observer.
         *
         * @param handler The handler to run {@link #onChange} on, or null if none.
         */
        public MediaStoreObserver(Handler handler) {
            super(handler);
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            Log.d(TAG, "MediaStore.Files has changed.");
            //查询外部存储数据库信息，关于数据库字段可以查看 MediaStore 子类相关的 Columns
            //或者通过 adb 导出数据库查看，数据库位于 com.android.providers.media/databases 目录下
            //按修改时间降序排列
            Cursor cursor = getContentResolver().query(CONTENT_URI,
                    new String[]{MediaStore.MediaColumns.TITLE, MediaStore.MediaColumns.DATA,
                            MediaStore.MediaColumns.DATE_MODIFIED, MediaStore.Files.FileColumns.PARENT},
                    null,
                    null,
                    MediaStore.MediaColumns.DATE_MODIFIED + " desc");
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
}
