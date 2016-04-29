package com.xuejinwei.doubanbookmovie.doubanbookmovie.ui.activity;

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
import com.xuejinwei.doubanbookmovie.doubanbookmovie.adapter.BookCollectionListHolder;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.api.FlatHandler;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.app.Setting;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.model.BookCollections;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.model.CollectionsResult;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.ui.base.activity.SwipeBackActivity;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.widget.DividerItemDecoration;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.functions.Action1;

/**
 * Created by xuejinwei on 16/4/25.
 * Email:xuejinwei@outlook.com
 */
public class BookCollectionListActivity extends SwipeBackActivity implements SwipeRefreshLayout.OnRefreshListener {
    @Bind(R.id.toolbar)       Toolbar            toolbar;
    @Bind(R.id.rv_movie_list) RecyclerView       rv_movie_list;
    @Bind(R.id.srl_refresh)   SwipeRefreshLayout srl_refresh;

    private CommonAdapter<BookCollections, BookCollectionListHolder> mBookCollectionsListAdapter;

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
        mBookCollectionsListAdapter = new CommonAdapter<>(this, BookCollectionListHolder.class);
        mBookCollectionsListAdapter.setOnItemClickListener((position, movieSimple) -> BookCollectionDetailActivity.start(this, movieSimple.book_id));
        rv_movie_list.setLayoutManager(new LinearLayoutManager(this));
        rv_movie_list.setItemAnimator(new DefaultItemAnimator());
        rv_movie_list.setAdapter(mBookCollectionsListAdapter);
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
        runRxTaskOnUi(mApiWrapper.getBookCollections(Setting.getSetting(Setting.Key.douban_user_id, "")), new Action1<CollectionsResult>() {
            @Override
            public void call(CollectionsResult collectionsResult) {
                if (mStart == 0) {
                    mBookCollectionsListAdapter.clear();
                    mBookCollectionsListAdapter.notifyDataSetChanged();
                }
                mBookCollectionsListAdapter.addAll(collectionsResult.collections);
                mBookCollectionsListAdapter.notifyDataSetChanged();
                mIsLoading = false;
                if (mBookCollectionsListAdapter.getItemCount() >= collectionsResult.total) {
                    mIsCompleted = true;
                    mPaginate.setHasMoreDataToLoad(false);
                } else {
                    mStart = mStart + getCurrentSize();
                    mIsCompleted = false;
                    mPaginate.setHasMoreDataToLoad(true);
                }
                srl_refresh.setRefreshing(false);
            }
        }, throwable -> {
            FlatHandler.handleError(this, throwable);
            srl_refresh.setRefreshing(false);
        });

    }


    public int getCurrentSize() {
        return 20;
    }
}
