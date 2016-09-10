package com.littlejie.listview;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ComplexEmptyListViewActivity extends Activity {

    private ListView mLv;
    private EmptyView mEmptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complex_empty_list_view);

        mLv = (ListView) findViewById(R.id.lv);
        mLv.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, Utils.generateString(10)));

        mEmptyView = new EmptyView(this);
        //此处是重点,如果不将mEmptyView添加进当前的ViewGroup,mEmptyView都不可见
        ((ViewGroup) mLv.getParent()).addView(mEmptyView);
        mLv.setEmptyView(mEmptyView);
    }
}
