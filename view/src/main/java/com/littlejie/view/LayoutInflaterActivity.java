package com.littlejie.view;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * 1.root和attachRoot的组合及效果
 * 2.获取LayoutInflater对象的两种方法
 * 3.layout_width、layout_height和width、height
 * 平时我们经常使用layout_width和layout_height来设置View的大小，并且一直都能正常工作，就好像这两个属性确实是用于设置View的大小的。而实际上则不然，它们其实是用于设置View在布局中的大小的，也就是说，首先View必须存在于一个布局中，之后如果将layout_width设置成match_parent表示让View的宽度填充满布局，如果设置成wrap_content表示让View的宽度刚好可以包含其内容，如果设置成具体的数值则View的宽度会变成相应的数值。这也是为什么这两个属性叫作layout_width和layout_height，而不是width和height。
 * 4.setContentView
 */
public class LayoutInflaterActivity extends Activity implements View.OnClickListener {

    private static final String TAG = LayoutInflaterActivity.class.getSimpleName();

    private Button mBtnRootNotNull, mBtnRootNull, mBtnRootWithFalse, mBtnRootWithTrue;
    private LinearLayout mLlContainer;
    private LayoutInflater mInflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_inflater);

        findViewById(R.id.btn_root_is_not_null).setOnClickListener(this);
        findViewById(R.id.btn_root_is_null).setOnClickListener(this);
        findViewById(R.id.btn_root_with_false).setOnClickListener(this);
        findViewById(R.id.btn_root_with_true).setOnClickListener(this);

        mLlContainer = (LinearLayout) findViewById(R.id.ll_container);
        //获取LayoutInflater实例
        //也可以通过 mInflater = (LayoutInflater) this.getSystemService(Context.Layout_INFLATER_SERVICE)来获取
        mInflater = LayoutInflater.from(this);
    }

    @Override
    public void onClick(View v) {
        //执行点击事件前，先把mLlContainer中的view移除
        mLlContainer.removeAllViews();
        switch (v.getId()) {
            case R.id.btn_root_is_not_null:
                inflateWithRoot();
                break;
            case R.id.btn_root_is_null:
                inflateWithoutRoot();
                break;
            case R.id.btn_root_with_false:
                inflateWithRootAndFalse();
                break;
            case R.id.btn_root_with_true:
                inflateWithRootAndTrue();
                break;
        }
    }

    /**
     * 使用 inflate(int resource,ViewGroup root) 载入布局
     * root != null
     */
    private void inflateWithRoot() {
        View inflateView = mInflater.inflate(R.layout.layout_single_button, mLlContainer);
        Log.d(TAG, "method = inflateWithRoot,inflaterView type = " + inflateView.getClass().getSimpleName());
        //此处必须注释掉，否则会抛出如下crash
        //java.lang.IllegalStateException: The specified child already has a parent. You must call removeView() on the child's parent first
        //mLlContainer.addView(inflateView);
    }

    /**
     * 使用 inflate(int resource,ViewGroup root) 载入布局
     * root != null
     */
    private void inflateWithoutRoot() {
        View inflateView = mInflater.inflate(R.layout.layout_single_button, null);
        Log.d(TAG, "method = inflateWithoutRoot,inflaterView type = " + inflateView.getClass().getSimpleName());
        //此处注释必须要去掉，否则看不到载入的button
        //mLlContainer.addView(inflateView);
    }

    private void inflateWithRootAndFalse() {
        //情况特殊,这里attachRoot=false,照理button的layout_width和layout_height应该无效
        //其实查看源码可知,当attachRoot=false的时候,系统默认会生成一个LayoutParams
        View inflateView = mInflater.inflate(R.layout.layout_single_button, mLlContainer, false);
        mLlContainer.addView(inflateView);
    }

    private void inflateWithRootAndTrue() {
        View inflateView = mInflater.inflate(R.layout.layout_single_button, mLlContainer, true);
        //java.lang.IllegalStateException: The specified child already has a parent. You must call removeView() on the child's parent first
        //mLlContainer.addView(inflateView);
    }
}
