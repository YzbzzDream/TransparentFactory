package com.transparent.automationfactory.base.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.transparent.automationfactory.R;


public class LampContentView extends LinearLayout {



    public LampContentView(Context context, AttributeSet attr) {
        super(context, attr);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.include_lamp_content, this);
        initView();
    }

    private void initView() {

    }
}
