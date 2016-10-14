package com.littlejie.tint;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends Activity {

    private List<Integer> mLstColor;
    private GridView mGv;
    private ImageAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initColors();

        mGv = (GridView) findViewById(R.id.gv);
        mAdapter = new ImageAdapter();
        mGv.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    private void initColors() {
        mLstColor = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            mLstColor.add(Color.argb(random(), random(), random(), random()));
        }
    }

    private int random() {
        Random random = new Random();
        return random.nextInt(256);
    }

    private class ImageAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mLstColor.size();
        }

        @Override
        public Object getItem(int position) {
            return mLstColor.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = new ImageView(MainActivity.this);
            }
            Drawable drawable = getResources().getDrawable(R.mipmap.ic_android_black_24dp);
            ((ImageView) convertView).setBackgroundDrawable(Util.tintDrawable(drawable, ColorStateList.valueOf(mLstColor.get(position))));
            return convertView;
        }
    }
}
