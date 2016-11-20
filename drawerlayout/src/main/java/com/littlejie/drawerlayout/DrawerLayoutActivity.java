package com.littlejie.drawerlayout;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class DrawerLayoutActivity extends AppCompatActivity {

    private int[] mIcons = new int[]{R.mipmap.ic_contacts_black_24dp, R.mipmap.ic_message_black_24dp
            , R.mipmap.ic_wifi_black_24dp, R.mipmap.ic_settings_black_24dp};
    private String[] mContents = new String[]{"Contacts", "Message", "Wifi", "Settings"};

    private DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;
    private TextView mTvContent;
    private ListView mLvItem;
    private DrawerItemAdapter mAdapter;

    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_layout);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        mTvContent = (TextView) findViewById(R.id.tv_content);
        mLvItem = (ListView) findViewById(R.id.lv_item);
        mAdapter = new DrawerItemAdapter();
        mLvItem.setAdapter(mAdapter);

        mLvItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mTvContent.setText("Current Selected Item : " + position);
            }
        });

        //设置 Logo
        //mToolbar.setLogo(R.mipmap.ic_launcher);
        //使用 Toolbar 取代 ActionBar
        setSupportActionBar(mToolbar);
        //注意，设置 Toolbar 及相关点击事件最好放在 setSupportActionBar 后，否则很可能无效
        //设置 Navigation 图标和点击事件必须放在 setSupportActionBar 后，否则无效
        mToolbar.setNavigationIcon(R.mipmap.ic_menu_black_24dp);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar,
                R.string.open_drawer, R.string.close_drawer) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        mDrawerLayout.addDrawerListener(mDrawerToggle);
//        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(DrawerLayoutActivity.this, "dshfje", Toast.LENGTH_SHORT).show();
//            }
//        });
        //给左上角图标的左边加上一个返回的图标
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_menu_black_24dp);
    }

    class DrawerItemAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mIcons.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder vh;
            if (convertView == null) {
                vh = new ViewHolder();
                convertView = LayoutInflater.from(DrawerLayoutActivity.this).inflate(R.layout.item_drawer, null);
                vh.ivIcon = (ImageView) convertView.findViewById(R.id.iv_icon);
                vh.tvContent = (TextView) convertView.findViewById(R.id.tv_content);
                convertView.setTag(vh);
            }
            vh = (ViewHolder) convertView.getTag();
            vh.ivIcon.setImageResource(mIcons[position]);
            vh.tvContent.setText(mContents[position]);
            return convertView;
        }

        class ViewHolder {
            ImageView ivIcon;
            TextView tvContent;
        }
    }
}
