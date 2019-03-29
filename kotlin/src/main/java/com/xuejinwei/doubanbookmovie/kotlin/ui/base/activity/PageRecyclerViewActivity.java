package com.xuejinwei.doubanbookmovie.kotlin.ui.base.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.paginate.Paginate;
import com.twiceyuan.commonadapter.library.adapter.CommonAdapter;
import com.xuejinwei.doubanbookmovie.kotlin.R;
import com.xuejinwei.doubanbookmovie.kotlin.api.FlatHandler;
import com.xuejinwei.doubanbookmovie.kotlin.widget.DividerItemDecoration;

import java.util.List;

import rx.Observable;

/**
 * Created by xuejinwei on 16/5/7.
 * Email:xuejinwei@outlook.com
 */
public abstract class PageRecyclerViewActivity<Entity> extends SwipeBackActivity implements SwipeRefreshLayout.OnRefreshListener {
    private View                     mEmptyView; // 无数据View
    private RecyclerView             mRecyclerView; // 操作的 RecyclerView
    private SwipeRefreshLayout       mRefreshLayout; // 控制刷新的控件
    private CommonAdapter<Entity, ?> mAdapter; // 适配器


    private int     mStart       = 0; // 当前页
    private boolean mIsLoading   = false;
    private boolean mIsCompleted = false;
    private Paginate mPaginate;

    /**
     * 提供布局 ID
     */
    public abstract
    @LayoutRes
    int provideLayoutId();

    /**
     * 提供 RecyclerView ID
     */
    public abstract
    @IdRes
    int provideRecyclerViewId();

    /**
     * 提供刷新 View ID
     */
    public abstract
    @IdRes
    int provideRefreshViewId();

    /**
     * 提供数分页据源
     *
     * @param start 起始数
     * @param count 返回个数
     */
    public abstract Observable<List<Entity>> provideDataSource(int start, int count);

    /**
     * 提供适配器
     */
    public abstract CommonAdapter<Entity, ?> provideAdapter();

    /**
     * 提供没有数据的布局
     */
    public abstract
    @IdRes
    int provideEmptyViewId();

    public abstract RecyclerView.LayoutManager provideLayoutManager();

    /**
     * activity需要传递的参数取值
     */
    public abstract void getExtra(Intent intent);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(provideLayoutId());
        getExtra(getIntent());

        mPaginate = Paginate.with(getRecyclerView(), new Paginate.Callbacks() {
            @Override
            public void onLoadMore() {
                mIsLoading = true;
                load();
            }

            @Override
            public boolean isLoading() {
                return mIsLoading;
            }

            @Override
            public boolean hasLoadedAllItems() {
                return mIsCompleted;
            }
        }).setLoadingTriggerThreshold(2).build();

        getRefreshLayout().setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        mStart = 0;
//        getEmptyView().setVisibility(View.GONE);
        load();
    }

    /**
     * 根据现有的 page 和 size 加载
     */
    final void load() {
        if (mStart == 0) {
            getRefreshLayout().setRefreshing(true);
        }
        runRxTaskOnUi(provideDataSource(mStart, getCurrentSize()), entities -> {
            if (mStart == 0) {
                mAdapter.clear();
                mAdapter.notifyDataSetChanged();
            }
            mAdapter.addAll(entities);
            mAdapter.notifyDataSetChanged();
            mIsLoading = false;
            if (entities.size() == 0) {
                mIsCompleted = true;
                Snackbar.make(this.getWindow().getDecorView().findViewById(provideRecyclerViewId()), "到底了……", Snackbar.LENGTH_LONG).show();
                mPaginate.setHasMoreDataToLoad(false);
            } else {
                mStart = mStart + getCurrentSize();
                mIsCompleted = false;
                mPaginate.setHasMoreDataToLoad(true);
            }
            getRefreshLayout().setRefreshing(false);
        }, throwable -> {

            FlatHandler.handleError(this, throwable);
            getRefreshLayout().setRefreshing(false);
        });

    }

    /**
     * 获得适配器(单例)
     */
    public RecyclerView.Adapter getAdapter() {
        if (mAdapter == null) {
            mAdapter = provideAdapter();
        }
        return mAdapter;
    }

    /**
     * 重新配置 Adapter
     */
    public void setAdapter(CommonAdapter<Entity, ?> adapter) {
        mAdapter = adapter;
        getRecyclerView().setAdapter(mAdapter);
//        bindClickListener();
    }

    /**
     * 不方便使用 ButterKnife
     */
    public <T extends View> T bind(int id) {
        //noinspection unchecked,ConstantConditions
        return (T) this.getWindow().getDecorView().findViewById(id);
    }

    /**
     * 默认 SIZE, 需要改动请重载
     */
    public int getCurrentSize() {
        return 12;
    }

    public RecyclerView getRecyclerView() {
        if (mRecyclerView == null) {
            mRecyclerView = bind(provideRecyclerViewId());
            mRecyclerView.setLayoutManager(provideLayoutManager());
            mRecyclerView.setAdapter(getAdapter());
            // 分割线
            mRecyclerView.addItemDecoration(
                    new DividerItemDecoration(
                            ContextCompat.getDrawable(this, R.drawable.divider)));
        }
        return mRecyclerView;
    }

    public SwipeRefreshLayout getRefreshLayout() {
        if (mRefreshLayout == null) {
            mRefreshLayout = bind(provideRefreshViewId());
            mRefreshLayout.setColorSchemeResources(R.color.google_green, R.color.google_red, R.color.google_blue, R.color.google_yellow);
        }
        return mRefreshLayout;
    }

    /**
     * 获得空布局
     */
    public View getEmptyView() {
        if (mEmptyView == null) {
            mEmptyView = bind(provideEmptyViewId());
            mEmptyView.setVisibility(View.GONE); // 创建时隐藏
        }
        return mEmptyView;
    }

    /**
     * 提供数据为空时的操作(例如配置 empty view)
     */
    public void empty() {
        getRecyclerView().setVisibility(View.GONE);
        getEmptyView().setVisibility(View.VISIBLE);
    }

    /**
     * 不为空
     */
    public void notEmpty() {
        getRecyclerView().setVisibility(View.VISIBLE);
        getEmptyView().setVisibility(View.GONE);
    }

}