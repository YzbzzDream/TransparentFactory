package com.transparent.automationfactory.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.transparent.automationfactory.R;
import com.transparent.automationfactory.base.fragment.BaseFragment;

/**
 * Created by yzbzz on 2016/11/11.
 */

public class CheckFragment extends BaseFragment {

    private View mView;

    public static CheckFragment newInstance() {
        CheckFragment fragment = new CheckFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public View getContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_check, container, false);
        return mView;
    }
}
