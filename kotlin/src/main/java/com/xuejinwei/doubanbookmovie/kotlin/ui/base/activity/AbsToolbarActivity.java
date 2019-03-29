package com.xuejinwei.doubanbookmovie.kotlin.ui.base.activity;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.xuejinwei.doubanbookmovie.kotlin.R;

/**
 * 抽象toolbar activity
 * Created by xuejinwei on 16/3/12.
 */
public class AbsToolbarActivity extends BaseActivity {
    protected Toolbar mToolbar;

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        setupToolbar();
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        setupToolbar();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void setupToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        try {
            setSupportActionBar(mToolbar);
        } catch (NullPointerException exception) {
            throw new NullPointerException("xml布局文件中的toolbar未找到！！！");
        }

        if (setHomeButtonAsBack()) {
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    /**
     * 配置 home 按钮为返回键
     *
     * @return home 按钮是否为返回键，默认为否
     */
    public boolean setHomeButtonAsBack() {
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }


    /**
     *
     * @return mToolbar
     */
    public Toolbar getToolbar() {
        return mToolbar;
    }
}
