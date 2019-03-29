package com.xuejinwei.doubanbookmovie.kotlin.ui.base.activity;

/**
 * 可以返回的activity，toolbar上面有返回按钮
 * Created by xuejinwei on 16/3/12.
 */
public abstract class BackActivity extends AbsToolbarActivity{

    @Override
    public final boolean setHomeButtonAsBack() {
        return true;
    }

    @Override
    public boolean canSwipeBack() {
        return false;
    }
}
