package com.transparent.automationfactory.base.controls;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

public class IntentUtil {


    /**
     * 不带数据的显式跳转
     *
     * @param activity
     * @param clazz
     */
    public static void launch(Context activity, Class<? extends Activity> clazz) {
        activity.startActivity(new Intent(activity, clazz));
    }


    /**
     * 带数据的显式跳转，定义intent的时候，只需要定义
     *
     * @param activity
     * @param intent
     * @param clazz
     */
    public static void launch(Context activity, Intent intent, Class<? extends Activity> clazz) {
        intent.setClass(activity, clazz);
        activity.startActivity(intent);
    }

    /**
     * 不带数据的隐式跳转
     *
     * @param activity
     * @param action   从IntentAction里面找
     */
    public static void launch(Context activity, String action) {
        activity.startActivity(new Intent(action));
    }

    /**
     * 带数据的隐式跳转
     *
     * @param activity
     * @param intent
     * @param action   从IntentAction里面找
     */
    public static void launch(Context activity, Intent intent, String action) {
        intent.setAction(action);
        activity.startActivity(intent);
    }

}
