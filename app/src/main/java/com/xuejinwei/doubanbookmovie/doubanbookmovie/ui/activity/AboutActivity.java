package com.xuejinwei.doubanbookmovie.doubanbookmovie.ui.activity;

import android.os.Bundle;

import com.xuejinwei.doubanbookmovie.doubanbookmovie.R;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.ui.base.activity.SwipeBackActivity;

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
