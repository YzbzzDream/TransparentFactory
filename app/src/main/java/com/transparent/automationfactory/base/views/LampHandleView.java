package com.transparent.automationfactory.base.views;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.transparent.automationfactory.R;

import java.util.Arrays;
import java.util.List;

public class LampHandleView extends RelativeLayout {

    private ImageView ivHandleUp;
    private TextView tvParkName;
    private TextView tvDistance;
    private TextView tvLots;
    private TextView tvFee;

    private LinearLayout llNavitgation;

    private TextView tvOnlinePay;
    private TextView tvTagTime;
    private TextView tvTagUp;
    private TextView tvTagDown;


    public LampHandleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.include_lamp_handle, this);
    }
}