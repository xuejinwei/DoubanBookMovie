package com.xuejinwei.doubanbookmovie.doubanbookmovie.ui.fragment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.twiceyuan.commonadapter.library.adapter.CommonAdapter;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.R;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.adapter.SubjectCollectionHolder;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.model.SubjectCollectionResult;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.ui.base.fragment.PageRecyclerViewFragment;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by xuejinwei on 16/4/14.
 * Email:xuejinwei@outlook.com
 */
public class SubjectCollectionFragment extends PageRecyclerViewFragment {
    public static final String subject_collection_item = "subject_collection_item";
    public String mType;

    public static SubjectCollectionFragment inject(AppCompatActivity activity, int containerId, String type) {

        SubjectCollectionFragment fragment = new BaseFragmentBuilder()
                .put(args -> args.putSerializable(subject_collection_item, type))
                .build(SubjectCollectionFragment.class);

        activity.getSupportFragmentManager().beginTransaction()
                .add(containerId, fragment)
                .commit();
        return fragment;
    }

    @Override
    public int provideLayoutId() {
        return R.layout.fragment_subject_collection;
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
        return mApiWrapper.getSubjectCollection(mType, start, count).map((Func1<SubjectCollectionResult, List>) subjectCollectionResult -> {
            mTitle = subjectCollectionResult.subject_collection.name;
            return subjectCollectionResult.subject_collection_items;
        });
    }

    @Override
    public CommonAdapter provideAdapter() {
        return new CommonAdapter<>(getActivity(), SubjectCollectionHolder.class);
    }

    @Override
    public int provideEmptyViewId() {
        return R.id.emptyView;
    }

    @Override
    public void getArguments(Bundle args) {
        mType = (String) args.getSerializable(subject_collection_item);
    }

}
