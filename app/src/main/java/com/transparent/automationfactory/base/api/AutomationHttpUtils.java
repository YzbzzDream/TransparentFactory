package com.transparent.automationfactory.base.api;


import android.content.Context;
import android.text.TextUtils;

import com.loopj.android.http.BinaryHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.squareup.okhttp.Request;
import com.transparent.automationfactory.AutomationApplication;
import com.transparent.automationfactory.base.app.AutomationSetting;
import com.transparent.automationfactory.base.interfaces.IConstants;
import com.transparent.automationfactory.base.network.okhttp.OKHttpManager;
import com.transparent.automationfactory.base.network.okhttp.request.OKHttpGetRequest;
import com.transparent.automationfactory.base.network.okhttp.request.OKHttpPostRequest;
import com.transparent.automationfactory.base.network.okhttp.request.OKHttpRequest;
import com.transparent.automationfactory.base.network.okhttp.response.StringResponseHandler;
import com.transparent.automationfactory.base.util.AutomationUtils;
import com.transparent.automationfactory.base.util.MD5Utils;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import com.loopj.android.http.AsyncHttpClient;
import java.util.concurrent.TimeUnit;


public class AutomationHttpUtils {

    private static final long SOCKET_TIMEOUT_DEFAULT = 10_000;
    private static List<String> NEW_SECRET_URLS;

    private static String URL_PREFIX;

    static {
//        URL_PREFIX = UrlConfig.BASE_ETCP_URL + UrlConfig.VERSION;
        NEW_SECRET_URLS = new ArrayList<>();
        NEW_SECRET_URLS.add(URL_PREFIX + "/users/loginOrRegister2");
//        NEW_SECRET_URLS.add(URL_PREFIX + "/sms/" + UrlConfig.VERSION + "/getSmsVoiceCode");
        NEW_SECRET_URLS.add(URL_PREFIX + "/sms/getSmsAuthCodeWithCheck");
    }


    public static final int TYPE_GET = 0;
    public static final int TYPE_POST = 1;

    /**
     * 解析出url参数中的键值对 如 "index.jsp?Action=del&id=123"，解析出Action:del,id:123存入map中
     *
     * @param URL url地址
     * @return url请求参数部分
     */
    public static Map<String, String> URLRequest(String URL) {
        Map<String, String> mapRequest = new HashMap<String, String>();

        String[] arrSplit = null;

        String strUrlParam = TruncateUrlPage(URL);
        if (strUrlParam == null) {
            return mapRequest;
        }
        // 每个键值为一组
        arrSplit = strUrlParam.split("[&]");
        for (String strSplit : arrSplit) {
            String[] arrSplitEqual = null;
            arrSplitEqual = strSplit.split("[=]");

            // 解析出键值
            if (arrSplitEqual.length > 1) {
                // 正确解析
                mapRequest.put(arrSplitEqual[0], arrSplitEqual[1]);

            } else {
                if (arrSplitEqual[0] != "") {
                    // 只有参数没有值，不加入
                    mapRequest.put(arrSplitEqual[0], "");
                }
            }
        }
        return mapRequest;
    }

    /**
     * 去掉url中的路径，留下请求参数部分
     *
     * @param strURL url地址
     * @return url请求参数部分
     */
    private static String TruncateUrlPage(String strURL) {
        String strAllParam = null;
        String[] arrSplit = null;

        strURL = strURL.trim();

        arrSplit = strURL.split("[?]");
        if (strURL.length() > 1) {
            if (arrSplit.length > 1) {
                if (arrSplit[1] != null) {
                    strAllParam = arrSplit[1];
                }
            }
        }

        return strAllParam;
    }

