package com.xuejinwei.doubanbookmovie.kotlin.ui.activity;

import android.os.Bundle;

import com.xuejinwei.doubanbookmovie.kotlin.R;
import com.xuejinwei.doubanbookmovie.kotlin.ui.base.activity.SwipeBackActivity;

/**
 * Created by xuejinwei on 16/6/2.
 * Email:xuejinwei@outlook.com
 */
public class AboutActivity extends SwipeBackActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        setTitle("关于APP");
    }
}
