package com.littlejie.adapter;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.littlejie.demoutil.Util;

import java.util.ArrayList;
import java.util.List;

public class ListActionActivity extends Activity implements View.OnClickListener {

    private Button mBtnAdd, mBtnAddAll;
    private Button mBtnInsert, mBtnClear;
    private Button mBtnRemove, mBtnSort;
    private Button mBtnSetNotifyOnChangeOpen, mBtnSetNotifyOnChangeClose;
    private ListView mLv;
    private ArrayAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_action);

        mBtnAdd = (Button) findViewById(R.id.btn_add);
        mBtnAddAll = (Button) findViewById(R.id.btn_addAll);
        mBtnInsert = (Button) findViewById(R.id.btn_insert);
        mBtnClear = (Button) findViewById(R.id.btn_clear);
        mBtnRemove = (Button) findViewById(R.id.btn_remove);
        mBtnSort = (Button) findViewById(R.id.btn_sort);
        mBtnSetNotifyOnChangeOpen = (Button) findViewById(R.id.btn_setNotifyOnChangeOpen);
        mBtnSetNotifyOnChangeClose = (Button) findViewById(R.id.btn_setNotifyOnChangeClose);

        mLv = (ListView) findViewById(R.id.lv);
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, Util.generateString(10));
        mLv.setAdapter(mAdapter);

        mBtnAdd.setOnClickListener(this);
        mBtnAddAll.setOnClickListener(this);
        mBtnInsert.setOnClickListener(this);
        mBtnClear.setOnClickListener(this);
        mBtnRemove.setOnClickListener(this);
        mBtnSort.setOnClickListener(this);
        mBtnSetNotifyOnChangeOpen.setOnClickListener(this);
        mBtnSetNotifyOnChangeClose.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add:
                add();
                break;
            case R.id.btn_addAll:
                addAll();
                break;
            case R.id.btn_insert:
                insert();
                break;
            case R.id.btn_clear:
                clear();
                break;
            case R.id.btn_remove:
                remove();
                break;
            case R.id.btn_sort:
                sort();
                break;
            case R.id.btn_setNotifyOnChangeOpen:
                setNotifyOnChangeOpen();
                break;
            case R.id.btn_setNotifyOnChangeClose:
                setNotifyOnCHangeClose();
                break;
        }
    }

    private void add() {
        String add = "我是通过add()添加进来的";
        mAdapter.add(add);
    }

    private void addAll() {
        List<String> addAll = new ArrayList<>();
        addAll.add("addAll-item1");
        addAll.add("addAll-item2");
        mAdapter.addAll(addAll);
    }

    private void insert() {
        String insert = "insert到第二个位置";
        mAdapter.insert(insert, 1);
    }

    private void clear() {
        mAdapter.clear();
    }

    private void remove() {
        mAdapter.remove("item 1");
    }

    private void sort() {
    }

    private void setNotifyOnChangeOpen() {
        mAdapter.setNotifyOnChange(true);
    }

    private void setNotifyOnCHangeClose() {
        mAdapter.setNotifyOnChange(false);
    }
}
