package com.littlejie.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by littlejie on 2016/12/1.
 */

public class DBManager {

    private static final String TAG = DBManager.class.getSimpleName();
    private static final int BUFFER_SIZE = 1024;
    private static final String DB = "simon.db";
    private static final String TEMP_DB_PATH = Environment.getExternalStorageDirectory().getAbsolutePath()
            + "/" + DB;
    private static final String FORMAT = "id = %1$d, name = %2$s, age = %3$d, address = %4$s, salary = %5$f.";

    private Context mContext;
    private SQLiteDatabase mDatabase;

    public DBManager(Context context) {
        mContext = context;
    }

    public void openDatabase() {
        openOuterDatabase(TEMP_DB_PATH);
    }

    public void queryAll() {
        String sql = "select * from company";
        Cursor cursor = mDatabase.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            //columnName必须与数据库里的一致，即 ID 不能写成 id
            //如果columnName不存在，getColumnIndex()返回-1
            //相比使用getColumnIndexOrThrow()，getColumnIndex()更加高效
//            int id = cursor.getInt(cursor.getColumnIndex("ID"));
//            String name = cursor.getString(cursor.getColumnIndexOrThrow("NAME"));
//            int age = cursor.getInt(cursor.getColumnIndexOrThrow("AGE"));
//            String address = cursor.getString(cursor.getColumnIndexOrThrow("ADDRESS"));
//            float salary = cursor.getFloat(cursor.getColumnIndexOrThrow("SALARY"));
            //可以直接使用整型代替cursor.getColumnIndexOrThrow("ID")，但是得确保index相对应
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            int age = cursor.getInt(2);
            String address = cursor.getString(3);
            float salary = cursor.getFloat(4);
            Log.d(TAG, String.format(FORMAT, id, name, age, address, salary));
        }
        cursor.close();
    }

    /**
     * 打开外部数据库
     *
     * @param db
     */
    private void openOuterDatabase(String db) {
        try {
            File dbFile = new File(db);
            if (!dbFile.exists()) {
                InputStream is = mContext.getResources().openRawResource(R.raw.test);
                FileOutputStream fos = new FileOutputStream(dbFile);
                byte[] buffer = new byte[BUFFER_SIZE];
                int count = 0;
                while ((count = is.read(buffer)) > 0) {
                    fos.write(buffer, 0, count);
                }
                fos.close();
                is.close();
            }
            mDatabase = SQLiteDatabase.openOrCreateDatabase(db, null);
        } catch (FileNotFoundException e) {
            Log.d(TAG, "File not found.");
            e.printStackTrace();
        } catch (IOException e) {
            Log.d(TAG, "IOException");
            e.printStackTrace();
        }
    }
}
