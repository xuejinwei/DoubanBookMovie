package com.xuejinwei.doubanbookmovie.doubanbookmovie.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xuejinwei.doubanbookmovie.doubanbookmovie.R;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.model.SubjectCollectionType;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.ui.base.fragment.BaseFragment;

import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class BookFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book, container, false);
        ButterKnife.bind(this, view);
        Box3Fragment.inject((AppCompatActivity) getActivity(), R.id.ll_fragment_book_root, SubjectCollectionType.book_latest, "新书速递");
        Box3Fragment.inject((AppCompatActivity) getActivity(), R.id.ll_fragment_book_root, SubjectCollectionType.book_fiction, "最受关注图书|虚构类");
        Box3Fragment.inject((AppCompatActivity) getActivity(), R.id.ll_fragment_book_root, SubjectCollectionType.book_nonfiction, "最受关注图书|非虚构类");
        Box3Fragment.inject((AppCompatActivity) getActivity(), R.id.ll_fragment_book_root, SubjectCollectionType.book_bestseller, "畅销图书榜");
        Box3Fragment.inject((AppCompatActivity) getActivity(), R.id.ll_fragment_book_root, SubjectCollectionType.ebook_hot, "热门电子书");
        return view;
    }

}
