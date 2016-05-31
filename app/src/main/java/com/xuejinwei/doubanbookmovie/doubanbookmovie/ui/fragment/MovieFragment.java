package com.xuejinwei.doubanbookmovie.doubanbookmovie.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.xuejinwei.doubanbookmovie.doubanbookmovie.R;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.model.SubjectCollectionType;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.ui.activity.BoxOfficeActivity;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.ui.activity.MovieListActivity;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.ui.base.fragment.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends BaseFragment {


    @Bind(R.id.ll_in_theaters) LinearLayout ll_in_theaters;
    @Bind(R.id.ll_coming_soon) LinearLayout ll_coming_soon;
    @Bind(R.id.ll_america)     LinearLayout ll_america;
    @Bind(R.id.ll_top250)      LinearLayout ll_top250;

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
        Box3Fragment.inject((AppCompatActivity) getActivity(), R.id.ll_fragment_movie_root, SubjectCollectionType.movie_showing, "正在热映");
        Box3Fragment.inject((AppCompatActivity) getActivity(), R.id.ll_fragment_movie_root, SubjectCollectionType.movie_latest, "新片速递");
        Box3Fragment.inject((AppCompatActivity) getActivity(), R.id.ll_fragment_movie_root, SubjectCollectionType.movie_score, "高分电影");
        Box3Fragment.inject((AppCompatActivity) getActivity(), R.id.ll_fragment_movie_root, SubjectCollectionType.movie_free_stream, "免费在线观看");

        ll_in_theaters.setOnClickListener(v -> MovieListActivity.start(getActivity(), MovieListActivity.Type.IN_THEATERS));
        ll_coming_soon.setOnClickListener(v -> MovieListActivity.start(getActivity(), MovieListActivity.Type.COMMING_SOON));
        ll_america.setOnClickListener(v -> startActivity(new Intent(getActivity(), BoxOfficeActivity.class)));
        ll_top250.setOnClickListener(v -> MovieListActivity.start(getActivity(), MovieListActivity.Type.TOP250));

    }
}
