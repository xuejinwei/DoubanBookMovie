package com.xuejinwei.doubanbookmovie.doubanbookmovie.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.twiceyuan.commonadapter.library.adapter.CommonAdapter;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.R;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.adapter.MovieBoxNewHolder;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.adapter.SubjectCollectionHolder;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.api.FlatHandler;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.model.MovieSimple;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.model.SubjectCollectionItems;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.model.SubjectCollectionType;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.ui.activity.AuthActivity;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.ui.activity.MovieListActivity;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.ui.base.fragment.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    @Bind(R.id.swipe_movie_in_theaters)   SwipeRefreshLayout swipe_fragment_movie;
    @Bind(R.id.ll_coming_soon)            LinearLayout       ll_coming_soon;
    @Bind(R.id.ll_america)                LinearLayout       ll_america;
    @Bind(R.id.ll_top250)                 LinearLayout       ll_top250;
    @Bind(R.id.ll_in_theaters)            LinearLayout       ll_in_theaters;
    @Bind(R.id.ll_movie_hot_more)         LinearLayout       ll_movie_hot_more;
    @Bind(R.id.ll_movie_latest_more)      LinearLayout       ll_movie_latest_more;
    @Bind(R.id.ll_movie_score_more)       LinearLayout       ll_movie_score_more;
    @Bind(R.id.ll_movie_free_stream_more) LinearLayout       ll_movie_free_stream_more;
    @Bind(R.id.rv_movie_in_theaters)      RecyclerView       rv_movie_in_theaters;
    @Bind(R.id.rv_movie_latest)           RecyclerView       rv_movie_latest;
    @Bind(R.id.rv_movie_score)            RecyclerView       rv_movie_score;
    @Bind(R.id.rv_movie_free_stream)      RecyclerView       rv_movie_free_stream;
    @Bind(R.id.ll_root)                   LinearLayout       ll_root;
    @Bind(R.id.progressBar)               ProgressBar        progressBar;

    private CommonAdapter<MovieSimple, MovieBoxNewHolder>                  mMovieBoxAdapterInTheater;
    private CommonAdapter<SubjectCollectionItems, SubjectCollectionHolder> mSubjectCollectionAdapterMovieLatest;
    private CommonAdapter<SubjectCollectionItems, SubjectCollectionHolder> mSubjectCollectionAdapterMovieScore;
    private CommonAdapter<SubjectCollectionItems, SubjectCollectionHolder> mSubjectCollectionAdapterMovieFreeStream;

    public MovieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();//初始化view
        setCliclListener();//绑定点击事件
        onRefresh();

    }

    /**
     * 初始化控件
     */
    private void initView() {
        // 隐藏初始控件
        ll_movie_free_stream_more.setVisibility(View.GONE);
        ll_movie_hot_more.setVisibility(View.GONE);
        ll_movie_latest_more.setVisibility(View.GONE);
        ll_movie_score_more.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        // 实例化适配器
        mMovieBoxAdapterInTheater = new CommonAdapter<>(getActivity(), MovieBoxNewHolder.class);
        mSubjectCollectionAdapterMovieLatest = new CommonAdapter<>(getActivity(), SubjectCollectionHolder.class);
        mSubjectCollectionAdapterMovieScore = new CommonAdapter<>(getActivity(), SubjectCollectionHolder.class);
        mSubjectCollectionAdapterMovieFreeStream = new CommonAdapter<>(getActivity(), SubjectCollectionHolder.class);

        //初始化recycleview
        initRecyclerView(rv_movie_in_theaters, mMovieBoxAdapterInTheater);
        initRecyclerView(rv_movie_latest, mSubjectCollectionAdapterMovieLatest);
        initRecyclerView(rv_movie_score, mSubjectCollectionAdapterMovieScore);
        initRecyclerView(rv_movie_free_stream, mSubjectCollectionAdapterMovieFreeStream);

        swipe_fragment_movie.setColorSchemeResources(R.color.google_green, R.color.google_red, R.color.google_blue, R.color.google_yellow);
        swipe_fragment_movie.setOnRefreshListener(this);

    }

    /**
     * 绑定点击事件
     */
    private void setCliclListener() {
        ll_in_theaters.setOnClickListener(v -> MovieListActivity.start(getActivity(), MovieListActivity.Type.IN_THEATERS));
        ll_coming_soon.setOnClickListener(v -> MovieListActivity.start(getActivity(), MovieListActivity.Type.COMMING_SOON));
        ll_america.setOnClickListener(v -> startActivity(new Intent(getActivity(), AuthActivity.class)));
        ll_top250.setOnClickListener(v -> MovieListActivity.start(getActivity(), MovieListActivity.Type.TOP250));
        swipe_fragment_movie.post(this::onRefresh);
    }

    /**
     * 初始化recyclerView
     *
     * @param recyclerView  要初始化的recyclerview
     * @param commonAdapter 为适配器绑定的adapter
     */
    private void initRecyclerView(RecyclerView recyclerView, CommonAdapter commonAdapter) {
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(commonAdapter);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onRefresh() {
        swipe_fragment_movie.setRefreshing(true);
        runRxTaskOnUi(mApiWrapper.getMovieInTheaters(0, 3), movieResult -> {
            mMovieBoxAdapterInTheater.clear();
            mMovieBoxAdapterInTheater.addAll(movieResult.subjects);
            mMovieBoxAdapterInTheater.notifyDataSetChanged();
            onRefreshSuccess();
            ll_movie_hot_more.setVisibility(View.VISIBLE);
        }, throwable -> {
            FlatHandler.handleError(getActivity(), throwable);
            swipe_fragment_movie.setRefreshing(false);
        });

        runRxTaskOnUi(mApiWrapper.getSubjectCollection(SubjectCollectionType.movie_latest, 0, 3), subjectCollectionResult -> {
            mSubjectCollectionAdapterMovieLatest.clear();
            mSubjectCollectionAdapterMovieLatest.addAll(subjectCollectionResult.subject_collection_items);
            mSubjectCollectionAdapterMovieLatest.notifyDataSetChanged();
            onRefreshSuccess();
            ll_movie_latest_more.setVisibility(View.VISIBLE);
        }, throwable -> {
            FlatHandler.handleError(getActivity(), throwable);
            swipe_fragment_movie.setRefreshing(false);
        });

        runRxTaskOnUi(mApiWrapper.getSubjectCollection(SubjectCollectionType.movie_score, 0, 3), subjectCollectionResult -> {
            mSubjectCollectionAdapterMovieScore.clear();
            mSubjectCollectionAdapterMovieScore.addAll(subjectCollectionResult.subject_collection_items);
            mSubjectCollectionAdapterMovieScore.notifyDataSetChanged();
            onRefreshSuccess();
            ll_movie_score_more.setVisibility(View.VISIBLE);
        }, throwable -> {
            FlatHandler.handleError(getActivity(), throwable);
            swipe_fragment_movie.setRefreshing(false);
        });

        runRxTaskOnUi(mApiWrapper.getSubjectCollection(SubjectCollectionType.movie_free_stream, 0, 3), subjectCollectionResult -> {
            mSubjectCollectionAdapterMovieFreeStream.clear();
            mSubjectCollectionAdapterMovieFreeStream.addAll(subjectCollectionResult.subject_collection_items);
            mSubjectCollectionAdapterMovieFreeStream.notifyDataSetChanged();
            onRefreshSuccess();
            ll_movie_free_stream_more.setVisibility(View.VISIBLE);
        }, throwable -> {
            FlatHandler.handleError(getActivity(), throwable);
            swipe_fragment_movie.setRefreshing(false);
        });
    }

    /**
     * 数据刷新成功之后的共同操作
     */
    public void onRefreshSuccess() {
        swipe_fragment_movie.setRefreshing(false);
        progressBar.setVisibility(View.GONE);
        ll_root.setVisibility(View.VISIBLE);
    }
}
