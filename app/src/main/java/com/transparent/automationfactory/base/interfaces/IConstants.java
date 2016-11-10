package com.transparent.automationfactory.base.interfaces;

/**
 * 项目常量接口
 */
public interface IConstants {

    String DEFAULT_ENCODING = "utf-8";

    String PLATFORM = "platform";
    String ANDROID_PLATFORM = "1";

    long ONE_DAY = 1 * 24 * 60 * 60 * 1000;
    int SUCCESS_CODE = 0;
    int ERROR_CODE_NIKE_NAME_USE = 33;
    int CODE_NO_MORE_DATA = 99;


    String REG_FROMS = "2";
    // 本地库路径
    String DB_DIR = "/sdcard/Android/data/com.ETCPOwner.yc/data/";
    // 本地库名称
    String DB_NAME = "ETCP.db";
    // 请求地图列表数据 SECRETKEY
    String PARKING_LIST_SECRETKEY = "f800005dxBx1399ffdmDX20d2mrrzomk";
    // 请求地图列表数据 CLIENTID
    String PARKING_LIST_CLIENTID = "AE6GF30C";

    String PARK_RECORD_INFO_KEY = "ParkRecordInfo";
    String DEBT_ORDER_ID = "DEBT_ORDER_ID";
    String DEBT_PARK_TYPE = "DEBT_PARK_TYPE";

    // 搜索停车场
    int ACTIVITY_REQUEST_SEARCH_CODE = 0;
    String INTENT_SEARCH_PARK_IS_MAP = "search_park_is_map";

    // 请求车场数量数据 SECRETKEY
    String PARKING_COUNT_SECRETKEY = "00a97d7029dde7c2iycit1nfo34jks9a";
    // 请求车场数量数据 CLIENTID
    String PARKING_COUNT_CLIENTID = "AX03D1KG";

    // JSON数据解析
    String JSON_PARSE_CODE_TOKEN = "code";
    String JSON_PARSE_MESSAGE_TOKEN = "message";
    String JSON_PARSE_DATA = "data";
    String JSON_PARSE_ID = "id";
    String JSON_PARSE_DATA_UPPER = "Data";
    String JSON_PARSE_CARID_TOKEN = "carId";
    String JSON_PARSE_PLATE_TOKEN = "plate";
    String JSON_PARSE_BANK_TOKEN = "bank";
    String JSON_PARSE_CARDNO_TOKEN = "carNo";
    String JSON_PARSE_BANKNAME_TOKEN = "bankName";
    String JSON_PARSE_BANK_CARDNO_TOKEN = "cardno";
    String JSON_PARSE_MOBILE_TOKEN = "mobile";
    String JSON_PARSE_CVV_TOKEN = "cvv";
    String JSON_PARSE_EXPIRE_TOKEN = "expire";
    String JSON_PARSE_IDCARD_TOKEN = "idcard";
    String JSON_PARSE_NAME_TOKEN = "name";
    String JSON_PARSE_YZM_TOKEN = "yzm";
    String JSON_PARSE_ORDERNO_TOKEN = "OrderNo";
    String JSON_PARSE_CONSTRAINTS_TOKEN = "constraints";
    String JSON_PARSE_VALUE_TOKEN = "value";

    // 请求参数
    String REQUEST_USERID_LOWER_PARM = "userid";
    String REQUEST_USERID_UPPER_PARM = "userId";
    String REQUEST_CARNUMBER_PARM = "carNumber";
    String REQUEST_CARCOLOR_PARM = "carColour";
    String REQUEST_OPENID_PARM = "openId";
    String REQUEST_PAYAMOUNT = "payAmount";
    String REQUEST_COUPONCODE = "couponCode";
    String REQUEST_COUPONVALUE = "couponValue";
    String REQUEST_PARK_ID_PARM = "parkId";
    String REQUEST_PARK_NAME_PARM = "parkName";
    String REQUEST_PAYWAY_PARM = "payWay";
    String REQUEST_ORDERID_PARM = "orderId";
    String REQUEST_ORDERNO_PARM = "orderNo";
    String REQUEST_SYNID_PARM = "synId";
    String REQUEST_LAT_PARM = "lat";
    String REQUEST_LON_PARM = "lon";
    String REQUEST_CARDNO_PARM = "cardNo";
    String REQUEST_SERETKEY_PRAM = "secretKey";
    String REQUEST_CLIENTID_PRAM = "clientId";
    String REQUEST_STARTIINDEX_PRAM = "startIndex";
    String REQUEST_PAGESIZE_PRAM = "pageSize";
    String REQUEST_DISTANCE_PRAM = "distance";
    String REQUEST_PHONE_PRAM = "phone";
    String REQUEST_LAT = "lat";
    String REQUEST_LNG = "lng";
    String REQUEST_AUTHCODE_PRAM = "authCode";
    String REQUEST_CHANNEL_PRAM = "channel";
    String REQUEST_REGFROMS_PRAM = "regFroms";
    String REQUEST_MY_LON_PRAM = "myLon";
    String REQUEST_MY_LAT_PRAM = "myLat";
    String REQUEST_CHANNEL = "channel";


