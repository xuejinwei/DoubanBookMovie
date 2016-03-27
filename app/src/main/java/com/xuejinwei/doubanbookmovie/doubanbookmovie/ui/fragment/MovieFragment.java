package com.xuejinwei.doubanbookmovie.doubanbookmovie.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.twiceyuan.commonadapter.library.adapter.CommonAdapter;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.R;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.adapter.MovieBoxHolder;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.model.MovieSimple;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.ui.base.fragment.BaseFragment;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.util.RxUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.functions.Action1;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    @Bind(R.id.ll_more)
    LinearLayout       ll_more;
    @Bind(R.id.rv_movie_in_theaters)
    RecyclerView       rv_movie_in_theaters;
    @Bind(R.id.swipe_movie_in_theaters)
    SwipeRefreshLayout swipe_movie_in_theaters;
    @Bind(R.id.ll_coming_soon)
    LinearLayout       ll_coming_soon;
    @Bind(R.id.ll_america)
    LinearLayout       ll_america;
    @Bind(R.id.ll_top250)
    LinearLayout       ll_top250;
    @Bind(R.id.ll_in_theaters)
    LinearLayout       ll_in_theaters;

    private CommonAdapter<MovieSimple, MovieBoxHolder> mMovieBoxAdapter;

    public MovieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_movie, container, false);
        ButterKnife.bind(this, view);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        mMovieBoxAdapter = new CommonAdapter<>(getActivity(), MovieBoxHolder.class);

        rv_movie_in_theaters.setLayoutManager(gridLayoutManager);
        rv_movie_in_theaters.setItemAnimator(new DefaultItemAnimator());
        rv_movie_in_theaters.setAdapter(mMovieBoxAdapter);
        swipe_movie_in_theaters.setColorSchemeResources(R.color.google_green, R.color.google_red, R.color.google_blue, R.color.google_yellow);
        swipe_movie_in_theaters.setOnRefreshListener(this);
        swipe_movie_in_theaters.post(new Runnable() {
            @Override
            public void run() {
                onRefresh();
            }
        });

        return view;
    }

//    @OnClick(R.id.ll_in_theaters)
//    public void in_theaters() {
//        MovieListActivity.start(getActivity(), MovieListActivity.Type.IN_THEATERS);
//    }
//
//    @OnClick(R.id.ll_coming_soon)
//    public void coming_soon() {
//        MovieListActivity.start(getActivity(), MovieListActivity.Type.COMMING_SOON);
//    }
//
//    @OnClick(R.id.ll_america)
//    public void ll_america() {
//        MovieListActivity.start(getActivity(), MovieListActivity.Type.AMERICA);
//    }
//
//    @OnClick(R.id.ll_top250)
//    public void ll_top250() {
//        MovieListActivity.start(getActivity(), MovieListActivity.Type.TOP250);
//    }

    /**
     * 更新适配器数据，并且通知
     *
     * @param movieList movie列表
     */
    public void upData(List<MovieSimple> movieList) {
        mMovieBoxAdapter.clear();
        mMovieBoxAdapter.addAll(movieList);
        mMovieBoxAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onRefresh() {

        swipe_movie_in_theaters.setRefreshing(true);
        RxUtils.callOnUI(mApiWrapper.getMovieInTheaters(0, 6))
                .subscribe(movieResult -> {
                    upData(movieResult.subjects);
                    swipe_movie_in_theaters.setRefreshing(false);
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
//                        FlatHandler.handleError(throwable);
                        swipe_movie_in_theaters.setRefreshing(false);
                    }
                });
    }
}
