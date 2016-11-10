package com.transparent.automationfactory.base.interfaces;

import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

/**
 * Created by ETCP on 2016/5/16.
 */
public interface ITextWatcher extends TextWatcher {
    public void setWatcher(EditText edit, View delView);
}
