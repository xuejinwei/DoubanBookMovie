package com.xuejinwei.doubanbookmovie.doubanbookmovie.widget;

import android.support.v7.widget.RecyclerView;

/**
 * Created by xuejinwei on 16/3/28.
 * Email:xuejinwei@outlook.com
 * 垂直滚动的 RecyclerView.OnScrollListener
 */
public class OnVerticalScrollListener extends RecyclerView.OnScrollListener {

    @Override
    public final void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        if (!recyclerView.canScrollVertically(1)) {
            onScrolledToEnd();
        } else if (!recyclerView.canScrollVertically(-1)) {
            onScrolledToTop();
        } else if (dy < 0) {
            onScrolledUp();
        } else if (dy > 0) {
            onScrolledDown();
        }
    }

    public void onScrolledUp() {
    }

    public void onScrolledDown() {
    }

    public void onScrolledToEnd() {
    }

    public void onScrolledToTop() {
    }
}
