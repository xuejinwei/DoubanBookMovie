package com.xuejinwei.doubanbookmovie.doubanbookmovie.ui.base.activity;

/**
 * 可以滑动返回的activity
 * Created by xuejinwei on 16/3/12.
 */
public class SwipeBackActivity extends BackActivity {
    @Override
    public final boolean canSwipeBack() {
        return true;
    }
}
