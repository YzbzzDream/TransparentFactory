package com.transparent.automationfactory;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;

import com.baidu.mapapi.SDKInitializer;
import com.transparent.automationfactory.base.controls.IntentAction;
import com.transparent.automationfactory.base.controls.IntentUtil;
import com.transparent.automationfactory.base.impl.AUTOImpl;
import com.transparent.automationfactory.base.util.AutomationUtils;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zcq on 2016/8/10.
 */
public class AutomationApplication extends Application{
    public static double sLatitude;
    public static double sLongitude;
    public static String sCity = "";
    public static String sProvince = "";
    private static Handler mainHandler = new Handler(Looper.getMainLooper());

    private Application mApplication;
    public static Context sContext;
    private static WeakReference<Activity> sCurrentActivity = null;

    protected static Map<Integer, WeakReference<Activity>> sCacheActivities;

    protected static AUTOImpl sETCPImpl;
    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
        SDKInitializer.initialize(getApplicationContext());
        mApplication = this;
        initMapImpl();
        init();
    }

    /**
     * 向缓存中添加Activity
     *
     * @param activity
     */
    public static void addActivity(Activity activity) {
        sCurrentActivity = new WeakReference<>(activity);
        if (sCacheActivities != null) {
            int hashCode = activity.hashCode();
            if (sCacheActivities.containsKey(hashCode)) {
                sCacheActivities.remove(hashCode);
            }
            sCacheActivities.put(hashCode, new WeakReference<>(activity));
        }
    }

    /**
     * 从缓存中移除Activity
     *
     * @param activity
     */
    public static void removeActivity(Activity activity) {
        int hashCode = activity.hashCode();
        if (sCacheActivities != null && sCacheActivities.containsKey(hashCode)) {
            sCacheActivities.remove(hashCode);
        }
    }

    /**
     * 获取缓存中的Activity
     *
     * @param hashCode
     * @return
     */
    public Activity getCacheActivity(int hashCode) {
        WeakReference<Activity> weakReference = sCacheActivities.get(hashCode);
        if (weakReference == null) {
            return null;
        }
        return weakReference.get();
    }

    /**
     * 关闭所有Activity
     *
     * @return
     */
    public static int finishAllActivity() {
        int finishCount = 0;
        if (sCacheActivities != null && !sCacheActivities.isEmpty()) {
            List<WeakReference<Activity>> activities = new ArrayList<WeakReference<Activity>>(sCacheActivities.values());
            for (WeakReference<Activity> activity : activities) {
                Activity tempActivity = activity.get();
                if (tempActivity == null) {
                    continue;
                }
                sCacheActivities.remove(tempActivity.hashCode());
                if (sCurrentActivity != null && tempActivity != sCurrentActivity.get()) {
                    if (tempActivity != null && !tempActivity.isFinishing()) {
                        tempActivity.finish();
                        finishCount++;
                    }
                }
            }
        }
        return finishCount;
    }


    private void init() {

        String processName = AutomationUtils.getProcessName(mApplication);
        String packageName = mApplication.getPackageName();
        if (processName.equals(packageName)) {// 防止多进程重复实始化
            sCacheActivities = new LinkedHashMap<>();
            startLocation();
//            JPushInterface.init(this);
        }
    }

    private void startLocation() {

    }
    /**
     * 地图模块提取初始化一些回调
     * <p/>
     * maybe need to overide
     */
    protected void initMapImpl() {

        sETCPImpl = new AUTOImpl() {
            @Override
            public void onETCPLog(String tag, String text) {

            }

            @Override
            public String getUserId() {
                return null;
            }

            @Override
            public String getToken() {
                return null;
            }

            @Override
            public void onExpiredTokenMsg(final Context context, String message) {

            }

            @Override
            public boolean isDebug() {
                return BuildConfig.DEBUG;
            }

            @Override
            public void goMyLoc(Context context, Intent intent) {
                IntentUtil.launch(context, intent, IntentAction.MyLocActivity);
            }
        };

    }

    public static AUTOImpl getsETCPImpl() {
        return sETCPImpl;
    }

    public static Handler getMainHandler() {
        return mainHandler;
    }
}
