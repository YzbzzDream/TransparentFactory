package com.transparent.automationfactory.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.transparent.automationfactory.MainActivity;
import com.transparent.automationfactory.R;
import com.transparent.automationfactory.base.activity.BaseActivity;
import com.transparent.automationfactory.base.activity.PermisssionsManager;
import com.transparent.automationfactory.base.util.DialogUtils;
import com.transparent.automationfactory.base.util.UserManager;

public class LogoActivity extends BaseActivity implements OnClickListener {

    private ImageView mLogoImageView;
    private Animation mFirstAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);
        if (!PermisssionsManager.grantedLocation() || !PermisssionsManager.grantedExternalStorage()) {
            PermisssionsManager.requestPermissions(LogoActivity.this,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE);
        } else {
            setContentView();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void setContentView() {
        mFirstAnimation = AnimationUtils.loadAnimation(this, R.anim.main_logo_anmi_first);
        mLogoImageView = (ImageView) findViewById(R.id.logo_image);
        mLogoImageView.setOnClickListener(this);

        mLogoImageView.startAnimation(mFirstAnimation);
        mFirstAnimation.setAnimationListener(animationListener);
        mLogoImageView.setClickable(false);
        new CountDownTimer(1500, 500) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                mLogoImageView.setClickable(true);
            }
        }.start();
    }

    private AnimationListener animationListener = new AnimationListener() {
        @Override
        public void onAnimationEnd(Animation animation) {
            if (animation.equals(mFirstAnimation)) {
                switchActivity();
            }
        }

        @Override
        public void onAnimationStart(Animation animation) {
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void switchActivity() {
         if (!TextUtils.isEmpty(UserManager.getUserId())) {
            startActivity(new Intent(LogoActivity.this, HomeActivity.class));
            LogoActivity.this.finish();
        } else {
             startActivity(new Intent(LogoActivity.this, LoginPhoneActivity.class));
             LogoActivity.this.finish();
        }
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (!PermisssionsManager.grantedExternalStorage()) {
            showConfirmGrantedExternalStoragePermisssions();
        } else {
            setContentView();
        }
    }

    private static boolean bFirstShow = true;

    private void showConfirmGrantedExternalStoragePermisssions() {
        final Dialog dialog = DialogUtils.createCustomDialog(LogoActivity.this,
                R.layout.dialog_with_ok_and_cancel_layout,
                R.drawable.delete_icon_exclamation,
                (bFirstShow) ? "没有读写权限无法使用" : "未能取得文件权限时无法使用",
                "退出",
                "重新授权",
                false, new DialogUtils.ITwoButtonListener() {
                    @Override
                    public void onBtnOkClickListener(Dialog dialog) {
                        dialog.dismiss();
                        PermisssionsManager.requestPermissions(LogoActivity.this,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    }

                    @Override
                    public void onBtnCancelClickListener(Dialog dialog) {
                        dialog.dismiss();
                        finish();
                    }
                });
        bFirstShow = false;
        dialog.show();
    }
}
