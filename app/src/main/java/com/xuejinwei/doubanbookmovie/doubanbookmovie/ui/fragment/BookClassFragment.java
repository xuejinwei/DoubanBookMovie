package com.xuejinwei.doubanbookmovie.doubanbookmovie.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xuejinwei.doubanbookmovie.doubanbookmovie.R;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.model.SubjectCollectionType;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.ui.activity.SubjectCollectionActivity;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.ui.base.fragment.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by xuejinwei on 16/4/19.
 * Email:xuejinwei@outlook.com
 */
public class BookClassFragment extends BaseFragment {

    @Bind(R.id.tv_filter_book_fiction_hot)     TextView tv_filter_book_fiction_hot;
    @Bind(R.id.tv_filter_book_love_hot)        TextView tv_filter_book_love_hot;
    @Bind(R.id.tv_filter_book_history_hot)     TextView tv_filter_book_history_hot;
    @Bind(R.id.tv_filter_book_foreign_hot)     TextView tv_filter_book_foreign_hot;
    @Bind(R.id.tv_filter_book_youth_hot)       TextView tv_filter_book_youth_hot;
    @Bind(R.id.tv_filter_book_inspiration_hot) TextView tv_filter_book_inspiration_hot;
    @Bind(R.id.tv_filter_book_essay_hot)       TextView tv_filter_book_essay_hot;
    @Bind(R.id.tv_filter_book_biography_hot)   TextView tv_filter_book_biography_hot;
    @Bind(R.id.tv_filter_book_detective_hot)   TextView tv_filter_book_detective_hot;
    @Bind(R.id.tv_filter_book_travel_hot)      TextView tv_filter_book_travel_hot;
    @Bind(R.id.tv_filter_book_fantasy_hot)     TextView tv_filter_book_fantasy_hot;
    @Bind(R.id.tv_filter_book_economic_hot)    TextView tv_filter_book_economic_hot;

    public static BookClassFragment inject(AppCompatActivity activity, int containerId) {
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        BookClassFragment fragment = new BookClassFragment();
        transaction.add(containerId, fragment);
        transaction.commit();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_class, container, false);
        ButterKnife.bind(this, view);
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
        tv_filter_book_inspiration_hot.setOnClickListener(v -> SubjectCollectionActivity.start(getActivity(), SubjectCollectionType.filter_book_inspiration_hot));
        tv_filter_book_essay_hot.setOnClickListener(v -> SubjectCollectionActivity.start(getActivity(), SubjectCollectionType.filter_book_essay_hot));
        tv_filter_book_biography_hot.setOnClickListener(v -> SubjectCollectionActivity.start(getActivity(), SubjectCollectionType.filter_book_biography_hot));
        tv_filter_book_detective_hot.setOnClickListener(v -> SubjectCollectionActivity.start(getActivity(), SubjectCollectionType.filter_book_detective_hot));
        tv_filter_book_travel_hot.setOnClickListener(v -> SubjectCollectionActivity.start(getActivity(), SubjectCollectionType.filter_book_travel_hot));
        tv_filter_book_fantasy_hot.setOnClickListener(v -> SubjectCollectionActivity.start(getActivity(), SubjectCollectionType.filter_book_fantasy_hot));
        tv_filter_book_economic_hot.setOnClickListener(v -> SubjectCollectionActivity.start(getActivity(), SubjectCollectionType.filter_book_economic_hot));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