    String HEAD_IMG_STR = "headImgStr";
    String HEAD_IMG = "headImg";
    String NICK_NAME = "nickname";
    String SEX = "sex";
    String BIRTHDAY = "birthday";
    String TOTAL_INTEGRAL = "totalIntegral";

    String CUR_PAGE = "curPage";
    String PAGE_COUNT = "pageCount";
    String CREATE_AT = "createAt";
    String INTEGRAL = "integral";
    String INTEGRAL_DESC = "integralDesc";
    String INTEGRAL_SOURCE = "integralSource";
    String STATUS = "status";

    String TITLE = "title";

    String LAT = "lat";
    String LON = "lon";
    String INFO = "info";
    String CARD = "card";
    String EXPIRE = "expire";
    String CVV2 = "cvv2";
    String NAME = "name";
    String TIME = "time";
    String IDCARD = "idcard";
    String PROMOTION_TYPE = "promotion_type";
    String TYPE = "type";
    String BANKABBRE = "bankAbbreviation";
    String CARDNUMBER = "cardNumber";
    String CARDID = "cardId";
    String URL = "url";
    String ACCOUNT = "account";
    String SOURCE = "source";
    String USERAGENT = "userAgent";
    String AUTOPAY = "autoPay";
    String VERSION = "version";

    String BANNER_IMGVERSION = "bannerImgVersion";
    String ADIMGERSION = "adImgVersion";


    String AMOUNT = "amount";
    String CREDITCARDBINDING = "creditCardBinding";
    String PARKINGCARDBINDING = "parkingCardBinding";
    String LINECARNUM = "lineCarNum";
    String REGISTERFROM = "registerFrom";
    String VALUEDATA = "valueData";
    String DRIVELICENSE = "drivingLicense";
    String PLATEPIC = "platePic";
    String NOTIFYURL = "notifyUrl";
    String PID = "pid";
    String ISBIND = "isBind";
    String CARPLATE = "carPlate";
    String CARID = "carId";
    String IMAGETYPE = "imageType";
    String IMAGE = "image";
    String IMAGEURL = "imageUrl";
    String CAROWNERNAME = "carOwnerName";
    String CARFRAMENUM = "carFrameNum";
    String CARENGINENUM = "carEngineNum";
    String CARBRAND = "carBrand";
    String PARKING_NAME = "parking_name";
    String PARKINGINFO = "parkingInfo";
    String START_LON = "start_lontitude";
    String START_LAT = "start_latitude";
    String END_LON = "end_lontitude";
    String END_LAT = "end_latitude";
    String SEARCH_PARKING_NAME = "search_park_name";
    String SEARCH_DATA = "search_data";
    String ADDTICKET = "addticket";

    String MAINPATH = "ETCP";

    String BUSINESS_ICON_DATA_FILE = "businessIcon.dat";

    String AD_DATA_FILE = "adimg.dat";
    String BANNER_DATA_FILE = "bannerimg.dat";

    String AD_IAMGE_FILE_NAME = "adimage";
    String DOWNLOAD_IMAGE_PATH = "/ETCP/Download/";

    String CODE = "code";
    String CONSTRAINTS = "constraints";
    String VALUE = "value";

    String CONSTRAINTS_NORMAL = "0";
    String CONSTRAINTS_ONLY_FOR_CREDITCRARD = "1";
    String CONSTRAINTS_ONLY_FOR_ROADSIDE = "2";
    String CONSTRAINTS_ONLY_FOR_TURNSTILE = "4";
    String CONSTRAINTS_NO = "6";

