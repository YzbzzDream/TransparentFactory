package com.transparent.automationfactory.base.app;


import com.transparent.automationfactory.AutomationApplication;

public class AutomationSetting {
    public static final boolean IS_DEBUG = (null== AutomationApplication.getsETCPImpl()?false:AutomationApplication.getsETCPImpl().isDebug());
    public static final boolean CHECK_LEAK = false;

    public static final String IS_FROM_LOGIN = "is_from_login";


    public enum ParkType {
        All, Payment

    }

    public enum TrafficStatus {
        Disable, Enable
    }


}
