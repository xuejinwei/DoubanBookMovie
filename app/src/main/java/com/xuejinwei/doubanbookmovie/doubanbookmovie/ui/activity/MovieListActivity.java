package com.xuejinwei.doubanbookmovie.doubanbookmovie.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.paginate.Paginate;
import com.twiceyuan.commonadapter.library.adapter.CommonAdapter;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.R;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.adapter.MovieListHolder;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.model.MovieResult;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.model.MovieSimple;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.ui.base.activity.SwipeBackActivity;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.widget.DividerItemDecoration;

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
        COMMING_SOON,     // 即将上映
        TOP250,           // top250
        SEARCH            // 关键字搜索
    }

    private Type                                        mType; // 类型
    private CommonAdapter<MovieSimple, MovieListHolder> mMovieListAdapter;

    private int     mStart       = 0; // 当前页
    private boolean mIsLoading   = false;
    private boolean mIsCompleted = false;
    private Paginate mPaginate;

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
        mMovieListAdapter.setOnItemClickListener((position, movieSimple) -> MovieDetailActivity.start(MovieListActivity.this, movieSimple.id));
        rv_movie_list.setLayoutManager(new LinearLayoutManager(this));
        rv_movie_list.setItemAnimator(new DefaultItemAnimator());
        rv_movie_list.setAdapter(mMovieListAdapter);
        rv_movie_list.addItemDecoration(
                new DividerItemDecoration(
                        ContextCompat.getDrawable(this, R.drawable.divider)));

        mPaginate = Paginate.with(rv_movie_list, new Paginate.Callbacks() {
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

        srl_refresh.setColorSchemeResources(R.color.google_green, R.color.google_red, R.color.google_blue, R.color.google_yellow);
        srl_refresh.setOnRefreshListener(this);
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
        mStart = 0;
        load();
    }

    /**
     * 加载数据
     */
    private void load() {
        if (mStart == 0) {
            srl_refresh.setRefreshing(true);
        }
        runRxTaskOnUi(provideDataSource(mStart, getCurrentSize()), movieResult -> {
            if (mStart == 0) {
                mMovieListAdapter.clear();
                mMovieListAdapter.notifyDataSetChanged();
            }
            mMovieListAdapter.addAll(movieResult.subjects);
            mMovieListAdapter.notifyDataSetChanged();
            mIsLoading = false;
            if (mMovieListAdapter.getItemCount() >= movieResult.total) {
                mIsCompleted = true;
                mPaginate.setHasMoreDataToLoad(false);
            } else {
                mStart = mStart + getCurrentSize();
                mIsCompleted = false;
                mPaginate.setHasMoreDataToLoad(true);
            }
            srl_refresh.setRefreshing(false);
        }, throwable -> {
            // TODO: 16/4/9 重新处理错误
//            FlatHandler.handleError(this,throwable);
            srl_refresh.setRefreshing(false);
        });

    }

    public int getCurrentSize() {
        return 10;
    }
}
