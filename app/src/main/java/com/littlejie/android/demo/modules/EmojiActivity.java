package com.littlejie.android.demo.modules;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.littlejie.android.demo.R;
import com.littlejie.base.BaseActivity;

import org.apache.commons.lang3.StringEscapeUtils;

/**
 * Created by Lion on 2016/4/25.
 */
public class EmojiActivity extends BaseActivity {

    private EditText mEtEmoji;
    private TextView mTvEmoji, mTvEmojiEncode, mTvEmojiDecode, mTvEmojiDirectDecode;
    private Button mBtnEmoji;

    @Override
    protected int getPageLayoutID() {
        return R.layout.activity_emoji;
    }

    @Override
    protected void initView() {
        mEtEmoji = (EditText) findViewById(R.id.et_emoji);
        mTvEmoji = (TextView) findViewById(R.id.tv_emoji_ori);
        mTvEmojiEncode = (TextView) findViewById(R.id.tv_emoji_encode);
        mTvEmojiDecode = (TextView) findViewById(R.id.tv_emoji_decode);
        mTvEmojiDirectDecode = (TextView) findViewById(R.id.tv_emoji_directly_decode);
        mBtnEmoji = (Button) findViewById(R.id.btn_emoji);
    }

    @Override
    protected void initViewListener() {
        mBtnEmoji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = mEtEmoji.getText().toString();
                String encode = StringEscapeUtils.escapeJava(input);
                String decode = StringEscapeUtils.unescapeJava(encode);
                String dirDecode = StringEscapeUtils.unescapeJava(input);
                mTvEmoji.setText(input);
                StringBuffer sb = new StringBuffer();
                sb.append(Character.toChars(0x54c8));
                sb.append(Character.toChars(0x54c8));
                sb.append(Character.toChars(0x1f393));
                sb.append(Character.toChars(0x1f393));
                sb.append(Character.toChars(0x1f494));
                sb.append(Character.toChars(0x1f48d));
                sb.append(Character.toChars(0x1f388));
                mTvEmojiEncode.setText(sb.toString());
                mTvEmojiDecode.setText(decode);
                mTvEmojiDirectDecode.setText(dirDecode);
                Log.d(TAG, "input:" + input);
                Log.d(TAG, "encode:" + encode);
                Log.d(TAG, "decode:" + decode);
                Log.d(TAG, "dirDecode:" + dirDecode);
            }
        });
    }

    @Override
    protected void processData() {

    }

    @Override
    protected void initData() {

    }
}
