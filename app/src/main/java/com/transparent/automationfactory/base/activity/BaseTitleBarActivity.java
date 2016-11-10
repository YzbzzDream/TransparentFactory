package com.transparent.automationfactory.base.activity;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.transparent.automationfactory.R;
import com.transparent.automationfactory.base.util.DialogUtils;
import com.transparent.automationfactory.base.util.ToastUtil;


public abstract class BaseTitleBarActivity extends BaseActivity {

    public ImageView mLeftImage;
    public ImageView mRightImage;
    public TextView mTitle;
    public TextView mLeftText;
    public TextView mRightText;
    public View mHeader;
    private boolean customTitleSupported = true;
    private Dialog progressDialog = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        customTitleSupported = requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
    }

    private void initTitleBar() {
        if (customTitleSupported) {
            getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar);

            mHeader = findViewById(R.id.header);
            mLeftImage = (ImageView) findViewById(R.id.btn_left_image);
            mRightImage = (ImageView) findViewById(R.id.right_image);
            mTitle = (TextView) findViewById(R.id.title);
            mLeftText = (TextView) findViewById(R.id.left_text);
            mRightText = (TextView) findViewById(R.id.right_text);

            setLeftImage(R.drawable.titlebar_back_icon);
            mLeftImage.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    finish();
                }
            });
        }
    }

    protected void setTabTitle(String title) {
        if (mTitle != null) {
            mTitle.setVisibility(View.VISIBLE);
            mTitle.setText(title);
        }
    }

    protected void setTabTitle(int strId) {
        if (mTitle != null) {
            mTitle.setVisibility(View.VISIBLE);
            mTitle.setText(strId);
        }
    }

    protected void setLeftText(String text) {
        if (mLeftText != null) {
            mLeftText.setVisibility(View.VISIBLE);
            mLeftText.setText(text);
        }
    }

    protected void setLeftText(int strId) {
        if (mLeftText != null) {
            mLeftText.setVisibility(View.VISIBLE);
            mLeftText.setText(strId);
        }
    }

    protected void setLeftImageOnClickListener(OnClickListener listener) {
        if (mLeftImage != null) {
            mLeftImage.setVisibility(View.VISIBLE);
            mLeftImage.setOnClickListener(listener);
        }
    }

    protected void setRightText(String text) {
        if (mRightText != null) {
            mRightText.setVisibility(View.VISIBLE);
            mRightText.setText(text);
        }
    }

    protected void setRightText(String text, int color) {
        if (mRightText != null) {
            mRightText.setVisibility(View.VISIBLE);
            mRightText.setText(text);
            mRightText.setTextColor(color);
        }
    }

    protected void setLeftImage(int res) {
        if (mLeftImage != null) {
            mLeftImage.setVisibility(View.VISIBLE);
            mLeftImage.setImageResource(res);
        }
    }

    protected void setLeftImage(Bitmap bitmap) {
        if (mLeftImage != null) {
            mLeftImage.setVisibility(View.VISIBLE);
            mLeftImage.setImageBitmap(bitmap);
        }
    }

    protected void setRightImage(int res) {
        if (mRightImage != null) {
            mRightImage.setVisibility(View.VISIBLE);
            mRightImage.setImageResource(res);
        }
    }

    protected void setRightImage(Bitmap bitmap) {
        if (mRightImage != null) {
            mRightImage.setVisibility(View.VISIBLE);
            mRightImage.setImageBitmap(bitmap);
        }
    }

    protected void setRightOnClickListener(OnClickListener listener) {
        if (mRightText != null) {
            mRightText.setVisibility(View.VISIBLE);
            mRightText.setOnClickListener(listener);
        }
    }

    protected void setRightImageOnClickListener(OnClickListener listener) {
        if (mRightImage != null) {
            // mRightImage.setVisibility(View.VISIBLE);
            mRightImage.setOnClickListener(listener);
        }
    }

    protected void setBackgroundColor(int color) {
        if (mHeader != null) {
            mHeader.setBackgroundColor(color);
        }
    }

    protected void setBackgroundResource(int resid) {
        if (mHeader != null) {
            mHeader.setBackgroundResource(resid);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        initTitleBar();
    }

    @Override
    public void setContentView(View view, LayoutParams params) {
        super.setContentView(view, params);
        initTitleBar();
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        initTitleBar();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    protected void setRightImageInvisible() {
        if (mRightImage != null) {
            mRightImage.setVisibility(View.INVISIBLE);
        }
    }

    protected void setRightTextVisibility(int visibility) {
        if (mRightText != null) {
            mRightText.setVisibility(visibility);
        }
    }

    protected void setLeftImageVisibility(int visibility) {
        if (mLeftImage != null) {
            mLeftImage.setVisibility(visibility);
        }
    }

    public void onEvent(String var1) {
//        ETCPClickUtil.onEvent(this, var1);
    }

    protected void showHttpError(String errMsg) {
        if (TextUtils.isEmpty(errMsg)) {
            ToastUtil.showNetworkError();
        } else {
            ToastUtil.showErrorToast( errMsg);
        }
    }

    protected void showProgress() {
        try {
            if (progressDialog == null) {
                progressDialog = DialogUtils.createLoadingDialog(this);
            }
            progressDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void dismissProgress() {
        try {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
