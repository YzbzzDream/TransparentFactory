package com.transparent.automationfactory.base.util;

import android.text.TextUtils;


import com.transparent.automationfactory.base.storage.PreferenceManager;
import com.transparent.automationfactory.base.storage.PreferenceTools;
import com.transparent.automationfactory.base.storage.PreferencesConstant;



public class UserManager {
    public static final int HEAD_IMG_SIZE = 640;
    public static final int HEAD_SMALL_IMG_SIZE = 320;

    public static double amount = 0;
    public static boolean creditCardBinding = false;
    public static boolean parkingCardBinding = false;
    public static boolean alipayWithholdBinding = false;
    public static int ticketNum = 0;
    public static int lineCarNum = 0;
    public static String money = "";
    public static String cashBack = "";
    public static String CHANGE_CODE = "";

    public static boolean isBindAlipay = false;
    public static boolean isCreditBindByBaiduPay = false;

    private static String userId = null;
    private static String phone = null;

    public static String ALIPAY_BIND_STATUS = "-1";
    private static String sToken = null;

    public static boolean isLogin() {
        return !getUserId().isEmpty() && !getPhone().isEmpty();
    }

    public static String getUserId() {
        if (userId == null || userId.isEmpty()) {
            userId = PreferenceTools.getString(PreferencesConstant.CONFIG_SHARED_PREFERENCES_USER_ID, "");
        }
        return userId;
    }

    public static String getPhone() {
        if (phone == null || phone.isEmpty()) {
            phone = PreferenceTools.getString(PreferencesConstant.CONFIG_SHARED_PREFERENCES_USER_PHONE, "");
        }
        return phone;
    }

    private static void clearUserId() {
        UserManager.userId = "";
        PreferenceTools.putString(PreferencesConstant.CONFIG_SHARED_PREFERENCES_USER_ID, "");
    }

    private static void clearPhone() {
        UserManager.phone = "";
        PreferenceTools.putString(PreferencesConstant.CONFIG_SHARED_PREFERENCES_USER_PHONE, "");
    }

    public static void clearUserInfo() {
        clearUserId();
        clearPhone();
        clearToken();
        PreferenceManager.getInstance().clearUserInfo();
    }

    public static void setUserIdAndPhone(String userId, String phoneNum) {
        phoneNum = phoneNum.replace(" ", "");
        PreferenceTools.putString(PreferencesConstant.CONFIG_SHARED_PREFERENCES_USER_ID, userId);
        PreferenceTools.putString(PreferencesConstant.CONFIG_SHARED_PREFERENCES_USER_PHONE, phoneNum);

    }

    public static String getToken() {
        if (TextUtils.isEmpty(sToken)) {
            sToken = PreferenceTools.getString(PreferencesConstant.CONFIG_SHARED_PREFERENCES_USER_TOKEN, "");
        }
        return sToken;
    }

    private static void clearToken() {
        UserManager.sToken = "";
        PreferenceTools.putString(PreferencesConstant.CONFIG_SHARED_PREFERENCES_USER_TOKEN, "");
    }
}
