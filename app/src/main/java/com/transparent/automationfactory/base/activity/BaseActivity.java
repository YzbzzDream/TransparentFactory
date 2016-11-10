package com.transparent.automationfactory.base.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.transparent.automationfactory.AutomationApplication;
import com.transparent.automationfactory.base.common.ObserverManager;
import com.transparent.automationfactory.base.common.UIObserver;
import com.transparent.automationfactory.base.interfaces.IConstants;
import com.transparent.automationfactory.base.storage.PreferencesConstant;
import com.transparent.automationfactory.base.util.DialogUtils;
import com.transparent.automationfactory.base.util.ToastUtil;


public abstract class BaseActivity extends FragmentActivity implements PreferencesConstant, IConstants, UIObserver {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AutomationApplication.addActivity(this);
        registerObserver();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        JPushInterface.onResume(this);
//        MobclickAgent.onResume(this);
    }

    @Override
    protected void onStop() {
        ToastUtil.cancel();
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
//        JPushInterface.onPause(this);
//        MobclickAgent.onPause(this);
    }

//	@Override
//	protected void onRestoreInstanceState(Bundle savedInstanceState) {
//		UserManager.userId = PreferenceTools.getString(this, PreferencesConstant.CONFIG_SHARED_PREFERENCES_USER_ID, "");
//		UserManager.phone = PreferenceTools.getString(this, PreferencesConstant.CONFIG_SHARED_PREFERENCES_USER_PHONE, "");
//
//		super.onRestoreInstanceState(savedInstanceState);
//	}

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AutomationApplication.removeActivity(this);
        unregister();
    }

    @Override
    public void registerObserver() {

    }

    @Override
    public void onReceiverNotify(int action, Object object, int stateCode) {

    }

    public void register(int action) {
        ObserverManager.getInstance().registerObserver(action, this);
    }

    public void unregister() {
        ObserverManager.getInstance().unRegisterObserver(this);
    }

    private Dialog progressDialog = null;

    protected void showProgress() {
        try {
            if (progressDialog == null) {
                progressDialog = DialogUtils.createLoadingDialog(this);
            }
            progressDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void dismissProgress() {
        try {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void superOnStop() {
        super.onStop();
    }
}
