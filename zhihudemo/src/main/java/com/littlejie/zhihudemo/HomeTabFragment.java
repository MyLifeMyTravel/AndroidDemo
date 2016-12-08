package com.littlejie.zhihudemo;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by sli on 2016/12/8.
 */

public class HomeTabFragment extends Fragment {

    private static final String TAG = HomeTabFragment.class.getSimpleName();
    private TextView mTvContent;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, this + " onAttach");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, this + " onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_tab, container, false);
        mTvContent = (TextView) view.findViewById(R.id.tv_content);
        Log.d(TAG, this + " onCreateView");
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, this + " onActivityCreated");
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        Log.d(TAG, this + " onViewStateRestored");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, this + " onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, this + " onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, this + " onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, this + " onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, this + " onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, this + " onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, this + " onDetach");
    }

    public void setContent(String content) {
        mTvContent.setText(content);
    }
}
