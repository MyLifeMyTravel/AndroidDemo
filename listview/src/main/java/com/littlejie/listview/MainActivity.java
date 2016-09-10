package com.littlejie.listview;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends Activity implements View.OnClickListener {

    private EditText mEdt;
    private Button mBtnJump, mBtnMoveUp, mBtnMoveDown;
    private Button mBtnSelect;
    private Button mBtnSelectPositive, mBtnSelectNagetive;
    private ListView mLv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEdt = (EditText) findViewById(R.id.edt);
        mBtnJump = (Button) findViewById(R.id.btn_jump);
        mBtnMoveUp = (Button) findViewById(R.id.btn_move_up);
        mBtnMoveDown = (Button) findViewById(R.id.btn_move_down);
        mBtnSelect = (Button) findViewById(R.id.btn_select);
        mBtnSelectPositive = (Button) findViewById(R.id.btn_select_from_top_positive_offset);
        mBtnSelectNagetive = (Button) findViewById(R.id.btn_select_from_top_nagetive_offset);
        mLv = (ListView) findViewById(R.id.lv);
        mLv.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, Utils.generateString(10)));

        mBtnJump.setOnClickListener(this);
        mBtnMoveUp.setOnClickListener(this);
        mBtnMoveDown.setOnClickListener(this);
        mBtnSelect.setOnClickListener(this);
        mBtnSelectPositive.setOnClickListener(this);
        mBtnSelectNagetive.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String input = mEdt.getText().toString();
        int position = Integer.valueOf(input);
        switch (v.getId()) {
            case R.id.btn_jump:
                //平滑滚动到指定位置
                mLv.smoothScrollToPosition(position);
                break;
            case R.id.btn_move_up:
                //平滑上移指定数量的item
                mLv.smoothScrollByOffset(-position);
                break;
            case R.id.btn_move_down:
                //平滑下移指定数量的item
                mLv.smoothScrollByOffset(position);
                break;
            case R.id.btn_select:
                //跳转到指定的位置,不平移
                mLv.setSelection(position);
                break;
            case R.id.btn_select_from_top_positive_offset:
                //跳转到指定的位置,第二个参数表示Y轴偏移量
                mLv.setSelectionFromTop(position, 100);
                break;
            case R.id.btn_select_from_top_nagetive_offset:
                //跳转到指定的位置,第二个参数表示Y轴偏移量
                mLv.setSelectionFromTop(position, -100);
                break;
        }
    }
}
