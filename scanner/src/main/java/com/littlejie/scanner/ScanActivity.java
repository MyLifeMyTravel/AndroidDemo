package com.littlejie.scanner;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.zxing.Result;
import com.littlejie.scanner.decoding.InactivityTimer;
import com.littlejie.scanner.interfaces.OnDecodeFinishListener;
import com.littlejie.scanner.view.ScanView;

import java.io.IOException;

/**
 * Initial the camera
 *
 * @author Ryan.Tang
 */
public class ScanActivity extends Activity {

    private static final float BEEP_VOLUME = 0.50f;

    private InactivityTimer inactivityTimer;
    private ScanView scanView;

    private MediaPlayer mediaPlayer;
    private boolean playBeep;
    private boolean vibrate;

    private OnDecodeFinishListener onDecodeFinishListener = new OnDecodeFinishListener() {
        @Override
        public void onDecodeFinish(Result result, Bitmap barcode) {
            playBeepSoundAndVibrate();
            String resultText = result.getText();
            if (TextUtils.isEmpty(resultText)) {
                Toast.makeText(ScanActivity.this, "Scan failed!", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent();
                intent.putExtra("result", resultText);
                setResult(RESULT_OK, intent);
            }
            ScanActivity.this.finish();
        }
    };

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        scanView = (ScanView) findViewById(R.id.scanview);
        scanView.setOnDecodeFinishListener(onDecodeFinishListener);
        findViewById(R.id.btn_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanView.onStart();
            }
        });
        findViewById(R.id.btn_pause).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanView.onPause();
            }
        });
        findViewById(R.id.btn_continue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanView.onContinue();
            }
        });

        inactivityTimer = new InactivityTimer(this);
        initBeepSoundAndVibrate();
    }

    private void initBeepSoundAndVibrate() {
        playBeep = true;
        AudioManager audioService = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
            playBeep = false;
        }
        vibrate = true;
        initBeepSound();
    }

    @Override
    protected void onResume() {
        super.onResume();
        scanView.onStart();
    }

    @Override
    protected void onDestroy() {
        inactivityTimer.shutdown();
        super.onDestroy();
    }

    private void initBeepSound() {
        if (playBeep && mediaPlayer == null) {
            // The volume on STREAM_SYSTEM is not adjustable, and users found it
            // too loud,
            // so we now play on the music stream.
            setVolumeControlStream(AudioManager.STREAM_MUSIC);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnCompletionListener(beepListener);

            AssetFileDescriptor file = getResources().openRawResourceFd(
                    R.raw.beep);
            try {
                mediaPlayer.setDataSource(file.getFileDescriptor(),
                        file.getStartOffset(), file.getLength());
                file.close();
                mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
                mediaPlayer.prepare();
            } catch (IOException e) {
                mediaPlayer = null;
            }
        }
    }

    private static final long VIBRATE_DURATION = 200L;

    private void playBeepSoundAndVibrate() {
        Log.d("ScanActivity", "playBeep=" + playBeep);
        Log.d("ScanActivity", "mediaPlayer=" + mediaPlayer);
        if (playBeep && mediaPlayer != null) {
            mediaPlayer.start();
        }
        if (vibrate) {
            Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(VIBRATE_DURATION);
        }
    }

    /**
     * When the beep has finished playing, rewind to queue up another one.
     */
    private final MediaPlayer.OnCompletionListener beepListener = new MediaPlayer.OnCompletionListener() {
        public void onCompletion(MediaPlayer mediaPlayer) {
            mediaPlayer.seekTo(0);
        }
    };

}