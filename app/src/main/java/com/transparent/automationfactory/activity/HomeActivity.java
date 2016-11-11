package com.transparent.automationfactory.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.transparent.automationfactory.R;
import com.transparent.automationfactory.base.activity.BaseTitleBarActivity;
import com.transparent.automationfactory.base.util.ToastUtil;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Created by yzbzz on 16/8/12.
 */
public class HomeActivity extends BaseTitleBarActivity implements View.OnClickListener {
    private boolean isExit;
    private static final int DELAYED_CHANGE_EXIT_FLAG = 0;
    private WeakRefHandler mHandler;

    private ConvenientBanner mConvenientBanner;
    private ArrayList<Integer> localImages = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData(savedInstanceState);
        setContentView(R.layout.fragment_home);
        setLeftImageVisibility(View.GONE);
        mConvenientBanner = (ConvenientBanner) findViewById(R.id.home_banner);
        mHandler = new WeakRefHandler(this);

        mConvenientBanner
                .setPages(new CBViewHolderCreator() {
                    @Override
                    public LocalImageHolderView createHolder() {
                        return new LocalImageHolderView();
                    }
                }, localImages);
        mConvenientBanner.setPointViewVisible(true);
        mConvenientBanner.setManualPageable(true);
        mConvenientBanner.setPageIndicator(new int[]{R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused});
        setViews();
    }

    public void initData(Bundle savedInstanceState) {
        for (int position = 0; position < 7; position++)
            localImages.add(getResId("ic_test_" + position, R.drawable.class));
    }

    public static int getResId(String variableName, Class<?> c) {
        try {
            Field idField = c.getDeclaredField(variableName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    private void setViews() {
        findViewById(R.id.cv_lamp).setOnClickListener(this);
        findViewById(R.id.cv_setting).setOnClickListener(this);
        findViewById(R.id.cv_scan).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cv_lamp:
                startActivity(new Intent(this, LampMapActivity.class));
                break;
            case R.id.cv_setting:
                startActivity(new Intent(this, SettingActivity.class));
                break;
            case R.id.cv_scan:
                Intent intent = new Intent(this, CaptureActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private static class WeakRefHandler extends android.os.Handler {
        WeakReference<HomeActivity> mActivity;

        public WeakRefHandler(HomeActivity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            int what = msg.what;
            if (mActivity.get() == null) {
                return;
            }
            switch (what) {
                case DELAYED_CHANGE_EXIT_FLAG:
                    mActivity.get().setExit();
                    break;
                default:
                    break;
            }
        }

    }

    public void exit() {
        if (!isExit) {
            isExit = true;
            ToastUtil.showSuccessToast(getString(R.string.main_exit_message));
            mHandler.sendEmptyMessageDelayed(DELAYED_CHANGE_EXIT_FLAG, 2000);
        } else {
            finish();
        }
    }

    private void setExit() {
        isExit = false;
    }

    public class LocalImageHolderView implements Holder<Integer> {

        private ImageView imageView;

        @Override
        public View createView(Context context) {
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, int position, Integer data) {
            imageView.setImageResource(data);
        }
    }
}
