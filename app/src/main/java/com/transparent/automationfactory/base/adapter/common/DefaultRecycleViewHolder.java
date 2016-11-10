package com.transparent.automationfactory.base.adapter.common;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.transparent.automationfactory.base.util.ViewUtil;


/**
 * Created by yzbzz on 16/4/12.
 */
public class DefaultRecycleViewHolder extends RecyclerView.ViewHolder {

    public View mConvertView;
    private SparseArray<View> mViews;

    public DefaultRecycleViewHolder(View itemView) {
        super(itemView);
        mConvertView = itemView;
        mViews = new SparseArray<>();
        mConvertView.setClickable(true);
    }

    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (null == view) {
            view = ViewUtil.findViewById(mConvertView, viewId);
            mViews.append(viewId, view);
        }
        return (T) view;
    }

    public DefaultRecycleViewHolder setText(int viewId, int resId) {
        TextView tv = getView(viewId);
        tv.setText(resId);
        return this;
    }

    public DefaultRecycleViewHolder setText(int viewId, String text) {
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }

    public DefaultRecycleViewHolder setText(int viewId, CharSequence text) {
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }

    public DefaultRecycleViewHolder setImageResource(int viewId, int resId) {
        ImageView view = getView(viewId);
        view.setImageResource(resId);
        return this;
    }

    public DefaultRecycleViewHolder setOnClickListener(int viewId, View.OnClickListener listener) {
        View view = getView(viewId);
        view.setOnClickListener(listener);
        return this;
    }

    public DefaultRecycleViewHolder setVisible(int viewId, int visibility) {
        View view = getView(viewId);
        view.setVisibility(visibility);
        return this;
    }

}
