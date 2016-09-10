package com.littlejie.listview;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class EmptyListViewActivity extends Activity {

    private ListView mLv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty_list_view);

        mLv = (ListView) findViewById(R.id.lv);
        mLv.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, Utils.generateString(0)));
        mLv.setEmptyView(findViewById(android.R.id.empty));
    }

    class SimpleArrayAdapter extends ArrayAdapter<String> {

        public SimpleArrayAdapter(Context context, int resource, List<String> objects) {
            super(context, resource, objects);
        }

        @Override
        public boolean isEmpty() {
            return super.isEmpty();
        }
    }

}
