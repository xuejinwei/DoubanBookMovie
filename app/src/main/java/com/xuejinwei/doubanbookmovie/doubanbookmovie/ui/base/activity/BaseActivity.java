package com.xuejinwei.doubanbookmovie.doubanbookmovie.ui.base.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivityBase;
import me.imid.swipebacklayout.lib.app.SwipeBackActivityHelper;

/**
 * activity基础类，该项目每个activity都要继承此类或者其子类
 * Created by xuejinwei on 16/3/12.
 */
public class BaseActivity extends AppCompatActivity implements SwipeBackActivityBase {
    private SwipeBackActivityHelper mSwipeBackHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mSwipeBackHelper = new SwipeBackActivityHelper(this);
        this.mSwipeBackHelper.onActivityCreate();
        setSwipeBackEnable(canSwipeBack());
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        this.mSwipeBackHelper.onPostCreate();
    }

    @Override
    public SwipeBackLayout getSwipeBackLayout() {
        return mSwipeBackHelper.getSwipeBackLayout();
    }

    @Override
    public void setSwipeBackEnable(boolean enable) {
        this.getSwipeBackLayout().setEnableGesture(enable);
    }

    @Override
    public void scrollToFinishActivity() {
        this.getSwipeBackLayout().scrollToFinishActivity();
    }

    /**
     * 是否可以右划返回，默认不可以
     */
    public boolean canSwipeBack() {
        return false;
    }
}
