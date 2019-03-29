package com.xuejinwei.doubanbookmovie.kotlin.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xuejinwei.doubanbookmovie.kotlin.R;
import com.xuejinwei.doubanbookmovie.kotlin.model.SubjectCollectionType;
import com.xuejinwei.doubanbookmovie.kotlin.ui.activity.SubjectCollectionActivity;
import com.xuejinwei.doubanbookmovie.kotlin.ui.base.fragment.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class BookFragment extends BaseFragment {
    @Bind(R.id.tv_filter_book_fiction_hot)  TextView tv_filter_book_fiction_hot;
    @Bind(R.id.tv_filter_book_love_hot)     TextView tv_filter_book_love_hot;
    @Bind(R.id.tv_filter_book_history_hot)  TextView tv_filter_book_history_hot;
    @Bind(R.id.tv_filter_book_foreign_hot)  TextView tv_filter_book_foreign_hot;
    @Bind(R.id.tv_filter_book_youth_hot)    TextView tv_filter_book_youth_hot;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book, container, false);
        ButterKnife.bind(this, view);
        Box3Fragment.inject((AppCompatActivity) getActivity(), R.id.framelayout_book_latest, SubjectCollectionType.book_latest, "新书速递");
        Box3Fragment.inject((AppCompatActivity) getActivity(), R.id.framelayout_book_fiction, SubjectCollectionType.book_fiction, "最受关注图书|虚构类");
        Box3Fragment.inject((AppCompatActivity) getActivity(), R.id.framelayout_book_nonfiction, SubjectCollectionType.book_nonfiction, "最受关注图书|非虚构类");
        Box3Fragment.inject((AppCompatActivity) getActivity(), R.id.framelayout_book_bestseller, SubjectCollectionType.book_bestseller, "畅销图书榜");
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tv_filter_book_fiction_hot.setOnClickListener(v -> SubjectCollectionActivity.start(getActivity(), SubjectCollectionType.filter_book_fiction_hot));
        tv_filter_book_love_hot.setOnClickListener(v -> SubjectCollectionActivity.start(getActivity(), SubjectCollectionType.filter_book_love_hot));
        tv_filter_book_history_hot.setOnClickListener(v -> SubjectCollectionActivity.start(getActivity(), SubjectCollectionType.filter_book_history_hot));
        tv_filter_book_foreign_hot.setOnClickListener(v -> SubjectCollectionActivity.start(getActivity(), SubjectCollectionType.filter_book_foreign_hot));
        tv_filter_book_youth_hot.setOnClickListener(v -> SubjectCollectionActivity.start(getActivity(), SubjectCollectionType.filter_book_youth_hot));
    }
}
