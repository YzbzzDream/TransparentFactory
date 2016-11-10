package com.transparent.automationfactory.base.fragment;

import android.app.Dialog;
import android.support.v4.app.Fragment;

import com.transparent.automationfactory.base.util.DialogUtils;


public class BaseFragment extends Fragment {

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
    public void onDestroy() {
        super.onDestroy();

    }
}
