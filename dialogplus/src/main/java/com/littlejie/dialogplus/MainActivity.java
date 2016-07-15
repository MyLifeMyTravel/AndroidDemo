package com.littlejie.dialogplus;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import com.littlejie.base.BaseActivity;
import com.littlejie.base.Core;
import com.littlejie.utils.MiscUtil;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnClickListener;
import com.orhanobut.dialogplus.ViewHolder;

public class MainActivity extends BaseActivity {

    @Override
    protected int getPageLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        int margin = Core.getPixel(R.dimen.activity_horizontal_margin);
        DialogPlus dialog = DialogPlus.newDialog(this)
                .setContentHolder(new ViewHolder(R.layout.dialog_only_one_button))
                .setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(DialogPlus dialog, View view) {
                        if (view.getId() == R.id.btn) {
                            MiscUtil.showDefautToast("我被点击了");
                        }
                    }
                }).setGravity(Gravity.CENTER)
                .setContentWidth(ViewGroup.LayoutParams.WRAP_CONTENT)
                .setContentHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
                .setOverlayBackgroundResource(R.color.colorAccent)
                .setFooter(R.layout.dialog_only_one_button)
                .setMargin(margin, margin, margin, margin).setCancelable(true).create();
        dialog.show();
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initViewListener() {

    }

    @Override
    protected void processData() {

    }
}
