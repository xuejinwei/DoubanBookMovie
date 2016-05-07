package com.xuejinwei.doubanbookmovie.doubanbookmovie.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.twiceyuan.commonadapter.library.adapter.CommonAdapter;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.R;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.adapter.CommentsHolder;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.adapter.ReviewsHolder;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.model.Comments;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.model.Reviews;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.model.SortType;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.ui.base.fragment.BaseFragment;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.widget.DividerItemDecoration;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by xuejinwei on 16/5/4.
 * Email:xuejinwei@outlook.com
 */
public class CommentsFragment extends BaseFragment {
    public static final String ARG_TYPE = "type";
    public static final String ID       = "id";
    @Bind(R.id.rv_comments)       RecyclerView rv_comments;
    @Bind(R.id.tv_comments_more)  TextView     tv_comments_more;
    @Bind(R.id.tv_comments_title) TextView     tv_comments_title;
    @Bind(R.id.ll_comments_root)  LinearLayout ll_comments_root;

    private CommonAdapter<?, ?> mCommentsHolder;
    private Type                mType; // 类型
    private String              mId; // id

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public enum Type {
        MOVIE_COMMENTS,      // 电影短评
        MOVIE_REVIEWS,     // 电影影评
        BOOK_COMMENTS,
        BOOK_REVIEWS
    }

    /**
     * @param activity    activity
     * @param containerId 要注入的layout的id
     * @param type        类型，电影短评，电影影评……
     */
    public static CommentsFragment inject(AppCompatActivity activity, int containerId, Type type, String id) {
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        CommentsFragment fragment = new CommentsFragment(type, id);
        transaction.add(containerId, fragment);
        transaction.commit();
        return fragment;
    }

    public CommentsFragment(Type type, String id) {
        this.mType = type;
        this.mId = id;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comments, container, false);
        ButterKnife.bind(this, view);
        ll_comments_root.setVisibility(View.GONE);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        switch (mType) {
            case MOVIE_COMMENTS:
                tv_comments_title.setText("短评");
                tv_comments_more.setText("更多短评");
                mCommentsHolder = new CommonAdapter<>(getActivity(), CommentsHolder.class);
                initRecyclerView(rv_comments, mCommentsHolder);
                runRxTaskOnUi(mApiWrapper.getMovieComment(mId, 0, 3, SortType.new_score), commentses -> {
                    if (commentses.size() != 0) {
                        mCommentsHolder.clear();
                        ((CommonAdapter<Comments, CommentsHolder>) mCommentsHolder).addAll(commentses);
                        mCommentsHolder.notifyDataSetChanged();
                        onRefreshSuccess();
                    }
                });
                break;
            case BOOK_COMMENTS:
                tv_comments_title.setText("短评");
                tv_comments_more.setText("更多短评");
                mCommentsHolder = new CommonAdapter<>(getActivity(), CommentsHolder.class);
                initRecyclerView(rv_comments, mCommentsHolder);
                runRxTaskOnUi(mApiWrapper.getBookComment(mId, 0, 3, SortType.new_score), commentses -> {
                    if (commentses.size() != 0) {
                        mCommentsHolder.clear();
                        ((CommonAdapter<Comments, CommentsHolder>) mCommentsHolder).addAll(commentses);
                        mCommentsHolder.notifyDataSetChanged();
                        onRefreshSuccess();
                    }
                });
                break;
            case MOVIE_REVIEWS:
                tv_comments_title.setText("影评");
                tv_comments_more.setText("更多影评");
                mCommentsHolder = new CommonAdapter<>(getActivity(), ReviewsHolder.class);
                initRecyclerView(rv_comments, mCommentsHolder);
                runRxTaskOnUi(mApiWrapper.getMovieReviews(mId, 0, 3, SortType.new_score), commentses -> {
                    if (commentses.size() != 0) {
                        mCommentsHolder.clear();
                        ((CommonAdapter<Reviews, ReviewsHolder>) mCommentsHolder).addAll(commentses);
                        mCommentsHolder.notifyDataSetChanged();
                        onRefreshSuccess();
                    }
                });
                break;
            case BOOK_REVIEWS:
                tv_comments_title.setText("影评");
                tv_comments_more.setText("更多影评");
                mCommentsHolder = new CommonAdapter<>(getActivity(), ReviewsHolder.class);
                initRecyclerView(rv_comments, mCommentsHolder);
                runRxTaskOnUi(mApiWrapper.getBookReviews(mId, 0, 3, SortType.new_score), commentses -> {
                    if (commentses.size() != 0) {
                        mCommentsHolder.clear();
                        if (commentses.size() > 3) {
                            ((CommonAdapter<Reviews, ReviewsHolder>) mCommentsHolder).add(commentses.get(0));
                            ((CommonAdapter<Reviews, ReviewsHolder>) mCommentsHolder).add(commentses.get(1));
                            ((CommonAdapter<Reviews, ReviewsHolder>) mCommentsHolder).add(commentses.get(2));
                        } else {
                            ((CommonAdapter<Reviews, ReviewsHolder>) mCommentsHolder).addAll(commentses);
                        }
                        mCommentsHolder.notifyDataSetChanged();
                        onRefreshSuccess();
                    }
                });
                break;
        }

        tv_comments_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (mType) {
                    case MOVIE_COMMENTS:
                        break;
                    case MOVIE_REVIEWS:
                        break;
                }
            }
        });
    }

    /**
     * 初始化recyclerView
     *
     * @param recyclerView  要初始化的recyclerview
     * @param commonAdapter 为适配器绑定的adapter
     */
    private void initRecyclerView(RecyclerView recyclerView, CommonAdapter commonAdapter) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(
                new DividerItemDecoration(
                        ContextCompat.getDrawable(getActivity(), R.drawable.divider)));

        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(commonAdapter);

    }

    /**
     * 数据刷新成功之后的共同操作
     */
    public void onRefreshSuccess() {
        ll_comments_root.setVisibility(View.VISIBLE);
    }
}
