package com.littlejie.window;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private ViewTreeObserver.OnGlobalLayoutListener mGlobalLayoutListener;
    private ImageView mIvFloat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mIvFloat = new ImageView(this);
        mIvFloat.setImageResource(R.mipmap.ic_launcher);
        final Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);

        mGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Log.d("TAG", "onGlobalLayout");
                getWindow().getDecorView().getViewTreeObserver().removeOnGlobalLayoutListener(mGlobalLayoutListener);
                if (bitmap.getHeight() == 0 && bitmap.getWidth() == 0) {
                    getWindow().getDecorView().getViewTreeObserver().addOnGlobalLayoutListener(mGlobalLayoutListener);
                    return;
                }
//                Button button = new Button(MainActivity.this);
//                button.setText("1234");
                WindowManager.LayoutParams params = new WindowManager.LayoutParams();
                params.format = PixelFormat.RGBA_8888;
                params.gravity = Gravity.TOP | Gravity.LEFT;
                params.height = WindowManager.LayoutParams.WRAP_CONTENT;
                params.width = WindowManager.LayoutParams.WRAP_CONTENT;
                params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
//                params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
//                        | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
//                        | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
//                        | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
                params.format = PixelFormat.TRANSLUCENT;
                params.windowAnimations = 0;
                params.x = bitmap.getWidth();
                params.y = bitmap.getHeight();

                getWindowManager().addView(mIvFloat, params);
            }
        };
        getWindow().getDecorView().getViewTreeObserver().addOnGlobalLayoutListener(mGlobalLayoutListener);
    }

    public View getActivityContentView() {
        try {
            return getWindow().getDecorView().findViewById(android.R.id.content);
        } catch (ClassCastException e) {
            throw new ClassCastException("Please provide an Activity context for this FloatingActionButton.");
        }
    }
}
