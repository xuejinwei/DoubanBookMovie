package com.xuejinwei.doubanbookmovie.doubanbookmovie.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.twiceyuan.commonadapter.library.adapter.CommonAdapter;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.R;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.adapter.BookBoxHolder;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.adapter.MovieBoxHolder;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.model.Book;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.model.BookResult;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.model.MovieResult;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.model.MovieSimple;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.ui.base.activity.SwipeBackActivity;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.ui.fragment.ClassFragment;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.util.CommonUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.functions.Action1;

/**
 * Created by xuejinwei on 16/4/20.
 * Email:xuejinwei@outlook.com
 */
public class SearchActivity extends SwipeBackActivity {
    @Bind(R.id.toolbar)                 Toolbar           toolbar;
    @Bind(R.id.et_search)               AppCompatEditText et_search;
    @Bind(R.id.img_search)              ImageView         img_search;
    @Bind(R.id.framelayout_book_class)  FrameLayout       framelayout_book_class;
    @Bind(R.id.framelayout_movie_class) FrameLayout       framelayout_movie_class;
    @Bind(R.id.ll_search_hint)          LinearLayout      ll_search_hint;
    @Bind(R.id.rv_search_book_result)   RecyclerView      rv_search_book_result;
    @Bind(R.id.rv_search_movie_result)  RecyclerView      rv_search_movie_result;
    @Bind(R.id.ll_search_result)        LinearLayout      ll_search_result;

    String mSearchKey;
    @Bind(R.id.tv_book_more)  TextView                                   tv_book_more;
    @Bind(R.id.tv_movie_more) TextView                                   tv_movie_more;
    private                   CommonAdapter<MovieSimple, MovieBoxHolder> mMovieBoxAdapter;
    private                   CommonAdapter<Book, BookBoxHolder>         mBookBoxAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serach);
        ButterKnife.bind(this);
        setTitle("");
        ClassFragment.inject(this, R.id.framelayout_book_class, ClassFragment.Type.BOOK);
        ClassFragment.inject(this, R.id.framelayout_movie_class, ClassFragment.Type.MOVIE);
        mMovieBoxAdapter = new CommonAdapter<>(this, MovieBoxHolder.class);
        mBookBoxAdapter = new CommonAdapter<>(this, BookBoxHolder.class);
        initRecyclerView(rv_search_book_result, mBookBoxAdapter);
        initRecyclerView(rv_search_movie_result, mMovieBoxAdapter);
        img_search.setOnClickListener(v -> onSearch());
        tv_movie_more.setOnClickListener(v -> {
            if (mSearchKey.equals("")) {
                CommonUtil.toast("关键字不能为空");
                return;
            }
            MovieListActivity.start(SearchActivity.this, MovieListActivity.Type.SEARCH, mSearchKey);
        });
        tv_book_more.setOnClickListener(v -> {
            if (mSearchKey.equals("")) {
                CommonUtil.toast("关键字不能为空");
                return;
            }
            BookListActivity.start(SearchActivity.this, BookListActivity.Type.SEARCH, mSearchKey);
        });
        et_search.setOnEditorActionListener((v, actionId, event) -> {
            onSearch();
            return true;
        });

    }

    private void onSearch() {
        CommonUtil.hideKeyboard(this);
        mSearchKey = et_search.getText().toString();
        if (mSearchKey.equals("")) {
            CommonUtil.toast("关键字不能为空");
            return;
        }
        runRxTaskOnUi(mApiWrapper.searchBook(0, 10, mSearchKey), new Action1<BookResult>() {
            @Override
            public void call(BookResult bookResult) {
                mBookBoxAdapter.clear();
                mBookBoxAdapter.addAll(bookResult.books);
                mBookBoxAdapter.notifyDataSetChanged();
                ll_search_hint.setVisibility(View.GONE);
                ll_search_result.setVisibility(View.VISIBLE);
            }
        });

        runRxTaskOnUi(mApiWrapper.searchMovie(0, 10, mSearchKey), new Action1<MovieResult>() {
            @Override
            public void call(MovieResult movieResult) {
                mMovieBoxAdapter.clear();
                mMovieBoxAdapter.addAll(movieResult.subjects);
                mMovieBoxAdapter.notifyDataSetChanged();
                ll_search_hint.setVisibility(View.GONE);
                ll_search_result.setVisibility(View.VISIBLE);
            }
        });
    }

    /**
     * 重写返回事件，判断是否搜索结果是否显示，如果显示则隐藏
     */
    @Override
    public void onBackPressed() {
        if (ll_search_result.getVisibility() == View.VISIBLE) {
            ll_search_result.setVisibility(View.GONE);
            ll_search_hint.setVisibility(View.VISIBLE);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * 初始化recyclerView
     *
     * @param recyclerView  要初始化的recyclerview
     * @param commonAdapter 为适配器绑定的adapter
     */
    private void initRecyclerView(RecyclerView recyclerView, CommonAdapter commonAdapter) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(commonAdapter);

    }
}
