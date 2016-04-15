package com.xuejinwei.doubanbookmovie.doubanbookmovie.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.twiceyuan.commonadapter.library.adapter.CommonAdapter;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.R;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.adapter.SubjectCollectionHolder;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.model.SubjectCollectionItems;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.ui.activity.SubjectCollectionActivity;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.ui.base.fragment.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by xuejinwei on 16/4/14.
 * Email:xuejinwei@outlook.com
 */
public class Box3Fragment extends BaseFragment {

    @Bind(R.id.tv_title) TextView     tv_title;
    @Bind(R.id.ll_movie) LinearLayout ll_movie;
    @Bind(R.id.rv_box3)  RecyclerView rv_box3;

    private CommonAdapter<SubjectCollectionItems, SubjectCollectionHolder> mSubjectCollectionAdapterBookLatest;
    private String                                                         mSubjectCollectionType, mTitle;

    /**
     * @param activity              activity
     * @param containerId           要注入的layout的id
     * @param subjectCollectionType 类型，{@link com.xuejinwei.doubanbookmovie.doubanbookmovie.model.SubjectCollectionType}
     * @param title                 mTitle
     * @return 返回的fragment注入到containerId中
     */
    public static Box3Fragment inject(AppCompatActivity activity, int containerId, String subjectCollectionType, String title) {
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        Box3Fragment fragment = new Box3Fragment(subjectCollectionType, title);
        transaction.add(containerId, fragment);
        transaction.commit();
        return fragment;
    }

    public Box3Fragment(String subjectCollectionType, String title) {
        this.mSubjectCollectionType = subjectCollectionType;
        this.mTitle = title;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_box3, container, false);
        ButterKnife.bind(this, view);
        mSubjectCollectionAdapterBookLatest = new CommonAdapter<>(getActivity(), SubjectCollectionHolder.class);
        ll_movie.setVisibility(View.GONE);
        initRecyclerView(rv_box3, mSubjectCollectionAdapterBookLatest);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tv_title.setText(mTitle);
        runRxTaskOnUi(mApiWrapper.getSubjectCollection(mSubjectCollectionType, 0, 3), subjectCollectionResult -> {
            if (subjectCollectionResult.subject_collection_items.size() == 3) {
                mSubjectCollectionAdapterBookLatest.clear();
                mSubjectCollectionAdapterBookLatest.addAll(subjectCollectionResult.subject_collection_items);
                mSubjectCollectionAdapterBookLatest.notifyDataSetChanged();
                onRefreshSuccess();
            }
        });
        ll_movie.setOnClickListener(v -> SubjectCollectionActivity.start(getActivity(), mSubjectCollectionType));
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
        ll_movie.setVisibility(View.VISIBLE);
    }
}
