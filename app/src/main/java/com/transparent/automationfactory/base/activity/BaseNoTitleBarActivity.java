package com.transparent.automationfactory.base.activity;

import android.os.Bundle;
import android.view.Window;

public abstract class BaseNoTitleBarActivity extends BaseActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
       
        requestWindowFeature(Window.FEATURE_NO_TITLE);	
    }
	
}
