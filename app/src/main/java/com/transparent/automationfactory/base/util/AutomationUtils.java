package com.transparent.automationfactory.base.util;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.transparent.automationfactory.AutomationApplication;
import com.transparent.automationfactory.base.app.AutomationSetting;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AutomationUtils {

    private static String versionName = "";
    private static String sdkVersion = Build.VERSION.RELEASE;
    private static String phoneName = Build.MODEL;

    public static String getShowVersionName() {
        try {
            return String.format("v%s%s", getVersionName(), (AutomationSetting.IS_DEBUG ? "测试版本" : ""));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getSdkVersion() {
        return sdkVersion;

    }

    public static String getPhoneName() {
        return phoneName;
    }

    public static String getVersionName() {
        if (!TextUtils.isEmpty(versionName)) {
            return versionName;
        }
        try {
            PackageManager manager = AutomationApplication.sContext.getPackageManager();
            PackageInfo info = manager.getPackageInfo(AutomationApplication.sContext.getPackageName(), 0);
            versionName = info.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return versionName;
    }

    /*
     * check the app is installed
     */
    public static boolean isAppInstalled(Context context, String packagename) {
        PackageInfo packageInfo;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(packagename, 0);
        } catch (NameNotFoundException e) {
            packageInfo = null;
            e.printStackTrace();
        }
        if (packageInfo == null) {
            // System.out.println("没有安装");
            return false;
        } else {
            // System.out.println("已经安装");
            return true;
        }
    }

    public static <T> T fromJson(String jsonStr, Class<T> clazz) {
        return JSON.parseObject(jsonStr, clazz);
        // Gson gson = new Gson();
        // return gson.fromJson(jsonStr, type);
    }

    public static String getProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        for (RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return "";
    }

    public static int getAppVersion() {
        try {
            PackageInfo info = AutomationApplication.sContext.getPackageManager().getPackageInfo(AutomationApplication.sContext.getPackageName(), 0);
            return info.versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }



    /**
     * 判断点击的位置是不是在指定的view上
     *
     * @param view
     * @param event
     * @return
     */
    public static boolean inRangeOfView(View view, MotionEvent event) {
        int[] locations = new int[2];

        int x = locations[0];
        int y = locations[1];

        view.getLocationOnScreen(locations);

        int maxWidth = x + view.getWidth();
        int maxHeight = y + view.getHeight();

        boolean result = true;

        //判断当前点是否在view 之内
        if (event.getX() < x || event.getX() > maxWidth || event.getY() > maxHeight || event.getY() < y) {
            result = false;
        }

        return result;
    }

    public static String getDateString(long time) {
        Date date = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm");
        return sdf.format(date);
    }

    public static String getApplicationMetaData(Context context, String key) {
        String data;
        try {
            ApplicationInfo info = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            String msg = info.metaData.getString(key);
            if (TextUtils.isEmpty(msg)) {
                msg = info.metaData.getInt(key) + "";
                if (msg.equals("0")) {
                    msg = "auto";
                }
            }
            data = msg;
        } catch (Exception e) {
            e.printStackTrace();
            data = "";
        }
        return data.trim();
    }

    public static String getChannel(Context context) {
        return getApplicationMetaData(context, "AUTO_CHANNEL");
    }

}
