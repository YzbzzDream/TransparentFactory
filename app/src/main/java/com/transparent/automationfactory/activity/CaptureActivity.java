/*
 * @项目名称: ETCP停车
 * @文件名称: CaptureActivity.java
 * @Copyright: 2016 悦畅科技有限公司. All rights reserved.
 * 注意：本内容仅限于悦畅科技有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */

package com.transparent.automationfactory.activity;

import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.transparent.automationfactory.R;
import com.transparent.automationfactory.base.activity.BaseTitleBarActivity;
import com.transparent.automationfactory.base.util.DisplayUtil;
import com.transparent.automationfactory.base.util.ToastUtil;
import com.transparent.automationfactory.zxing.camera.CameraManager;
import com.transparent.automationfactory.zxing.camera.CameraSurfaceView;
import com.transparent.automationfactory.zxing.decoding.CaptureActivityHandler;
import com.transparent.automationfactory.zxing.decoding.InactivityTimer;
import com.transparent.automationfactory.zxing.view.ViewfinderView;

import java.io.IOException;
import java.util.Vector;

public class CaptureActivity extends BaseTitleBarActivity implements Callback,
        OnClickListener,
        CameraManager.CamOpenOverCallback {
    private static final String TAG = "CaptureActivity";
    private CaptureActivityHandler mHandler;
    private ViewfinderView mViewFinderView;
    private boolean mHasSurface;
    private Vector<BarcodeFormat> mDecodeFormats;
    private String mCharacterSet;
    private TextView mTxtResult;
    private InactivityTimer mInactivityTimer;
    private MediaPlayer mMediaPlayer;
    private boolean mPlayBeep;
    private static final float BEEP_VOLUME = 0.10f;
    private boolean vibrate;

    private CameraSurfaceView mSurfaceView;
    private float previewRate = -1f;
    private static final long VIBRATE_DURATION = 200L;
    private static final int SCAN_RESPONSE_COUPON_CODE_SUCCESS = 5;
    private static final int SCAN_RESPONSE_SHOP_DISCOUNT_SUCCESS = 6;// 商家优免
    private static final int SCAN_RESPONSE_COUPON_CODE_FAIL = -1;
    // 闪光灯开关
    private ImageView mFlashBtn;
    // 闪光灯状态
    private boolean isLight = false;
    //开灯  关灯
    private TextView mTxtLight;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture);

        setTabTitle(getString(R.string.scan));
        if (null != mTitle) {
            mTitle.setTextColor(getResources().getColor(R.color.white));
        }
        setBackgroundColor(getResources().getColor(R.color.black));
        setLeftImage(R.drawable.btn_map_close_normal);
        CameraManager.init(getApplication());

        mViewFinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);
        mTxtResult = (TextView) findViewById(R.id.txtResult);
        mTxtResult.setText(R.string.text_scan);
        mHasSurface = false;
        mInactivityTimer = new InactivityTimer(this);

        mFlashBtn = (ImageView) findViewById(R.id.btn_flash_switch);
        mFlashBtn.setOnClickListener(this);

        mTxtLight = (TextView) findViewById(R.id.text_flash_switch);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSurfaceView = (CameraSurfaceView) findViewById(R.id.preview_view);
        SurfaceHolder surfaceHolder = mSurfaceView.getSurfaceHolder();
        if (mHasSurface) {
            initCamera(surfaceHolder);
        } else {
            surfaceHolder.addCallback(this);
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }
        mDecodeFormats = null;
        mCharacterSet = null;

        mPlayBeep = true;
        AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
        if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
            mPlayBeep = false;
        }
        initBeepSound();
        vibrate = true;
        initViewParams();
        if (isLight != CameraManager.isFlashOn()) {
            isLight = CameraManager.isFlashOn();
            if (!isLight) {
                mTxtLight.setText("开灯");
            } else {
                mTxtLight.setText("关灯");
            }
        }
    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        try {
            CameraManager.get().openDriver(surfaceHolder, this);
            if (mHandler == null) {
                mHandler = new CaptureActivityHandler(this, mDecodeFormats,
                        mCharacterSet);
            }
        } catch (Exception e) {
            ToastUtil.showCommonCustomToast(
                    this.getResources()
                            .getString(R.string.cmera_is_not_working),
                    R.drawable.toast_error_icon
            );

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mHandler != null) {
            mHandler.quitSynchronously();
            mHandler = null;
        }
        CameraManager.get().closeDriver();
    }

    @Override
    protected void onDestroy() {
        mInactivityTimer.shutdown();
        super.onDestroy();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!mHasSurface) {
            mHasSurface = true;
            initCamera(holder);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mHasSurface = false;

    }

    public ViewfinderView getViewfinderView() {
        return mViewFinderView;
    }

    public Handler getHandler() {
        return mHandler;
    }

    public void drawViewfinder() {
        mViewFinderView.drawViewfinder();
    }

    public void handleDecode(Result obj, Bitmap barcode) {
        mInactivityTimer.onActivity();
        // viewfinderView.drawResultBitmap(barcode);
        playBeepSoundAndVibrate();
        ToastUtil.showSuccessToast("success");

    }

    private void initBeepSound() {
        if (mPlayBeep && mMediaPlayer == null) {
            // The volume on STREAM_SYSTEM is not adjustable, and users found it
            // too loud,
            // so we now play on the music stream.
            setVolumeControlStream(AudioManager.STREAM_MUSIC);
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mMediaPlayer.setOnCompletionListener(beepListener);

            AssetFileDescriptor file = getResources().openRawResourceFd(
                    R.raw.beep);
            try {
                mMediaPlayer.setDataSource(file.getFileDescriptor(),
                        file.getStartOffset(), file.getLength());
                file.close();
                mMediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
                mMediaPlayer.prepare();
            } catch (IOException e) {
                mMediaPlayer = null;
            }
        }
    }

    private void playBeepSoundAndVibrate() {
        if (mPlayBeep && mMediaPlayer != null) {
            mMediaPlayer.start();
        }
        if (vibrate) {
            Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(VIBRATE_DURATION);
        }
    }

    /**
     * When the beep has finished playing, rewind to queue up another one.
     */
    private final OnCompletionListener beepListener = new OnCompletionListener() {
        public void onCompletion(MediaPlayer mediaPlayer) {
            mediaPlayer.seekTo(0);
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_flash_switch:
                if (isLight) {
                    mTxtLight.setText("开灯");
                    isLight = false;
                } else {
                    mTxtLight.setText("关灯");
                    isLight = true;
                }

                CameraManager.lightSwitch();
                break;

            default:
                break;
        }
    }


    private void initViewParams() {
        LayoutParams params = mSurfaceView.getLayoutParams();
        Point p = DisplayUtil.getScreenMetrics(this);
        params.width = p.x;
        params.height = p.y;
        previewRate = DisplayUtil.getScreenRate(this); // 默认全屏的比例预览
        mSurfaceView.setLayoutParams(params);

    }

    @Override
    public void cameraHasOpened() {
        SurfaceHolder holder = mSurfaceView.getSurfaceHolder();
        CameraManager.get().doStartPreview(holder, previewRate);
    }


}