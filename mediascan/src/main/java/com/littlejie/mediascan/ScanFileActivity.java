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

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

public class ScanFileActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = ScanFileActivity.class.getSimpleName();
    private static final String ROOT = Environment.getExternalStorageDirectory().getAbsolutePath();
    private static final String TMP_FILE = ROOT + "/tmp.txt";
    //获取外部存储上的文件Uri，相当于 content://media/external/files/
    private static final Uri CONTENT_URI = MediaStore.Files.getContentUri("external");
    private Handler mHandler = new Handler();
    private MediaStoreObserver mMediaStoreObserver = new MediaStoreObserver(mHandler);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_file);

        findViewById(R.id.btn_create_file).setOnClickListener(this);

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
                    //记得添加文件读写权限
                    FileUtils.write(new File(TMP_FILE), "test", Charset.forName("UTF-8"));
                    sendScanFileBroadcast(TMP_FILE);
                } catch (IOException e) {
                    e.printStackTrace();
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
            Log.d(TAG, "MediaStore has changed.");
            //查询外部存储数据库信息
            Cursor cursor = getContentResolver().query(CONTENT_URI,
                    new String[]{MediaStore.MediaColumns.DATA, MediaStore.MediaColumns.DATE_MODIFIED},
                    null,
                    null,
                    MediaStore.MediaColumns.DATE_MODIFIED + " desc");
            if (cursor == null) {
                return;
            }
            while (cursor.moveToNext()) {
                Log.d(TAG, cursor.getString(0));
            }
        }
    }
}
