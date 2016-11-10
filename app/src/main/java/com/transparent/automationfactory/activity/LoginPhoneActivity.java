package com.transparent.automationfactory.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.transparent.automationfactory.R;
import com.transparent.automationfactory.base.activity.BaseTitleBarActivity;
import com.transparent.automationfactory.base.activity.PermisssionsManager;
import com.transparent.automationfactory.base.interfaces.ITextWatcher;
import com.transparent.automationfactory.base.util.ToastUtil;
import com.transparent.automationfactory.base.util.UserManager;
import com.transparent.automationfactory.base.util.VerificationUtil;

import java.util.Timer;
import java.util.TimerTask;


public class LoginPhoneActivity extends BaseTitleBarActivity implements OnClickListener {
    private static final String TAG = LoginPhoneActivity.class.getSimpleName();
    private EditText mEtPhoneNumber;
    private TextView mTvUserAgree;
    private ImageView mIvAgree;

    private final static int PHONE_NUMBER_LENGTH = 11;
    private final static int SPACE_NUMBER = 0;
    private static int EDIT_LENGTH = PHONE_NUMBER_LENGTH + SPACE_NUMBER;

    private LinearLayout mLlPhone;

    private String mPhoneNum = "";
    private boolean mAgree = true;

    private RegisterTimer registerTimer;


    private EditText mEtVerify;
    private Button mBtSendVerify;

    private Button mBtLogin;
    private LinearLayout mLlVerifyBg;

    private static int verifyCodeLen = 4;
    private static final int COUNT_DOWN_TIME = 60 * 1000;

    private View phoneClearIMG;
    private View vcodeClearIMG;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_phone);
        setTabTitle(getResources().getString(R.string.verify_phone_number));
        setLeftImageOnClickListener(null);
        setLeftImage(View.GONE);
        if (!PermisssionsManager.grantedPermiss(Manifest.permission.READ_PHONE_STATE)) {
            PermisssionsManager.requestPermissions(LoginPhoneActivity.this, Manifest.permission.READ_PHONE_STATE);
        }

        initView();
        setView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                mEtPhoneNumber.post(new Runnable() {
                    @Override
                    public void run() {
                        mEtPhoneNumber.setFocusable(true);
                        mEtPhoneNumber.setFocusableInTouchMode(true);
                        mEtPhoneNumber.requestFocus();
                        InputMethodManager inputManager = (InputMethodManager) mEtPhoneNumber.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputManager.showSoftInput(mEtPhoneNumber, 0);
                    }
                });
            }
        }, 500);

    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_user_agree:
                break;
            case R.id.iv_agree:
                switchAgree();
                updateButtonStatus();
                break;
            case R.id.btn_send_verify: {
                onSendVcode();
            }
            break;
            case R.id.bt_login:
                if (mEtVerify.getText().toString().equals("")) {
                    ToastUtil.showCommonCustomToast(R.string.input_verifycode_info_text, R.drawable.toast_error_icon);
                    return;
                }
                LoginRequest();
                break;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void initView() {
        mLlPhone = (LinearLayout) findViewById(R.id.ll_phone);
        mEtPhoneNumber = (EditText) findViewById(R.id.edit_phone_number);
        mEtPhoneNumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                mLlPhone.setBackgroundResource((b) ? R.drawable.shape_white_bg_corners : R.drawable.shape_white_bg_gray_stroke_corners);
                if (b && !TextUtils.isEmpty(mEtPhoneNumber.getText().toString())) {
                    phoneClearIMG.setVisibility(View.VISIBLE);
                } else {
                    phoneClearIMG.setVisibility(View.GONE);
                }

            }
        });


        mIvAgree = (ImageView) findViewById(R.id.iv_agree);
        mTvUserAgree = (TextView) findViewById(R.id.tv_user_agree);

        mEtVerify = (EditText) findViewById(R.id.edit_verification_code);
        mLlVerifyBg = (LinearLayout) findViewById(R.id.ll_verify_bg);

        mBtSendVerify = (Button) findViewById(R.id.btn_send_verify);

        mBtLogin = (Button) findViewById(R.id.bt_login);
        phoneClearIMG = findViewById(R.id.phoneClearIMG);
        vcodeClearIMG = findViewById(R.id.vcodeClearIMG);
    }

    private void setView() {
        mTvUserAgree.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        mTvUserAgree.setOnClickListener(this);

        Phone_Watcher.setWatcher(mEtPhoneNumber, phoneClearIMG);
        mEtPhoneNumber.clearFocus();
        mIvAgree.setOnClickListener(this);
        Verification_Watcher.setWatcher(mEtVerify, vcodeClearIMG);

        mEtVerify.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                mLlVerifyBg.setBackgroundResource((b) ? R.drawable.shape_white_bg_corners : R.drawable.shape_white_bg_gray_stroke_corners);
                if (b && !TextUtils.isEmpty(mEtVerify.getText().toString())) {
                    vcodeClearIMG.setVisibility(View.VISIBLE);
                } else {
                    vcodeClearIMG.setVisibility(View.GONE);
                }
            }
        });

        registerTimer = new RegisterTimer(COUNT_DOWN_TIME, 1000);
