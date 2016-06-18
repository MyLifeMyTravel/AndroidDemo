package com.littlejie.scanner;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.littlejie.scanner.camera.CameraManager;
import com.littlejie.scanner.decoding.CodeDecodeHandler;
import com.littlejie.scanner.interfaces.OnDecodeFinishListener;
import com.littlejie.scanner.view.ViewfinderView;

import java.io.IOException;
import java.util.Vector;

/**
 * Created by Lion on 2016/6/18.
 */
public class ScanView extends LinearLayout implements SurfaceHolder.Callback, IHandler {

    private static final String TAG = "ScanView";

    private Context context;
    private ViewfinderView viewfinderView;
    private SurfaceView surfaceView;

    private CodeDecodeHandler handler;
    private Vector<BarcodeFormat> decodeFormats;
    private String characterSet;
    private boolean hasSurface;

    private OnDecodeFinishListener onDecodeFinishListener;

    public ScanView(Context context) {
        super(context);
        init(context, null);
    }

    public ScanView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        this.context = context;
        View view = LayoutInflater.from(context).inflate(R.layout.scan, this);
        CameraManager.init(context);
        viewfinderView = (ViewfinderView) view.findViewById(R.id.viewfinder_view);
        surfaceView = (SurfaceView) view.findViewById(R.id.preview_view);

        hasSurface = false;
    }

    public void onResume() {
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        if (hasSurface) {
            initCamera(surfaceHolder);
        } else {
            surfaceHolder.addCallback(this);
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }
        decodeFormats = null;
        characterSet = null;
    }

    public void onPause() {
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        CameraManager.get().closeDriver();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!hasSurface) {
            hasSurface = true;
            initCamera(holder);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;
    }

    @Override
    public Handler getDecodeHandler() {
        return handler;
    }

    @Override
    public void handleDecode(Result result, Bitmap barcode) {
        if (onDecodeFinishListener != null) {
            onDecodeFinishListener.onDecodeFinish(result, barcode);
        } else {
            Log.e(TAG, "onDecodeFinishListener is null");
        }
    }

    @Override
    public ViewfinderView getViewfinderView() {
        return viewfinderView;
    }

    @Override
    public void drawViewfinder() {
        viewfinderView.drawViewfinder();
    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        try {
            CameraManager.get().openDriver(surfaceHolder);
        } catch (IOException ioe) {
            showOpenCameraFail();
            return;
        } catch (RuntimeException e) {
            showOpenCameraFail();
            return;
        }
        if (handler == null) {
            handler = new CodeDecodeHandler(this, decodeFormats,
                    characterSet);
        }
    }

    private void showOpenCameraFail() {
        Toast.makeText(context,
                context.getString(R.string.open_camera_failed)
                        + context.getString(R.string.open_camera_permission_tip),
                Toast.LENGTH_SHORT).show();
    }

    public void setOnDecodeFinishListener(OnDecodeFinishListener onDecodeFinishListener) {
        this.onDecodeFinishListener = onDecodeFinishListener;
    }
}