    /**
     * 执行网络请求
     *
     * @param type            请求方法(get/post)
     * @param context         上下文对象
     * @param path            地址
     * @param params          参数
     * @param responseHandler 回调接口
     */
    public static void excute(int type, final Context context, final String path, LinkedHashMap<String, String> params,
                              final IAsyncHttpResponseHandler responseHandler) {

        if (params != null) {
            String userId = "";
            String token = "";
            if (null != AutomationApplication.getsETCPImpl()) {
                userId = AutomationApplication.getsETCPImpl().getUserId();
                token = AutomationApplication.getsETCPImpl().getToken();
            }
            String ts = System.currentTimeMillis() + "";
            String acvalidate = MD5Utils.validate(userId, token, ts);
            params.put(IConstants.REQUEST_USERID_UPPER_PARM, userId);
            params.put(IConstants.REQUEST_USER_TOKEN, token);
            params.put(IConstants.REQUEST_USER_TS, ts);
            params.put(IConstants.REQUEST_USER_ACVALIDATE, acvalidate);
        }

        OKHttpRequest okHttpRequest = null;
        if (type == TYPE_GET) {
            okHttpRequest = createOKHttpGetRequest(path, params);


        } else if (type == TYPE_POST) {
            okHttpRequest = createOKHttpPostRequest(path, params);
        }
        if (okHttpRequest == null) {
            responseHandler.onFailure(-4, "", new Exception("okHttpRequest is null"));
            return;
        }
        if (AutomationSetting.IS_DEBUG) {
            if (params != null) {
                RequestParams requestParams = new RequestParams(params);
            }
        }

        try {
            okHttpRequest.request(new StringResponseHandler() {
                @Override
                public void onUIResponse(String string) {
                    validateToken(context, string, responseHandler);
                }

                @Override
                public void onUIFailure(int code, Request request, Exception e) {
                    responseHandler.onFailure(code, request, e);
                }

                @Override
                public void onUIError(Throwable e) {
                    responseHandler.onFailure(-1, "", e);


                }
            });
        } catch (Exception e) {
            responseHandler.onFailure(-2, "", e);
            e.printStackTrace();
        }
    }


    private static OKHttpGetRequest createOKHttpGetRequest(String path, LinkedHashMap<String, String> params) {
        String userAgentString = getUserAgentString();
        return OKHttpGetRequest.create()
                .addHeader("versionName", AutomationUtils.getVersionName())
                .addHeader(IConstants.PLATFORM, "android")
                .addHeader("User-Agent", userAgentString)
                .url(path)
                .params(params)
                .build();

    }

    public static String getUserAgentString() {
        return String.format("ETCP/%s (%s; Android %s;)",
                AutomationUtils.getVersionName(), AutomationUtils.getSdkVersion(), AutomationUtils.getPhoneName());

    }

    private static OKHttpPostRequest createOKHttpPostRequest(String path, LinkedHashMap<String, String> params) {
        String userAgentString = getUserAgentString();

        return OKHttpPostRequest.create()
                .addHeader("versionName", AutomationUtils.getVersionName())
                .addHeader(IConstants.PLATFORM, "android")
                .addHeader("User-Agent", userAgentString)
                .url(path)
                .params(params)
                .build();

    }

