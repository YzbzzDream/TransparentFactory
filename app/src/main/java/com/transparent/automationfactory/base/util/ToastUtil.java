package com.transparent.automationfactory.base.util;

import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.transparent.automationfactory.AutomationApplication;
import com.transparent.automationfactory.R;


public class ToastUtil {

    private static Toast sToast = null;
    private static Handler mHandler;

    private static Handler getHandler() {
        if (mHandler == null) {
            mHandler = new Handler(Looper.getMainLooper());
        }
        return mHandler;
    }

    private ToastUtil() {

    }

    public static void showCommonCustomToast(int resId, int imId) {
        if (null == AutomationApplication.sContext) {
            return;
        }
        showCustomToast(AutomationApplication.sContext.getString(resId), imId, Toast.LENGTH_SHORT);
    }

    public static void showCommonCustomToast(String str, int imId) {
        showCustomToast(str, imId, Toast.LENGTH_SHORT);
    }

    public static void showCommonCustomToast(String str, int imId, int duration) {
        showCustomToast(str, imId, duration);
    }

    public static void showNetworkError() {
        if (null == AutomationApplication.sContext) {
            return;
        }
        showCommonCustomToast(AutomationApplication.sContext.getString(R.string.net_error), R.drawable.toast_error_icon);
    }


    private static void showCustomToast(final String msg, final int imgRes, final int duration) {
        if (null == AutomationApplication.sContext) {
            return;
        }
        getHandler().post(new Runnable() {
            public void run() {
                try {
                    LayoutInflater dinflater = LayoutInflater.from(AutomationApplication.sContext);

                    View toastRoot = dinflater.inflate(R.layout.toast_custom, null);
                    if (sToast == null) {
                        sToast = new Toast(AutomationApplication.sContext);
                    }

                    sToast.setView(toastRoot);

                    TextView tv = (TextView) toastRoot.findViewById(R.id.tv_toast);
                    tv.setText(msg);

                    ImageView iv = (ImageView) toastRoot.findViewById(R.id.iv_icon);
                    iv.setImageResource(imgRes);

                    sToast.setGravity(Gravity.CENTER, 0, 0);
                    sToast.setDuration(duration);
                    sToast.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void cancel() {
        try {
            if (sToast != null) {
                sToast.cancel();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showErrorToast(String message) {
        showCustomToast(message, R.drawable.toast_error_icon, Toast.LENGTH_SHORT);
    }

    public static void showErrorToast(int messageId) {
        if (null == AutomationApplication.sContext) {
            return;
        }
        showCustomToast(AutomationApplication.sContext.getString(messageId), R.drawable.toast_error_icon, Toast.LENGTH_SHORT);
    }

    public static void showSuccessToast(String message) {
        showCustomToast(message, R.drawable.toast_right_icon, Toast.LENGTH_SHORT);
    }

    public static void showSuccessToast(int messageId) {
        if (null == AutomationApplication.sContext) {
            return;
        }
        showCustomToast(AutomationApplication.sContext.getString(messageId), R.drawable.toast_right_icon, Toast.LENGTH_SHORT);
    }

    public static void showImageToast(final int imId) {
        if (null == AutomationApplication.sContext) {
            return;
        }
        getHandler().post(new Runnable() {
            public void run() {
                try {
                    LayoutInflater dinflater = LayoutInflater.from(AutomationApplication.sContext);

                    View layout = dinflater.inflate(R.layout.toast_image, null);

                    if (sToast == null) {
                        sToast = new Toast(AutomationApplication.sContext);
                    }

                    ImageView iv = (ImageView) layout.findViewById(R.id.iv_icon);

                    iv.setImageResource(imId);


                    sToast.setView(layout);
                    sToast.setGravity(Gravity.CENTER, 0, 0);
                    sToast.setDuration(Toast.LENGTH_LONG);
                    sToast.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
