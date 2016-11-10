package com.transparent.automationfactory.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.transparent.automationfactory.MainActivity;
import com.transparent.automationfactory.R;
import com.transparent.automationfactory.base.activity.BaseTitleBarActivity;
import com.transparent.automationfactory.base.api.IAsyncHttpResponseHandler;
import com.transparent.automationfactory.base.interfaces.IConstants;
import com.transparent.automationfactory.base.storage.PreferenceTools;
import com.transparent.automationfactory.base.storage.PreferencesConstant;
import com.transparent.automationfactory.base.util.AutomationUtils;
import com.transparent.automationfactory.base.util.CheckNetwork;
import com.transparent.automationfactory.base.util.DialogUtils;
import com.transparent.automationfactory.base.util.NetInfoParams;
import com.transparent.automationfactory.base.util.ToastUtil;
import com.transparent.automationfactory.base.util.UserManager;

import org.json.JSONObject;

import java.util.LinkedHashMap;

public class SettingActivity extends BaseTitleBarActivity implements OnClickListener {
    private TextView mTvVersion;
    private RelativeLayout mRlCheckUp;
    private RelativeLayout mRlAbout;
    private LinearLayout mLlLogout;

    private ImageView ivMsg;
    private ImageView ivSms;


    private static final String ITEM_SEND = "1";
    private static final String ITEM_NO_SEND = "0";

    private static boolean IS_RECEIVER_SMS = true;
    private static boolean IS_RECEIVER_MSG = true;

    private static final int NO_SETTING_STATUS_CODE = 99;
    private RelativeLayout mRlFeedBack;
    private View setting_rl_help;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_layout);
        getWindow().setBackgroundDrawable(null);
        setTabTitle(getString(R.string.setting));

        initView();
        registerListener();
        loadDataAndShowUI();

        setStatus();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getReceiverStatus();
    }

    private void setStatus() {
        IS_RECEIVER_MSG = PreferenceTools.getBoolean(PreferencesConstant.RECEIVER_MSG, true);
        IS_RECEIVER_SMS = PreferenceTools.getBoolean(PreferencesConstant.RECEIVER_SMS, true);

        ivMsg.setBackgroundResource(IS_RECEIVER_MSG ? R.drawable.ios7_switch_on : R.drawable.ios7_switch_off);
        ivSms.setBackgroundResource(IS_RECEIVER_SMS ? R.drawable.ios7_switch_on : R.drawable.ios7_switch_off);
    }

    private void getReceiverStatus() {
        showProgress();
//        boolean ret = RequestUtil.getReceiverStatus(mContext, new IAsyncHttpResponseHandler() {
//            @Override
//            public void onSuccess(String result) {
//                dismissProgress();
//                if (!TextUtils.isEmpty(result)) {
//                    try {
//                        JSONObject resultObject = new JSONObject(result);
//                        if (resultObject != null) {
//                            int code = resultObject.optInt(JSON_PARSE_CODE_TOKEN);
//                            if (code == SUCCESS_CODE) {
//                                JSONObject dataObject = resultObject.optJSONObject(JSON_PARSE_DATA);
//                                if (dataObject != null) {
//                                    String sendJpush = dataObject.optString("sendJpush");
//                                    String sendSms = dataObject.optString("sendSms");
//                                    IS_RECEIVER_MSG = (TextUtils.isEmpty(sendJpush) || ITEM_SEND.equals(sendJpush));
//                                    IS_RECEIVER_SMS = (TextUtils.isEmpty(sendSms) || ITEM_SEND.equals(sendSms));
//                                }
//                            } else if (code == NO_SETTING_STATUS_CODE) {
//                                IS_RECEIVER_SMS = true;
//                                IS_RECEIVER_MSG = true;
//                            } else {
//                                ToastUtil.showErrorToast(resultObject.optString(JSON_PARSE_MESSAGE_TOKEN));
//                            }
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                        ToastUtil.showErrorToast(R.string.request_error_msg);
//                    }
//                    PreferenceTools.putBoolean(PreferencesConstant.RECEIVER_MSG, IS_RECEIVER_MSG);
//                    PreferenceTools.putBoolean(PreferencesConstant.RECEIVER_SMS, IS_RECEIVER_SMS);
//
//                    ivMsg.setBackgroundResource(IS_RECEIVER_MSG ? R.drawable.ios7_switch_on : R.drawable.ios7_switch_off);
//                    ivSms.setBackgroundResource(IS_RECEIVER_SMS ? R.drawable.ios7_switch_on : R.drawable.ios7_switch_off);
//                }
//            }
//
//            @Override
//            public void onFailure(int code, Object obj, Throwable e) {
//                dismissProgress();
//                ToastUtil.showErrorToast(getString(R.string.request_error_msg, code));
//            }
//        });
//        if (!ret) {
//            dismissProgress();
//        }
        dismissProgress();
    }

    private void initView() {
        mTvVersion = (TextView) findViewById(R.id.setting_tv_version);
        mRlAbout = (RelativeLayout) findViewById(R.id.setting_rl_about);
        mRlCheckUp = (RelativeLayout) findViewById(R.id.setting_rl_checkup);
        mRlFeedBack = (RelativeLayout) findViewById(R.id.setting_rl_feedback);
        setting_rl_help = findViewById(R.id.setting_rl_help);
        mLlLogout = (LinearLayout) findViewById(R.id.setting_ll_logout);

        ivMsg = (ImageView) findViewById(R.id.iv_msg);
        ivSms = (ImageView) findViewById(R.id.iv_sms);
    }

    private void registerListener() {
        mRlAbout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
//                String url = UrlConfig.PARK_ETCP_BASE_WEBURL + "about_us";
//                String title = getString(R.string.about_us);
//                UrlUtils.gotoBrowser(SettingActivity.this, UrlUtils.WEB_TYPE_ABOUT_US, url, title);
            }
        });
        mRlCheckUp.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // 判断当前网络是否可用
                if (!NetInfoParams.getInstacne(SettingActivity.this).isAvailable()) {
                    ToastUtil.showErrorToast(getString(R.string.no_network));
                    return;
                }
                checkUp();
            }
        });

        mRlFeedBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(v.getContext(), ServicePhoneActivity.class);
