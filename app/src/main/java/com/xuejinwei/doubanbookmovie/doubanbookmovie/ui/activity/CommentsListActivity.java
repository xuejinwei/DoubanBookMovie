package com.xuejinwei.doubanbookmovie.doubanbookmovie.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.twiceyuan.commonadapter.library.adapter.CommonAdapter;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.R;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.adapter.CommentsHolder;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.adapter.ReviewsHolder;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.model.Comments;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.model.Reviews;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.model.SortType;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.ui.base.activity.PageRecyclerViewActivity;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by xuejinwei on 16/5/7.
 * Email:xuejinwei@outlook.com
 */
public class CommentsListActivity extends PageRecyclerViewActivity {
    public static final String ID    = "id";
    public static final String TITLE = "title";
    public static final String TYPE  = "type";
    public String mId;
    public String mTitle;
    public Type   mType;

    public enum Type {
        MOVIE_COMMENTS,      // 电影短评
        MOVIE_REVIEWS,     // 电影影评
        BOOK_COMMENTS,
        BOOK_REVIEWS
    }

    /**
     * @param context context
     * @param type    类型
     * @param title   显示在toolbar上的title
     * @param id      book 或者movie的id
     */
    public static void start(Context context, Type type, String title, String id) {
        Intent starter = new Intent(context, CommentsListActivity.class);
        starter.putExtra(ID, id);
        starter.putExtra(TITLE, title);
        starter.putExtra(TYPE, type);
        context.startActivity(starter);
    }

    @Override
    public int provideLayoutId() {
        return R.layout.activity_comments_list;
    }

    @Override
    public int provideRecyclerViewId() {
        return R.id.rv_subject_collection;
    }

    @Override
    public int provideRefreshViewId() {
        return R.id.srl_refresh_subject_collection;
    }

    @Override
    public Observable<List> provideDataSource(int start, int count) {
        if (mType == Type.MOVIE_COMMENTS) {
            return mApiWrapper.getMovieComment(mId, start, count, SortType.new_score).map((Func1<List<Comments>, List<Comments>>) reviewses -> reviewses);
        }
        if (mType == Type.MOVIE_REVIEWS) {
            return mApiWrapper.getMovieReviews(mId, start, count, SortType.new_score).map((Func1<List<Reviews>, List<Reviews>>) reviewses -> reviewses);
        }
        if (mType == Type.BOOK_COMMENTS) {
            return mApiWrapper.getBookComment(mId, start, count, SortType.new_score).map((Func1<List<Comments>, List<Comments>>) reviewses -> reviewses);
        }
        if (mType == Type.BOOK_REVIEWS) {
            return mApiWrapper.getBookReviews(mId, start, count, SortType.new_score).map((Func1<List<Reviews>, List<Reviews>>) reviewses -> reviewses);
        }
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(mTitle);
    }

    @Override
    public int getCurrentSize() {
        return 25;
    }

    @Override
    public CommonAdapter provideAdapter() {
        if (mType == Type.MOVIE_COMMENTS) {
            return new CommonAdapter<>(this, CommentsHolder.class);
        }
        if (mType == Type.MOVIE_REVIEWS) {
            return new CommonAdapter<>(this, ReviewsHolder.class);
        }
        if (mType == Type.BOOK_COMMENTS) {
            return new CommonAdapter<>(this, CommentsHolder.class);
        }
        if (mType == Type.BOOK_REVIEWS) {
            return new CommonAdapter<>(this, ReviewsHolder.class);
        }
        return null;
    }

    @Override
    public int provideEmptyViewId() {
        return R.id.emptyView;
    }

    @Override
    public RecyclerView.LayoutManager provideLayoutManager() {
        return new LinearLayoutManager(this);
    }

    @Override
    public void getExtra(Intent intent) {
        mId = intent.getStringExtra(ID);
        mTitle = intent.getStringExtra(TITLE);
        mType = (Type) intent.getSerializableExtra(TYPE);

    }
}
