package com.littlejie.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.littlejie.fragment.base.BaseFragment;
import com.littlejie.fragment.util.Constant;

/**
 * 测试 Fragment 生命周期，setUserVisibleHint 初始进来时只有默认 Tab
 * Created by littlejie on 2016/12/30.
 */

public class LifeCircleFragment extends BaseFragment {

    private final String TAG = LifeCircleFragment.class.getSimpleName();
    //截取 Fragment.toString() 方法中的标识数字
    private final String ID = this.toString().substring(this.toString().indexOf("{") + 1, this.toString().length() - 1);
    private TextView mTvContent;

    //默认 Title 值
    private String mTitle = "Tab";

    public static LifeCircleFragment newInstance(String title) {
        Bundle args = new Bundle();

        LifeCircleFragment fragment = new LifeCircleFragment();
        args.putString(Constant.PARAM_TITLE, title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        Log.i(TAG, "Fragment id = " + ID + "," + mTitle + " is onHiddenChanged.hidden = " + hidden);
        super.onHiddenChanged(hidden);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        Log.i(TAG, "Fragment id = " + ID + "," + mTitle + " is setUserVisibleHint.isVisibleToUser = " + isVisibleToUser);
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onAttach(Context context) {
        //由于 onCreate 是在 onAttach 后执行，故此时 mTitle 为空
        Log.i(TAG, "Fragment id = " + ID + "," + mTitle + " is onAttach.");
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.i(TAG, "Fragment id = " + ID + "," + mTitle + " is onCreate.");
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTitle = getArguments().getString(Constant.PARAM_TITLE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "Fragment id = " + ID + "," + mTitle + " is onCreateView.");
        View view = inflater.inflate(R.layout.fragment_life_circle, container, false);
        mTvContent = (TextView) view.findViewById(R.id.tv_content);
        mTvContent.setText(mTitle);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "Fragment id = " + ID + "," + mTitle + " is onViewCreated.");
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.i(TAG, "Fragment id = " + ID + "," + mTitle + " is onActivityCreated.");
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        Log.i(TAG, "Fragment id = " + ID + "," + mTitle + " is onStart.");
        super.onStart();
    }

    @Override
    public void onResume() {
        Log.i(TAG, "Fragment id = " + ID + "," + mTitle + " is onResume.");
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.i(TAG, "Fragment id = " + ID + "," + mTitle + " is onPause.");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.i(TAG, "Fragment id = " + ID + "," + mTitle + " is onStop.");
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        Log.i(TAG, "Fragment id = " + ID + "," + mTitle + " is onDestroyView.");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        //当调用 Activity 的 onDestroy() 时调用
        Log.i(TAG, "Fragment id = " + ID + "," + mTitle + " is onDestroy.");
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        Log.i(TAG, "Fragment id = " + ID + "," + mTitle + " is onDetach.");
        super.onDetach();
    }

}
