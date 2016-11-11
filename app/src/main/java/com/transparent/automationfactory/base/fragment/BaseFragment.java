package com.transparent.automationfactory.base.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.transparent.automationfactory.base.util.DialogUtils;


public abstract class BaseFragment extends Fragment {

    protected View mRootView;

    protected Activity mActivity;
    protected Context mContext;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
        mContext = getContext();

        initData(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (null == mRootView) {
            mRootView = getContentView(inflater, container, savedInstanceState);
        }
        return mRootView;
    }

    public abstract void initData(Bundle savedInstanceState);

    public abstract View getContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);


    private Dialog progressDialog = null;

    protected void showProgress() {
        try {
            if (progressDialog == null) {
                progressDialog = DialogUtils.createLoadingDialog(getContext());
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ViewGroup parent = (ViewGroup) mRootView.getParent();
        if (parent != null) {
            parent.removeView(mRootView);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