//                v.getContext().startActivity(intent);
            }
        });

        setting_rl_help.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });


        mLlLogout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtils.showDialog(SettingActivity.this,
                        R.drawable.delete_icon_exclamation,
                        getString(R.string.setting_logout_dialog_message),
                        getString(R.string.cancel_text),
                        getString(R.string.ok_text), true,
                        new DialogUtils.ITwoButtonListener() {
                            @Override
                            public void onBtnOkClickListener(Dialog dialog) {

                                dialog.cancel();
//                                AppImageManager.clearCache();
                                UserManager.clearUserInfo();
                                clearJPush();
                                Intent intent = new Intent(
                                        SettingActivity.this,
                                        LoginPhoneActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            }

                            @Override
                            public void onBtnCancelClickListener(Dialog dialog) {
                                dialog.cancel();
                            }
                        });
            }
        });

        ivMsg.setOnClickListener(this);
        ivSms.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.iv_msg:
                IS_RECEIVER_MSG = !IS_RECEIVER_MSG;
                setStatus(!IS_RECEIVER_MSG, IConstants.REQUEST_SETTING_JPUSH, getString(R.string.setting_close_msg));
                ivMsg.setBackgroundResource(IS_RECEIVER_MSG ? R.drawable.ios7_switch_on : R.drawable.ios7_switch_off);
                break;
            case R.id.iv_sms:
                IS_RECEIVER_SMS = !IS_RECEIVER_SMS;
                setStatus(!IS_RECEIVER_SMS, IConstants.REQUEST_SETTING_SMS, getString(R.string.setting_close_sms));
                ivSms.setBackgroundResource(IS_RECEIVER_SMS ? R.drawable.ios7_switch_on : R.drawable.ios7_switch_off);
                break;
            default:
                break;
        }
    }

    public void setStatus(final boolean isSend, final String msgType, String title) {
        final String sendState = isSend ? ITEM_SEND : ITEM_NO_SEND;
        setReceiverStatus(msgType, sendState);
    }

    private void setReceiverStatus(final String msgType, final String sendState) {
//        showProgress();
//        RequestUtil.setReceiverStatus(mContext, msgType, sendState, new IAsyncHttpResponseHandler() {
//            @Override
//            public void onSuccess(String result) {
//                try {
//                    dismissProgress();
//                    JSONObject resultObject = new JSONObject(result);
//                    if (resultObject != null) {
//                        int code = resultObject.optInt(JSON_PARSE_CODE_TOKEN);
//                        if (code == SUCCESS_CODE) {
//                            boolean isNoSend = sendState.equals(ITEM_NO_SEND);
//                            if (msgType == REQUEST_SETTING_JPUSH) {
//                                IS_RECEIVER_MSG = !isNoSend;
//                            } else if (msgType == REQUEST_SETTING_SMS) {
//                                IS_RECEIVER_SMS = !isNoSend;
//                            }
//
//                        } else {
//                            ToastUtil.showErrorToast(resultObject.optString(JSON_PARSE_MESSAGE_TOKEN));
//                        }
//                    } else {
//                        ToastUtil.showErrorToast(R.string.setting_error_msg);
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    ToastUtil.showErrorToast(R.string.setting_error_msg);
//                }
//
//                PreferenceTools.putBoolean(PreferencesConstant.RECEIVER_MSG, IS_RECEIVER_MSG);
//                PreferenceTools.putBoolean(PreferencesConstant.RECEIVER_SMS, IS_RECEIVER_SMS);
//
//                ivMsg.setBackgroundResource(IS_RECEIVER_MSG ? R.drawable.ios7_switch_on : R.drawable.ios7_switch_off);
//                ivSms.setBackgroundResource(IS_RECEIVER_SMS ? R.drawable.ios7_switch_on : R.drawable.ios7_switch_off);
//            }
//
//            @Override
//            public void onFailure(int code, Object obj, Throwable e) {
//                dismissProgress();
//                ToastUtil.showErrorToast(getString(R.string.setting_error_msg) + getString(R.string.error_code_format, code));
//            }
//        });
//        dismissProgress();
    }

    private void clearJPush() {
        try {
//            JPushInterface.setAlias(this, "",
//                    new TagAliasCallback() {
//                        @Override
//                        public void gotResult(int arg0, String arg1, Set<String> arg2) {
//                        }
//                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadDataAndShowUI() {
        mTvVersion.setText(AutomationUtils.getShowVersionName());
//        if (null != MainActivity.mUpdateQueryEntity && 0 == MainActivity.mUpdateQueryEntity.getCode()
//                && null != MainActivity.mUpdateQueryEntity.getData()) {
//            if ("2".equals(MainActivity.mUpdateQueryEntity.getData().getType())
//                    || "1".equals(MainActivity.mUpdateQueryEntity.getData().getType())) {
//                mTvVersion.setText("有新版本更新");
//            }
//        }
    }


    private void checkUp() {
        if (!CheckNetwork.isNetworkConnected(SettingActivity.this)) {
            ToastUtil.showErrorToast(R.string.net_error);
            return;
        }
        appsettingsUpdateQuery();
    }

    private void appsettingsUpdateQuery() {
//        Toast.makeText(this,R.string.latest_version, Toast.LENGTH_LONG).show();
        ToastUtil.showSuccessToast(R.string.latest_version);
//        showProgress();
//        LinkedHashMap<String, String> params = new LinkedHashMap<String, String>();
//        params.put(IConstants.PLATFORM, IConstants.ANDROID_PLATFORM);
//        params.put("platVersion", AutomationUtils.getVersionName());

//        ETCPHttpUtils.doGet(this,
//                UrlConfig.UPDATE_QUERY_URL, params, new IAsyncHttpResponseHandler() {
//                    @Override
//                    public void onSuccess(String result) {
//                        dismissProgress();
//                        //result =  "{\"code\":0,\"data\":{\"function\":\"最新版本的\\n安卓版本\",\"id\":28,\"platform\":1,\"status\":\"1\",\"type\":2,\"url\":\"http://www.etcp.cn/product/etcpParking#\",\"version\":\"3.0.8\"},\"message\":\"成功\"}";
//                        try {
//                            Gson gson = new Gson();
//                            MainActivity.mUpdateQueryEntity = gson.fromJson(result,
//                                    new TypeToken<UpdateQueryEntity>() {
//                                    }.getType());
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                            ToastUtil.showCommonCustomToast(
//                                    getString(R.string.error_data),
//                                    R.drawable.toast_error_icon);
//                            return;
//                        }
//
//                        if (null != MainActivity.mUpdateQueryEntity && 0 == MainActivity.mUpdateQueryEntity.getCode()
//                                && null != MainActivity.mUpdateQueryEntity.getData() &&
//                                ("2".equals(MainActivity.mUpdateQueryEntity.getData().getType())
//                                        || "1".equals(MainActivity.mUpdateQueryEntity.getData().getType()))) {
//                            UploadDialog.newInstanceByUpdateQueryEntity(MainActivity.mUpdateQueryEntity, true).show(SettingActivity.this);
//                        } else {
//                            ToastUtil
//                                    .showCommonCustomToast(
//                                            getString(R.string.setting_checkup_status_no_message),
//                                            R.drawable.toast_right_icon);
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(int code, Object obj, Throwable e) {
//                        dismissProgress();
//                        ToastUtil.showCommonCustomToast(
//                                getString(R.string.request_error_msg, code),
//                                R.drawable.toast_error_icon);
//                    }
//                });
//        dismissProgress();
    }
}
