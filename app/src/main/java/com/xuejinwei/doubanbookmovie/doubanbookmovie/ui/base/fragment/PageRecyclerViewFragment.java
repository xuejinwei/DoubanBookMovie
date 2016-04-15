package com.xuejinwei.doubanbookmovie.doubanbookmovie.ui.base.fragment;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.paginate.Paginate;
import com.twiceyuan.commonadapter.library.adapter.CommonAdapter;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.R;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.api.FlatHandler;

import java.util.List;

import rx.Observable;

/**
 * Created by xuejinwei on 16/4/14.
 * Email:xuejinwei@outlook.com
 * 一般 List 类型 RecyclerView 数据
 */
public abstract class PageRecyclerViewFragment<Entity> extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    private int                      mCurrentPage; // 当前页
    private View                     mEmptyView; // 无数据View
    private RecyclerView             mRecyclerView; // 操作的 RecyclerView
    private SwipeRefreshLayout       mRefreshLayout; // 控制刷新的控件
    private CommonAdapter<Entity, ?> mAdapter; // 适配器


    private int     mStart       = 0; // 当前页
    private boolean mIsLoading   = false;
    private boolean mIsCompleted = false;
    private Paginate mPaginate;

    public String mTitle = "";

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

    /**
     * Fragment 需要传递的参数取值
     *
     * @param args 传递给 fragment 的参数
     */
    public abstract void getArguments(Bundle args);

    /**
     * 配置事件监听器
     */
//    public abstract void bindClickListener();
    @Override
    public final void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getArguments(getArguments());
    }

    @Nullable
    @Override
    public final View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(provideLayoutId(), container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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
            getActivity().setTitle(mTitle);
            mIsLoading = false;
            if (entities.size() == 0) {
                mIsCompleted = true;
                Snackbar.make(getView(), "到底了……", Snackbar.LENGTH_LONG).show();
                mPaginate.setHasMoreDataToLoad(false);
            } else {
                mStart = mStart + getCurrentSize();
                mIsCompleted = false;
                mPaginate.setHasMoreDataToLoad(true);
            }
            getRefreshLayout().setRefreshing(false);
        }, throwable -> {

            FlatHandler.handleError(getActivity(), throwable);
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
        return (T) getView().findViewById(id);
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
            mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
            mRecyclerView.setAdapter(getAdapter());
            // 分割线
//            mRecyclerView.addItemDecoration(
//                    new DividerItemDecoration(
//                            ContextCompat.getDrawable(getActivity(), R.drawable.divider)));
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
