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

import org.apmem.tools.layouts.FlowLayout;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by xuejinwei on 16/4/19.
 * Email:xuejinwei@outlook.com
 */
public class ClassFragment extends BaseFragment {

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

    @Bind(R.id.flow_book)  FlowLayout flow_book;
    @Bind(R.id.flow_movie) FlowLayout flow_movie;

    @Bind(R.id.tv_filter_movie_classic_hot)    TextView tv_filter_movie_classic_hot;
    @Bind(R.id.tv_filter_movie_unpopular_hot)  TextView tv_filter_movie_unpopular_hot;
    @Bind(R.id.tv_filter_movie_score_hot)      TextView tv_filter_movie_score_hot;
    @Bind(R.id.tv_filter_movie_action_hot)     TextView tv_filter_movie_action_hot;
    @Bind(R.id.tv_filter_movie_comedy_hot)     TextView tv_filter_movie_comedy_hot;
    @Bind(R.id.tv_filter_movie_love_hot)       TextView tv_filter_movie_love_hot;
    @Bind(R.id.tv_filter_movie_mystery_hot)    TextView tv_filter_movie_mystery_hot;
    @Bind(R.id.tv_filter_movie_horror_hot)     TextView tv_filter_movie_horror_hot;
    @Bind(R.id.tv_filter_movie_sci_fi_hot)     TextView tv_filter_movie_sci_fi_hot;
    @Bind(R.id.tv_filter_movie_cure_hot)       TextView tv_filter_movie_cure_hot;
    @Bind(R.id.tv_filter_movie_literature_hot) TextView tv_filter_movie_literature_hot;
    @Bind(R.id.tv_filter_movie_growth_hot)     TextView tv_filter_movie_growth_hot;
    @Bind(R.id.tv_filter_movie_cartoon_hot)    TextView tv_filter_movie_cartoon_hot;
    @Bind(R.id.tv_filter_movie_chinese_hot)    TextView tv_filter_movie_chinese_hot;
    @Bind(R.id.tv_filter_movie_occident_hot)   TextView tv_filter_movie_occident_hot;
    @Bind(R.id.tv_filter_movie_korea_hot)      TextView tv_filter_movie_korea_hot;
    @Bind(R.id.tv_filter_movie_japan_hot)      TextView tv_filter_movie_japan_hot;

    public ClassFragment.Type mType;

    public ClassFragment(Type type) {
        this.mType = type;
    }

    public enum Type {
        MOVIE,      // 正在热映
        BOOK     // 即将上映
    }

    public static ClassFragment inject(AppCompatActivity activity, int containerId, ClassFragment.Type type) {
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        ClassFragment fragment = new ClassFragment(type);
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
        switch (mType) {
            case BOOK:
                flow_book.setVisibility(View.VISIBLE);
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
                break;
            case MOVIE:
                flow_movie.setVisibility(View.VISIBLE);
                tv_filter_movie_classic_hot.setOnClickListener(v -> SubjectCollectionActivity.start(getActivity(), SubjectCollectionType.filter_movie_classic_hot));
                tv_filter_movie_unpopular_hot.setOnClickListener(v -> SubjectCollectionActivity.start(getActivity(), SubjectCollectionType.filter_movie_unpopular_hot));
                tv_filter_movie_score_hot.setOnClickListener(v -> SubjectCollectionActivity.start(getActivity(), SubjectCollectionType.filter_movie_score_hot));
                tv_filter_movie_action_hot.setOnClickListener(v -> SubjectCollectionActivity.start(getActivity(), SubjectCollectionType.filter_movie_action_hot));
                tv_filter_movie_comedy_hot.setOnClickListener(v -> SubjectCollectionActivity.start(getActivity(), SubjectCollectionType.filter_movie_comedy_hot));
                tv_filter_movie_love_hot.setOnClickListener(v -> SubjectCollectionActivity.start(getActivity(), SubjectCollectionType.filter_movie_love_hot));
                tv_filter_movie_mystery_hot.setOnClickListener(v -> SubjectCollectionActivity.start(getActivity(), SubjectCollectionType.filter_movie_mystery_hot));
                tv_filter_movie_horror_hot.setOnClickListener(v -> SubjectCollectionActivity.start(getActivity(), SubjectCollectionType.filter_movie_horror_hot));
                tv_filter_movie_sci_fi_hot.setOnClickListener(v -> SubjectCollectionActivity.start(getActivity(), SubjectCollectionType.filter_movie_sci_fi_hot));
                tv_filter_movie_cure_hot.setOnClickListener(v -> SubjectCollectionActivity.start(getActivity(), SubjectCollectionType.filter_movie_cure_hot));
                tv_filter_movie_literature_hot.setOnClickListener(v -> SubjectCollectionActivity.start(getActivity(), SubjectCollectionType.filter_movie_literature_hot));
                tv_filter_movie_growth_hot.setOnClickListener(v -> SubjectCollectionActivity.start(getActivity(), SubjectCollectionType.filter_movie_growth_hot));
                tv_filter_movie_cartoon_hot.setOnClickListener(v -> SubjectCollectionActivity.start(getActivity(), SubjectCollectionType.filter_movie_cartoon_hot));
                tv_filter_movie_chinese_hot.setOnClickListener(v -> SubjectCollectionActivity.start(getActivity(), SubjectCollectionType.filter_movie_chinese_hot));
                tv_filter_movie_occident_hot.setOnClickListener(v -> SubjectCollectionActivity.start(getActivity(), SubjectCollectionType.filter_movie_occident_hot));
                tv_filter_movie_korea_hot.setOnClickListener(v -> SubjectCollectionActivity.start(getActivity(), SubjectCollectionType.filter_movie_korea_hot));
                tv_filter_movie_japan_hot.setOnClickListener(v -> SubjectCollectionActivity.start(getActivity(), SubjectCollectionType.filter_movie_japan_hot));

                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
