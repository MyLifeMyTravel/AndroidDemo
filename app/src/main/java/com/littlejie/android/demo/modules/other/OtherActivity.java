package com.littlejie.android.demo.modules.other;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.littlejie.android.demo.R;
import com.littlejie.android.demo.model.CardInfo;
import com.littlejie.android.demo.modules.base.BaseActivity;
import com.littlejie.android.demo.ui.adapter.CardAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lion on 2016/4/6.
 */
public class OtherActivity extends BaseActivity {

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private CardAdapter mAdapter;
    private List<CardInfo> mLstCard;

    @Override
    protected void onResume() {
        super.onResume();
        mAdapter.setData(mLstCard);
    }

    @Override
    protected int getPageLayoutID() {
        return R.layout.activity_mixed_item;
    }

    @Override
    protected void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler);
        mLinearLayoutManager = new LinearLayoutManager(this);
        //一定要记得给RecyclerView设置LayoutManager，否则显示不出来
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        mAdapter = new CardAdapter();
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initViewListener() {

    }

    @Override
    protected void initData() {
        genData();
    }

    private void genData() {
        mLstCard = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            CardInfo info = new CardInfo();
            info.setName("Card " + i);
            mLstCard.add(info);
        }
    }
}
