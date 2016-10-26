package com.littlejie.view.widget.edit;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.littlejie.view.R;

/**
 * 软键盘 imeOptions 测试
 */
public class ImeOptionsActivity extends Activity implements TextView.OnEditorActionListener {

    private static final String TAG = ImeOptionsActivity.class.getSimpleName();

    private EditText mEdtNormal, mEdtActionUnspecified, mEdtActionNone, mEdtActionGo;
    private EditText mEdtActionSearch, mEdtActionSend, mEdtActionNext, mEdtActionDone, mEdtActionPrevious;
    private EditText mEdtActionUnspecifiedTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ime_options);

        mEdtNormal = (EditText) findViewById(R.id.actionNormal);
        mEdtActionUnspecified = (EditText) findViewById(R.id.actionUnspecified_one);
        mEdtActionNone = (EditText) findViewById(R.id.actionNone);
        mEdtActionGo = (EditText) findViewById(R.id.actionGo);
        mEdtActionSearch = (EditText) findViewById(R.id.actionSearch);
        mEdtActionSend = (EditText) findViewById(R.id.actionSend);
        mEdtActionNext = (EditText) findViewById(R.id.actionNext);
        mEdtActionDone = (EditText) findViewById(R.id.actionDone);
        mEdtActionPrevious = (EditText) findViewById(R.id.actionPrevious);
        mEdtActionUnspecifiedTwo = (EditText) findViewById(R.id.actionUnspecified_two);

        mEdtNormal.setOnEditorActionListener(this);
        mEdtActionUnspecified.setOnEditorActionListener(this);
        mEdtActionNone.setOnEditorActionListener(this);
        mEdtActionGo.setOnEditorActionListener(this);
        mEdtActionSearch.setOnEditorActionListener(this);
        mEdtActionSend.setOnEditorActionListener(this);
        mEdtActionNext.setOnEditorActionListener(this);
        mEdtActionDone.setOnEditorActionListener(this);
        mEdtActionPrevious.setOnEditorActionListener(this);
        mEdtActionUnspecifiedTwo.setOnEditorActionListener(this);

    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        Log.d(TAG, "imeOptions=" + v.getHint());
        Log.d(TAG, "actionId=" + actionId + ",Corresponds to:" + getCorrespondsImeAction(actionId));
        Log.d(TAG, "keyEvent=" + event);
        //返回true表示已消费该事件
        return true;
    }

    /**
     * 获取actionId关联的Action
     *
     * @param actionId
     * @return
     */
    private String getCorrespondsImeAction(int actionId) {
        switch (actionId) {
            case EditorInfo.IME_ACTION_DONE:
                return "IME_ACTION_DONE";
            case EditorInfo.IME_ACTION_GO:
                return "IME_ACTION_GO";
            case EditorInfo.IME_ACTION_NEXT:
                return "IME_ACTION_NEXT";
            case EditorInfo.IME_ACTION_NONE:
                return "IME_ACTION_NONE";
            case EditorInfo.IME_ACTION_PREVIOUS:
                return "IME_ACTION_PREVIOUS";
            case EditorInfo.IME_ACTION_SEARCH:
                return "IME_ACTION_SEARCH";
            case EditorInfo.IME_ACTION_SEND:
                return "IME_ACTION_SEND";
            case EditorInfo.IME_ACTION_UNSPECIFIED:
                return "IME_ACTION_UNSPECIFIED";
            default:
                return "";
        }
    }
}
