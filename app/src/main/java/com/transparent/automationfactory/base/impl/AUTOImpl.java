package com.transparent.automationfactory.base.impl;

import android.content.Context;
import android.content.Intent;

public interface AUTOImpl {
    void onETCPLog(String tag, String text);
    String getUserId();
    String getToken();
    void onExpiredTokenMsg(final Context context, String message);
    boolean isDebug();
    void goMyLoc(Context context, Intent intent);
}
