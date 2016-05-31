package com.xuejinwei.doubanbookmovie.doubanbookmovie.ui.activity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.paginate.Paginate;
import com.twiceyuan.commonadapter.library.adapter.CommonAdapter;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.R;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.adapter.BoxOfficeListHolder;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.model.JvHeBoxMovie;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.ui.base.activity.SwipeBackActivity;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.widget.DividerItemDecoration;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by xuejinwei on 16/5/31.
 * Email:xuejinwei@outlook.com
 */
public class BoxOfficeActivity extends SwipeBackActivity implements SwipeRefreshLayout.OnRefreshListener{

    @Bind(R.id.rv_movie_list) RecyclerView                   rv_movie_list;
    @Bind(R.id.srl_refresh)   SwipeRefreshLayout             srl_refresh;
    private CommonAdapter<JvHeBoxMovie, BoxOfficeListHolder> mMovieCollectionsListAdapter;

    private int     mStart       = 0; // 当前页
    private boolean mIsLoading   = false;
    private boolean mIsCompleted = false;
    private Paginate mPaginate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_box_office);
        ButterKnife.bind(this);
        setTitle("本周票房");
        mMovieCollectionsListAdapter = new CommonAdapter<>(this, BoxOfficeListHolder.class);
        mMovieCollectionsListAdapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener<JvHeBoxMovie>() {
            @Override
            public void onClick(int position, JvHeBoxMovie movieSimple) {
                MovieListActivity.start(BoxOfficeActivity.this, MovieListActivity.Type.SEARCH,movieSimple.name);
            }
        });
        rv_movie_list.setLayoutManager(new LinearLayoutManager(this));
        rv_movie_list.setItemAnimator(new DefaultItemAnimator());
        rv_movie_list.setAdapter(mMovieCollectionsListAdapter);
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
        runRxTaskOnUi(mApiWrapper.getBoxOffice(), jvHeResult -> {
            mMovieCollectionsListAdapter.clear();
            mMovieCollectionsListAdapter.addAll(jvHeResult.result);
            mMovieCollectionsListAdapter.notifyDataSetChanged();
            mIsLoading = false;
            mIsCompleted = true;
            mPaginate.setHasMoreDataToLoad(false);
            srl_refresh.setRefreshing(false);
        });

    }


    public int getCurrentSize() {
        return 20;
    }
}
