package com.transparent.automationfactory.base.storage;

public class PreferenceManager {

    private static final String HOT_SALE_SHOW_TIME = "HotSaleShowTime";
    private static final String USER_CENTER_INFO = "user_center_info";

    private static final String LOGIN_REGISTER_CLICKED = "RegisterOrLoginClicked";
    private static final String FRIST_IN = "first_in";

    private static PreferenceManager instance = new PreferenceManager();

    private PreferenceManager() {

    }

    public static PreferenceManager getInstance() {
        return instance;
    }


    public long getHotSaleShowTime() {
        return PreferenceTools.getLong(HOT_SALE_SHOW_TIME, 0);
    }

    public void setHotSaleShowTime(long time) {
        PreferenceTools.putLong(HOT_SALE_SHOW_TIME, time);
    }

    public String getUserInfo() {
        return PreferenceTools.getString(USER_CENTER_INFO, "");
    }

    public void setUserInfo(String userInfo) {
        PreferenceTools.putString(USER_CENTER_INFO, userInfo);
    }

    public void clearUserInfo() {
        PreferenceTools.clearInfo(USER_CENTER_INFO);
    }

    public boolean getRegisterOrLoginClick() {
        return PreferenceTools.getBoolean(LOGIN_REGISTER_CLICKED, false);
        // return false;
    }

    public void setLoginRegisterClicked(boolean registerClicked) {
        PreferenceTools.putBoolean(LOGIN_REGISTER_CLICKED, registerClicked);
    }

    public boolean getFristIn() {
        return PreferenceTools.getBoolean(FRIST_IN, true);
    }

    public void setFristIn(boolean isFirst) {
        PreferenceTools.putBoolean(FRIST_IN, isFirst);
    }


}