    String REQUEST_M_LENGTH = "mlength";
    String REQUEST_LAST_ID = "lastId";
    String REQUEST_E_LENGTH = "elength";

    String COUPON_TYPE_UNIT_HOUR = "3";

    String REQUEST_WEATHER_INFO_PROVINCE = "province";
    String REQUEST_WEATHER_INFO_CITY = "city";
    String REQUEST_WEATHER_INFO_DISTRICT = "district";

    String REQUEST_LENGTH = "length";

    String REQUEST_LAST_CODE = "lastCode";

    String SUGGESTION_KEYWORD = "keyWord";

    /**
     * BEGIN 发票相关
     */
    int INVOICE_SUCCESS_CODE = 0;
    String REQUEST_INVOICE_UID = "uId";
    String REQUEST_INVOICE_BILL_ID = "billId";
    String REQUEST_INVOICE_UNAME = "uName";
    String REQUEST_INVOICE_UPHONE = "uPhone";
    String REQUEST_INVOICE_UTITLE = "uTitle";
    String REQUEST_INVOICE_PROVINCE = "province";
    String REQUEST_INVOICE_ADDRESS = "address";
    String REQUEST_INVOICE_MAXPRICE = "maxPrice";
    String REQUEST_INVOICE_PRICE = "price";
    String REQUEST_INVOICE_DELIVERY_FRIM = "deliveryFrim";
    String REQUEST_INVOICE_DELIVERY_ID = "deliveryId";
    String REQUEST_INVOICE_DELIVERY_PRICE = "deliveryPrice";
    String REQUEST_INVOICE_TYPE = "type";
    String REQUEST_INVOICE_OP_NAME = "opName";
    /**
     * END 发票相关
     */

    String REQUEST_C_LENGTH = "clength";

    String REQUEST_CHECK_ID = "checkId";

    String REQUEST_PARKING_CITY = "cityName";

    String REQUEST_PARK_TYPE = "parkType";
    String REQUEST_RECORD_SYNID = "recordSynId";
    String REQUEST_RECEIVABLE_FEE = "receivableFee";
    String REQUEST_HB_ACCOUNT = "hbAccount";
    String REQUEST_BALANCE_PAYMENT = "balancePayment";
    String REQUEST_PARK_COUPON_TIME = "parkcouponTime";
    String REQUEST_PARK_COUPON_FREE = "parkcouponFree";
    String REQUEST_COUPON_ID = "couponId";
    String REQUEST_INFORMATION = "information";
    String REQUEST_ENTRANCE_TIME = "entranceTime";
    String REQUEST_PARK_NAME = "parkingName";

    String REQUEST_USERID_BY_CREDIT_CARD = REQUEST_USERID_UPPER_PARM;
    String REQUEST_PHONE_BY_CREDIT_CARD = REQUEST_PHONE_PRAM;

    String REQUEST_NEW_CAR_NUMBER = "newCarNumber";
    String REQUEST_AUTH_ID = "authId";

    String REQUEST_VALIDATE = "validate";

    /* Setting Begin*/
    String REQUEST_SETTING_MSG_TYPE = "msgType";
    String REQUEST_SETTING_SEND_STATE = "sendState";
    String REQUEST_SETTING_SMS = "sms";
    String REQUEST_SETTING_JPUSH = "jpush";
    /* Setting End*/

    /* Token */
    String REQUEST_USER_TOKEN = "token";
    String REQUEST_USER_TS = "ts";
    String REQUEST_USER_ACVALIDATE = "acvalidate";
    /* Token */

    String ETCP_LOG_TAG = "ETCPLog";
    String ETCP_LOG_DIR = "etcpLog";

    String REQUEST_COUPON_FEE = "couponFee";
    String REQUEST_BALANCE = "balance";
    String REQUEST_SERVICECARD = "serviceCard";
    String REQUEST_ONLINE_PAY = "onlinePay";

    String SERVICE_CARDTYPE_ID = "serviceCardTypeId";
    String REQUEST_CD_KEY = "cdkey";

    String REQUEST_CENT_LAT = "centlat";
    String REQUEST_CENT_LON = "centlon";

    String REQUEST_LOW_LAT = "lowlat";
    String REQUEST_LOW_LON = "lowlon";
    String REQUEST_HIG_LAT = "higlat";
    String REQUEST_HIG_LON = "higlon";

    String REG_DATE = "regdate";
    String MOBILE_PHONE = "mobilePhone";
}
