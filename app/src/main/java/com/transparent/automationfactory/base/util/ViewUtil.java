package com.transparent.automationfactory.base.util;

import android.app.Activity;
import android.view.View;

public class ViewUtil {

    public static <T extends View> T findViewById(View view,int resId){
        return (T)view.findViewById(resId);
    }

    public static <T extends View> T findViewById(Activity activity, int resId){
        return (T)activity.findViewById(resId);
    }

}
