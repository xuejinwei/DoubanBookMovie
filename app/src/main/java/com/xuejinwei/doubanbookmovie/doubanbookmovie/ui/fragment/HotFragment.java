package com.xuejinwei.doubanbookmovie.doubanbookmovie.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import com.xuejinwei.doubanbookmovie.doubanbookmovie.ui.activity.MovieListActivity;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.ui.activity.WebViewActivity;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.ui.base.fragment.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by xuejinwei on 16/4/11.
 * Email:xuejinwei@outlook.com
 */
public class HotFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    @Bind(R.id.img_gallery)          ImageView          img_gallery;
    @Bind(R.id.swipe_fragment_hot)   SwipeRefreshLayout swipe_fragment_hot;
    @Bind(R.id.rv_movie_in_theaters) RecyclerView       rv_movie_in_theaters;
    @Bind(R.id.ll_movie_hot_more)    LinearLayout       ll_movie_hot_more;
    @Bind(R.id.ll_book_latest_more)  LinearLayout       ll_book_latest_more;
    @Bind(R.id.rv_book_latest)       RecyclerView       rv_book_latest;
    @Bind(R.id.ll_root)              LinearLayout       ll_root;
    @Bind(R.id.progressBar)          ProgressBar        progressBar;

    private CommonAdapter<MovieSimple, MovieBoxNewHolder>                  mMovieBoxNewAdapter;
    private CommonAdapter<SubjectCollectionItems, SubjectCollectionHolder> mSubjectCollectionAdapterBookLatest;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hot, container, false);
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

    private void initView() {
        ll_root.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        ll_movie_hot_more.setVisibility(View.GONE);
        ll_book_latest_more.setVisibility(View.GONE);

        mMovieBoxNewAdapter = new CommonAdapter<>(getActivity(), MovieBoxNewHolder.class);
        mSubjectCollectionAdapterBookLatest = new CommonAdapter<>(getActivity(), SubjectCollectionHolder.class);

        initRecyclerView(rv_movie_in_theaters, mMovieBoxNewAdapter);
        initRecyclerView(rv_book_latest, mSubjectCollectionAdapterBookLatest);
        swipe_fragment_hot.setColorSchemeResources(R.color.google_green, R.color.google_red, R.color.google_blue, R.color.google_yellow);
        swipe_fragment_hot.setOnRefreshListener(this);

    }

    private void setCliclListener() {
        img_gallery.setOnClickListener(v -> WebViewActivity.start(getActivity(), "豆瓣为您做的第一本书", "https://market.douban.com/book/winter_in_china?platform=talion&channel=dale_talion_subject_movie_center"));
        ll_movie_hot_more.setOnClickListener(v -> MovieListActivity.start(getActivity(), MovieListActivity.Type.IN_THEATERS));
        swipe_fragment_hot.post(this::onRefresh);
    }


    @Override
    public void onRefresh() {
        swipe_fragment_hot.setRefreshing(true);

        runRxTaskOnUi(mApiWrapper.getMovieInTheaters(0, 3), movieResult -> {
            mMovieBoxNewAdapter.clear();
            mMovieBoxNewAdapter.addAll(movieResult.subjects);
            mMovieBoxNewAdapter.notifyDataSetChanged();
            onRefreshSuccess();
            ll_movie_hot_more.setVisibility(View.VISIBLE);
        }, throwable1 -> {
            FlatHandler.handleError(getActivity(), throwable1);
            swipe_fragment_hot.setRefreshing(false);
        });

        runRxTaskOnUi(mApiWrapper.getSubjectCollection(SubjectCollectionType.book_latest, 0, 3), subjectCollectionResult -> {
            mSubjectCollectionAdapterBookLatest.clear();
            mSubjectCollectionAdapterBookLatest.addAll(subjectCollectionResult.subject_collection_items);
            mSubjectCollectionAdapterBookLatest.notifyDataSetChanged();
            onRefreshSuccess();
            ll_book_latest_more.setVisibility(View.VISIBLE);
        }, throwable1 -> {
            FlatHandler.handleError(getActivity(), throwable1);
            swipe_fragment_hot.setRefreshing(false);
        });
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

    /**
     * 数据刷新成功之后的共同操作
     */
    public void onRefreshSuccess() {
        swipe_fragment_hot.setRefreshing(false);
        progressBar.setVisibility(View.GONE);
        ll_root.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
