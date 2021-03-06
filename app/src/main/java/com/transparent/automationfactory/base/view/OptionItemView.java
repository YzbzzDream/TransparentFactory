/*
 * @项目名称: ETCP停车
 * @文件名称: OptionItemView.java
 * @Copyright: 2016 悦畅科技有限公司. All rights reserved.
 * 注意：本内容仅限于悦畅科技有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */

package com.transparent.automationfactory.base.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.transparent.automationfactory.R;

public class OptionItemView extends LinearLayout {

    private Context mContext;

    private TextView tvTitle;
    private TextView tvContent;
    private ImageView ivIcon;

    private ImageView ivArrow;


    public OptionItemView(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public OptionItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;

        init();

        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomItemView);

        int resId = a.getResourceId(R.styleable.CustomItemView_cimage, -1);
        if (resId > 0) {
            ivIcon.setImageResource(resId);
        } else {
            ivIcon.setVisibility(GONE);
        }

        int mImageScale = a.getInt(R.styleable.CustomItemView_cimageScaleType, -1);
        if (mImageScale > 0) {
            ivIcon.setScaleType(sScaleTypeArray[mImageScale]);
        }

        String title = a.getString(R.styleable.CustomItemView_ctitleText);
        if (!TextUtils.isEmpty(title)) {
            tvTitle.setText(title);
        } else {
            tvTitle.setVisibility(GONE);
        }

        int color = a.getColor(R.styleable.CustomItemView_ctitleTextColor, Color.BLACK);
        tvTitle.setTextColor(color);

        int size = a.getDimensionPixelSize(R.styleable.CustomItemView_ctitleTextColor, 16);
        tvTitle.setTextSize(size);

        String content = a.getString(R.styleable.CustomItemView_cdescriptionText);
        if (!TextUtils.isEmpty(content)) {
            tvContent.setText(content);
        } else {
            tvContent.setVisibility(GONE);
        }

        int contentColor = a.getColor(R.styleable.CustomItemView_cdescriptionTextColor, 0xff666666);
        tvContent.setTextColor(contentColor);

        int contentSize = a.getDimensionPixelSize(R.styleable.CustomItemView_cdescriptionTextSize, 14);
        tvContent.setTextSize(contentSize);

        int arrowResId = a.getResourceId(R.styleable.CustomItemView_carrowImage, -1);
        if (arrowResId > 0) {
            ivArrow.setImageResource(arrowResId);
        } else {
            ivArrow.setVisibility(GONE);
        }

        int arrowScale = a.getInt(R.styleable.CustomItemView_carrowImageScaleType, -1);
        if (arrowScale > 0) {
            ivArrow.setScaleType(sScaleTypeArray[arrowScale]);
        }

        a.recycle();

    }

    private void init() {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.option_item_view, this);
        ivIcon = (ImageView) findViewById(R.id.iv_icon);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvContent = (TextView) findViewById(R.id.tv_content);
        ivArrow = (ImageView) findViewById(R.id.iv_arrow);
    }


    public void setTitle(String str) {
        tvTitle.setText(str);
    }

    public void setTitle(int id) {
        tvTitle.setText(id);
    }

    public void setIcon(int id) {
        ivIcon.setVisibility(VISIBLE);
        ivIcon.setImageResource(id);
    }

    public void setContent(String content) {
        tvContent.setText(content);
        tvContent.setVisibility(VISIBLE);
    }

    public void setArrowVisibility(int visibility) {
        ivArrow.setVisibility(visibility);
    }

    private static final ImageView.ScaleType[] sScaleTypeArray = {
            ImageView.ScaleType.MATRIX,
            ImageView.ScaleType.FIT_XY,
            ImageView.ScaleType.FIT_START,
            ImageView.ScaleType.FIT_CENTER,
            ImageView.ScaleType.FIT_END,
            ImageView.ScaleType.CENTER,
            ImageView.ScaleType.CENTER_CROP,
            ImageView.ScaleType.CENTER_INSIDE
    };
}
