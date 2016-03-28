package com.xuejinwei.doubanbookmovie.doubanbookmovie.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.twiceyuan.commonadapter.library.adapter.CommonAdapter;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.R;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.adapter.MovieListHolder;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.api.FlatHandler;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.model.MovieResult;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.model.MovieSimple;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.ui.base.activity.SwipeBackActivity;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.util.CommonUtil;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.widget.OnVerticalScrollListener;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;

public class MovieListActivity extends SwipeBackActivity implements SwipeRefreshLayout.OnRefreshListener {
    public static final String ARG_TYPE = "type";

    @Bind(R.id.toolbar)       Toolbar            toolbar;
    @Bind(R.id.rv_movie_list) RecyclerView       rv_movie_list;
    @Bind(R.id.srl_refresh)   SwipeRefreshLayout srl_refresh;

    public enum Type {
        IN_THEATERS,      // 正在热映
        COMMING_SOON     // 即将上映
        ,TOP250
        ,SEARCH            // 关键字搜索
    }

    private Type                                        mType; // 类型
    private CommonAdapter<MovieSimple, MovieListHolder> mMovieListAdapter;
    private boolean isLoading = false;
    private int mStart; // 当前页
    private final Object monitor = new Object();


    public static void start(Context context, MovieListActivity.Type type) {
        Intent starter = new Intent(context, MovieListActivity.class);
        starter.putExtra(ARG_TYPE, type);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);
        ButterKnife.bind(this);
        mType = (Type) getIntent().getSerializableExtra(ARG_TYPE);

        mMovieListAdapter = new CommonAdapter<>(this, MovieListHolder.class);
        rv_movie_list.setLayoutManager(new LinearLayoutManager(this));
        rv_movie_list.setItemAnimator(new DefaultItemAnimator());
        rv_movie_list.setAdapter(mMovieListAdapter);
        rv_movie_list.addOnScrollListener(new OnVerticalScrollListener() {
            @Override
            public void onScrolledToEnd() {
                super.onScrolledToEnd();
                // 如果适配器中数据少于一页, 则在下滑时不加载数据 (防止第一次进入界面时加载两次)
                if (mMovieListAdapter.getItemCount() >= getCurrentSize()) {
                    load();
                }
            }
        });
        srl_refresh.setColorSchemeResources(R.color.google_green, R.color.google_red, R.color.google_blue, R.color.google_yellow);
        srl_refresh.setOnRefreshListener(this);
        srl_refresh.post(() -> onRefresh());
    }

    /**
     * 提供数分页据源
     */
    public Observable<MovieResult> provideDataSource(int page, int size) {
        switch (mType) {
            case IN_THEATERS:
                setTitle("正在热映");
                return mApiWrapper.getMovieInTheaters(page, size);
            case COMMING_SOON:
                setTitle("即将上映");
                return mApiWrapper.getMovieComingSoon(page, size);
            case TOP250:
                setTitle("Top250");
                return mApiWrapper.getMovieTop250(page, size);
            case SEARCH:
                return null;
            default:
                return null;
        }
    }

    @Override
    public void onRefresh() {
        synchronized (monitor) {
            mStart = 0;
//            getEmptyView().setVisibility(View.GONE);
            load();
        }
    }

    /**
     * 更新适配器数据，并且通知
     *
     * @param movieList movie列表
     */
    public void upData(List<MovieSimple> movieList) {
        mMovieListAdapter.clear();
        mMovieListAdapter.addAll(movieList);
        mMovieListAdapter.notifyDataSetChanged();
    }

    /**
     * 根据现有的 page 和 size 加载
     */
    final void load() {
        if (isLoading) return; // 如果已经在加载，则跳出
        isLoading = true; // 否则开始加载，并设置加载标识为 true

        srl_refresh.post(() -> srl_refresh.setRefreshing(true));
        runRxTaskOnUi(provideDataSource(mStart, getCurrentSize()),
                movieResult -> {
                    MovieListActivity.this.loadSuccess(movieResult);
                    MovieListActivity.this.loaded();
                },
                throwable -> {
                    FlatHandler.handleError(throwable);
                    loaded();
                });
    }

    final void loaded() {
        srl_refresh.post(() -> srl_refresh.setRefreshing(false));
        isLoading = false;
    }

    /**
     * 加载成功时的操作
     */
    void loadSuccess(MovieResult movieResult) {
        synchronized (monitor) {
            int count = movieResult == null ? 0 : movieResult.count;
            // 如果返回结果为空
            if (count == 0) {
                // 如果结果和当前页均为 0, 则代表当前列表无数据, 调用抽象方法 empty, 否则为没有更多数据
                if (mStart == 0) {
                    // 一开始就没数据, 则按空数据处理
                    // TODO: 16/3/28 处理为空
//                    empty();
                } else {
                    CommonUtil.toast("没有更多数据了");
                }
            } else {
                // 如果当前页是 0, 则在加载结束后删除原有数据
                if (mStart == 0) {
                    mMovieListAdapter.clear();
                }
                mMovieListAdapter.addAll(movieResult.subjects);
                mMovieListAdapter.notifyDataSetChanged();
                CommonUtil.toast("加载了 " + count + "条数据");
                // 加载将当前页数+1, 供下一次加载是调用
                mStart = mStart + getCurrentSize();
                // TODO: 16/3/28 处理非空
//                notEmpty(); // 任何情况下有数据, 隐藏 empty view 并显示 RecyclerView
            }
            isLoading = false; // 加载结束
        }
    }

    public int getCurrentSize() {
        return 10;
    }
}
