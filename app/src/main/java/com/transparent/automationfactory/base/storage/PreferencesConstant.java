package com.transparent.automationfactory.base.storage;

/**
 * 功能：Preferences常量接口
 */
public interface PreferencesConstant {

    /**
     * 项目配置SharedPreferences实体key
     */
    public static final String CONFIG_SHARED_PREFERENCES_ENTITY_KEY = "ConfigEntity";
    /** 项目配置SharedPreferences名称 */
    // public static final String CONFIG_SHARED_PREFERENCES_NAME = "Config";
    /**
     * 帐号
     **/
    public static final String CONFIG_SHARED_PREFERENCES_REMEMBER_PHONE = "userphone";
    /**
     * 密码
     **/
    public static final String CONFIG_SHARED_PREFERENCES_REMEMBER_PASSWORD = "userpassword";

    public static final String CONFIG_SHARED_PREFERENCES_USER_ID = "userid";

    public static final String CONFIG_SHARED_PREFERENCES_USER_PHONE = "phone";

    public static final String CONFIG_SHARED_PREFERENCES_USER_IS_BINDCAR = "isBindCar";

    public static final String CONFIG_JPUSH_DOT_STATUS = "jPushDotStatus";

    /**
     * 用户类型
     **/
    public static final String CONFIG_SHARED_CONFIG_SHARED_PREFERENCES_STAFFTYPE = "staffType";

    /**
     * 首页点击提示
     **/
    public static final String NEARBY_CLICK_NUMBER_KEY = "nearby_click_number";
    public static final String NewVersionGuideRead = "NewVersionGuideRead";


    //是否为全部车场
    public static final String CONFIG_SHARED_PREFERENCES_USER_IS_ALL_PARKING = "isAllParking";

    public static final String VERSION_CODE = "version_code";
    public static final String VERSION_NAME = "version_name";

    public static final String IS_JUMP = "is_jump";

    public static final String ORDER_NUMBER_IN = "order_number_in";

    public static final String ORDER_NUMBER_OUT = "order_number_out";

    String CONFIG_RECEIVER_STATUS = "config_receiver_status";

    String DOWN_LOAD_STATISTIC = "downloadStatistic";

    String RECEIVER_MSG = "receiver_msg";
    String RECEIVER_SMS = "receiver_sms";

    String ETCP_SERVER_URL = "etcp_server_url";
    String OPEN_PLATFORM_URL = "open_platform_url";
    String NEW_PAY_URL = "new_pay_url";
    String ALIPAY_URL = "alipay_url";
    String BSADMIN_URL = "bs_admin_url";

    String OLD_ETCP_SERVER_URL = "old_etcp_server_url";
    String OLD_OPEN_PLATFORM_URL = "old_open_platform_url";
    String OLD_NEW_PAY_URL = "old_new_pay_url";
    String OLD_ALIPAY_URL = "old_alipay_url";
    String OLD_BSADMIN_URL = "old_bs_admin_url";
    String OLD_HOT_PROMOTION_URL = "old_hot_promotion_url";

    String HOT_PROMOTION_URL = "hot_promotion_url";
    public static final String CONFIG_SHARED_PREFERENCES_USER_TOKEN = "user_token";

    String MSG_CENTER_DOT = "MSG_CENTER_DOT";
    String HOT_ACTIVITY_DOT = "HOT_ACTIVITY_DOT";
    String COUPON_DOT = "COUPON_DOT";
    String MSG_MAIL_DOT = "MSG_MAIL_DOT";

    String MACHINE_ORDER_URL = "machine_order_url";
    String MAKE_LOG_FILE_DATE = "make_log_file_date";

    String LS_ON_ACTIVITY_PAUSED_TIMEMILLIS = "ls_on_activity_paused_timemillis";

    String IS_UPLOAD_LOG = "isUploadLog";

    String BUSINESS_SECOND_HAND_CAR = "business_second_hand_car";
    String BUSINESS_REFUELING = "refueling";
    String BUSINESS_INSURANCE = "insurance";

}