//        registerTimer.start();
        mBtSendVerify.setEnabled(false);
        mBtLogin.setOnClickListener(this);
        mBtLogin.setEnabled(false);

        mBtSendVerify.setOnClickListener(this);

        switchAgree();
        mAgree = true;
        updateButtonStatus();

    }

    private void switchAgree() {
        mAgree = !mAgree;
        mIvAgree.setImageResource((mAgree) ? R.drawable.invoice_addressee_true_phone : R.drawable.invoice_addressee_false);
    }

    class RegisterTimer extends CountDownTimer {

        public RegisterTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            mBtSendVerify.setText(R.string.retry_get);
            mBtSendVerify.setEnabled(true);
            mEtPhoneNumber.setEnabled(true);
//            mBtSendVerify.setBackgroundResource(R.drawable.etcp_get_verify_button);
            //mBtSendVerify.setTextColor(getResources().getColor(R.color.color_blue_title));
        }

        @Override
        public void onTick(long millisUntilFinished) {
            mBtSendVerify.setEnabled(false);
            mEtPhoneNumber.setEnabled(false);
            mBtSendVerify.setText(String.format(getResources().getString(R.string.format_getting), millisUntilFinished / 1000));
            if (millisUntilFinished / 1000 == COUNT_DOWN_TIME / 1000 - 20) {

            }
        }
    }


    ITextWatcher Phone_Watcher = new ITextWatcher() {
        private EditText edit;
        private View delView;

        public void setWatcher(final EditText edit, View delView) {
            this.edit = edit;
            this.delView = delView;
            if (null != delView) {
                invalidate();

                this.delView.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        edit.setText("");
                    }
                });
            }
            this.edit.addTextChangedListener(this);
        }


        private void invalidate() {
            // TODO Auto-generated method stub
            if (null != delView) {
                if (0 < edit.getText().length()) {
                    delView.setVisibility(View.VISIBLE);
                } else {
                    delView.setVisibility(View.INVISIBLE);
                }
            }
        }

        private void invalidate(int len) {
            // TODO Auto-generated method stub
            if (null != delView) {
                if (0 < len) {
                    delView.setVisibility(View.VISIBLE);
                } else {
                    delView.setVisibility(View.INVISIBLE);
                }
            }
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            invalidate(null != s ? s.length() : 0);

            if (s == null || s.length() == 0) return;
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < s.length(); i++) {
//                if (i != 3 && i != 8 && s.charAt(i) == ' ') {
//                    continue;
//                } else {
                sb.append(s.charAt(i));
//                if ((sb.length() == 4 || sb.length() == 9) && sb.charAt(sb.length() - 1) != ' ') {
//                    sb.insert(sb.length() - 1, ' ');
//                }
//                }
            }
            try {
                if (!sb.toString().equals(s.toString())) {
                    int index = start + 1;
                    if (sb.charAt(start) == ' ') {
                        if (before == 0) {
                            index++;
                        } else {
                            index--;
                        }
                    } else {
                        if (before == 1) {
                            index--;
                        }
                    }
                    mEtPhoneNumber.setText(sb.toString());

                    mEtPhoneNumber.setSelection(index);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            invalidate(null != s ? s.length() : 0);
        }

        @Override
        public void afterTextChanged(Editable s) {
            mPhoneNum = mEtPhoneNumber.getText().toString();
            mPhoneNum = mPhoneNum.replaceAll(" ", "");
            if (EDIT_LENGTH == s.length() && !VerificationUtil.checkMobile11(mPhoneNum)) {
                ToastUtil.showCommonCustomToast(R.string.input_correct_phone_num, R.drawable.toast_error_icon);
                s.clear();
            }
            invalidate();
            updateButtonStatus();
        }
    };

    ITextWatcher Verification_Watcher = new ITextWatcher() {

        public View delView;
        public EditText edit;

        @Override
        public void setWatcher(final EditText edit, View delView) {
            this.edit = edit;
            this.delView = delView;
            if (null != delView) {
                invalidate();

                this.delView.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        edit.setText("");
                    }
                });
            }
            this.edit.addTextChangedListener(this);

        }

        private void invalidate() {
            // TODO Auto-generated method stub
            if (null != delView) {
                if (0 < edit.getText().length()) {
                    delView.setVisibility(View.VISIBLE);
                } else {
                    delView.setVisibility(View.INVISIBLE);
                }
            }
        }

        private void invalidate(int len) {
            // TODO Auto-generated method stub
            if (null != delView) {
                if (0 < len) {
                    delView.setVisibility(View.VISIBLE);
                } else {
                    delView.setVisibility(View.INVISIBLE);
                }
            }
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            invalidate(null != s ? s.length() : 0);
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            invalidate(null != s ? s.length() : 0);

        }

        @Override
        public void afterTextChanged(Editable s) {
            invalidate();
            updateButtonStatus();
        }
    };

    private void updateButtonStatus() {
        if (mPhoneNum.length() >= 11 && mAgree) {

            mBtSendVerify.setEnabled(true);
        } else {

            mBtSendVerify.setEnabled(false);
        }

        {
            if (mEtVerify.length() == verifyCodeLen && mPhoneNum.length() >= 11 && mAgree) {
                mBtLogin.setEnabled(true);
                mEtVerify.clearFocus();
            } else {
                mBtLogin.setEnabled(false);
            }

        }
    }

    private void onSendVcode() {

    }

    private void LoginRequest() {
        showProgress();
        dismissProgress();
        startActivity(new Intent(this, HomeActivity.class));
        saveUserInfo();
        this.finish();
    }

    private void saveUserInfo() {
        UserManager.setUserIdAndPhone("123456", "13611146591");
    }
}
