package com.littlejie.androidui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;

public class EditTextActivity extends Activity {

    private EditText mEdtInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_text);

        mEdtInput = (EditText) findViewById(R.id.edt_input);

    }
}