    private static void validateToken(final Context context, String result, final IAsyncHttpResponseHandler responseHandler) {
        if (!TextUtils.isEmpty(result)) {
            try {
                JSONObject resultObject = new JSONObject(result);
                if (resultObject != null) {
                    int code = resultObject.optInt(IConstants.JSON_PARSE_CODE_TOKEN);
                    if (code == 8888) {
                        String message = resultObject.getString(IConstants.JSON_PARSE_MESSAGE_TOKEN);
                        if (TextUtils.isEmpty(message)) {
                            message = "登录已过期，请重新登录";
                        }

                        if (null != AutomationApplication.getsETCPImpl()) {
                            AutomationApplication.getsETCPImpl().onExpiredTokenMsg(context, message);
                        }


                        try {
//                            JPushInterface.setAlias(context, "",
//                                    new TagAliasCallback() {
//                                        @Override
//                                        public void gotResult(int arg0, String arg1, Set<String> arg2) {
//                                        }
//                                    });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        responseHandler.onSuccess(result);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public static void doPost(final Context context, final String path, LinkedHashMap<String, String> params, final IAsyncHttpResponseHandler responseHandler) {
        doPost(context, path, params, responseHandler, "valueData");
    }

    public static void doPost(final Context context, final String path, LinkedHashMap<String, String> params, final IAsyncHttpResponseHandler responseHandler, String md5Param) {
        String validate = getValidate(path, params);
        params.put(md5Param, validate);
        excute(TYPE_POST, context, path, params, responseHandler);
    }

    public static void doGetNoAutoValidate(final Context context, final String path, LinkedHashMap<String, String> params, final IAsyncHttpResponseHandler responseHandler) {

        LinkedHashMap<String, String> paramsUTF8 = new LinkedHashMap<String, String>();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            try {
                paramsUTF8.put(entry.getKey(), entry.getValue());
            } catch (Exception e) {
            }
        }

        excute(TYPE_GET, context, path, paramsUTF8, responseHandler);
    }

    public static void doGetNoAutoValidate(final Context context, final String path, final IAsyncHttpResponseHandler responseHandler) {
        excute(TYPE_GET, context, path, null, responseHandler);
    }

    public static void doPostNoAutoValidate(final Context context, final String path, LinkedHashMap<String, String> params, final IAsyncHttpResponseHandler responseHandler) {
        excute(TYPE_POST, context, path, params, responseHandler);
    }

    public static void doGet(final Context context, final String path, LinkedHashMap<String, String> params,
                             final IAsyncHttpResponseHandler responseHandler) {
        doGet(context, path, params, responseHandler, "valueData");
    }

    public static void doGetNoEncoder(final Context context, final String path, LinkedHashMap<String, String> params,
                                      final IAsyncHttpResponseHandler responseHandler) {
        doGetNoEncoder(context, path, params, responseHandler, "valueData");
    }

    public static void doGet(final String path, LinkedHashMap<String, String> params,
                             final IAsyncHttpResponseHandler responseHandler) {
        doGet(AutomationApplication.sContext, path, params, responseHandler, "valueData");
    }

    public static void doGet(final Context context, final String path, LinkedHashMap<String, String> params,
                             final IAsyncHttpResponseHandler responseHandler, String md5Param) {
        try {

            LinkedHashMap<String, String> paramsUTF8 = new LinkedHashMap<String, String>();

            if (params != null) {
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    paramsUTF8.put(entry.getKey(), URLEncoder.encode(entry.getValue(), "UTF-8"));

                }
            }
            String validate = getValidate(path, paramsUTF8);
            try {
                paramsUTF8.put(md5Param, URLEncoder.encode(validate, "UTF-8"));
            } catch (Exception e) {
                e.printStackTrace();

            }
            excute(TYPE_GET, context, path, paramsUTF8, responseHandler);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public static void doGetNoEncoder(final Context context, final String path, LinkedHashMap<String, String> params,
                                      final IAsyncHttpResponseHandler responseHandler, String md5Param) {

        LinkedHashMap<String, String> paramsUTF8 = new LinkedHashMap<String, String>();

        if (params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                paramsUTF8.put(entry.getKey(), entry.getValue());

            }
        }
        String validate = getValidate(path, paramsUTF8);
        try {
            paramsUTF8.put(md5Param, validate);
        } catch (Exception e) {
            e.printStackTrace();

        }
        excute(TYPE_GET, context, path, paramsUTF8, responseHandler);

    }

    private static String getValidate(String url, HashMap<String, String> hashMap) {
        boolean isContains = false;
        String validate;
        for (String secretUrl : NEW_SECRET_URLS) {
            if (url.contains(secretUrl)) {
                isContains = true;
                break;
            }
        }
        if (isContains) {
            validate = MD5Utils.validate(hashMap);
        } else {
            validate = MD5Utils.validate(hashMap);
        }
        return validate;
    }

    /**
     * @param url 要下载的文件URL
     * @throws Exception
     */
    public static void downloadImageFile(String url, final BinaryHttpResponseHandler hadler) throws Exception {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, hadler);
    }

    /**
     *
     * @param ms socket超时毫秒数
     */
    public static void setSoTimeOut(long ms){
        OKHttpManager.getInstance().getOkHttpClient().setReadTimeout(ms, TimeUnit.MILLISECONDS);
        OKHttpManager.getInstance().getOkHttpClient().setWriteTimeout(ms, TimeUnit.MILLISECONDS);
    }

    /**
     * 恢复默认的socket超时
     */
    public static void restoreSoTimeOut(){
        OKHttpManager.getInstance().getOkHttpClient().setReadTimeout(SOCKET_TIMEOUT_DEFAULT, TimeUnit.MILLISECONDS);
        OKHttpManager.getInstance().getOkHttpClient().setWriteTimeout(SOCKET_TIMEOUT_DEFAULT, TimeUnit.MILLISECONDS);
    }


}
