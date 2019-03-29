package com.xuejinwei.doubanbookmovie.kotlin.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.xuejinwei.doubanbookmovie.kotlin.R;
import com.xuejinwei.doubanbookmovie.kotlin.model.SubjectCollectionType;
import com.xuejinwei.doubanbookmovie.kotlin.ui.activity.WebViewActivity;
import com.xuejinwei.doubanbookmovie.kotlin.ui.base.fragment.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by xuejinwei on 16/4/11.
 * Email:xuejinwei@outlook.com
 */
public class HotFragment extends BaseFragment {

    @Bind(R.id.img_gallery) ImageView img_gallery;

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
        Box3Fragment.inject((AppCompatActivity) getActivity(), R.id.ll_fragment_hot_root, SubjectCollectionType.movie_showing, "影院热映");
        Box3Fragment.inject((AppCompatActivity) getActivity(), R.id.ll_fragment_hot_root, SubjectCollectionType.book_latest, "新书速递");

        img_gallery.setOnClickListener(v -> WebViewActivity.start(getActivity(), "豆瓣为您做的第一本书", "https://market.douban.com/book/winter_in_china?platform=talion&channel=dale_talion_subject_movie_center"));

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
