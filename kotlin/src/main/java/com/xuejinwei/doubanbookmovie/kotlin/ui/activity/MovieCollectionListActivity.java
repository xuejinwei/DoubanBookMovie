package com.xuejinwei.doubanbookmovie.kotlin.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.paginate.Paginate;
import com.twiceyuan.commonadapter.library.adapter.CommonAdapter;
import com.xuejinwei.doubanbookmovie.kotlin.R;
import com.xuejinwei.doubanbookmovie.kotlin.adapter.MovieCollectionListHolder;
import com.xuejinwei.doubanbookmovie.kotlin.app.Setting;
import com.xuejinwei.doubanbookmovie.kotlin.model.MovieCollections;
import com.xuejinwei.doubanbookmovie.kotlin.ui.base.activity.SwipeBackActivity;
import com.xuejinwei.doubanbookmovie.kotlin.widget.DividerItemDecoration;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by xuejinwei on 16/5/11.
 * Email:xuejinwei@outlook.com
 */
public class MovieCollectionListActivity extends SwipeBackActivity implements SwipeRefreshLayout.OnRefreshListener {
    @Bind(R.id.toolbar)       Toolbar            toolbar;
    @Bind(R.id.rv_movie_list) RecyclerView       rv_movie_list;
    @Bind(R.id.srl_refresh)   SwipeRefreshLayout srl_refresh;

    private CommonAdapter<MovieCollections, MovieCollectionListHolder> mMovieCollectionsListAdapter;

    private int     mStart       = 0; // 当前页
    private boolean mIsLoading   = false;
    private boolean mIsCompleted = false;
    private Paginate mPaginate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);
        ButterKnife.bind(this);
        setTitle("搜藏列表");
        mMovieCollectionsListAdapter = new CommonAdapter<>(this, MovieCollectionListHolder.class);
        mMovieCollectionsListAdapter.setOnItemClickListener((position, movieSimple) -> MovieCollectionDetailActivity.start(this, movieSimple.getMovieId(),movieSimple));
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
        AVQuery<MovieCollections> movieCollectionsAVQuery = new AVQuery<>("MovieCollection");
        movieCollectionsAVQuery.addDescendingOrder("createdAt");
        movieCollectionsAVQuery.whereEqualTo("userId", Setting.getSetting(Setting.Key.douban_user_id, ""));
        movieCollectionsAVQuery.findInBackground(new FindCallback<MovieCollections>() {
            @Override
            public void done(List<MovieCollections> list, AVException e) {
                mMovieCollectionsListAdapter.clear();
                mMovieCollectionsListAdapter.addAll(list);
                mMovieCollectionsListAdapter.notifyDataSetChanged();
                mIsLoading = false;
                mIsCompleted = true;
                mPaginate.setHasMoreDataToLoad(false);
                srl_refresh.setRefreshing(false);
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            onRefresh();
        }
    }

    public int getCurrentSize() {
        return 20;
    }
}
