package com.xuejinwei.doubanbookmovie.doubanbookmovie.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.xuejinwei.doubanbookmovie.doubanbookmovie.R;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.ui.base.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class BookFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    @Bind(R.id.et_search)
    EditText           et_search;
    @Bind(R.id.ll_more)
    LinearLayout       ll_more;
    @Bind(R.id.rv_book_hot)
    RecyclerView       rv_book_hot;
    @Bind(R.id.swipe_book_hot)
    SwipeRefreshLayout swipe_book_hot;
//    private BookBoxAdapter mBookBoxAdapter;

    public BookFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_book, container, false);
        ButterKnife.bind(this, view);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);

//        mBookBoxAdapter = new BookBoxAdapter(getActivity());
//
//        rv_book_hot.setLayoutManager(gridLayoutManager);
//        rv_book_hot.setItemAnimator(new DefaultItemAnimator());
//        rv_book_hot.setAdapter(mBookBoxAdapter);
//        swipe_book_hot.setColorSchemeResources(R.color.google_green, R.color.google_red, R.color.google_blue, R.color.google_yellow);
//        swipe_book_hot.setOnRefreshListener(this);
//        swipe_book_hot.post(new Runnable() {
//            @Override
//            public void run() {
//                onRefresh();
//            }
//        });
//        et_search.setOnEditorActionListener(new EditText.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
//                    if (!et_search.getText().toString().equals("")) {
//                        String inputText = et_search.getText().toString();
//                        inputText = inputText.replace(" ", "\b");
//                        BookListActivity.start(getActivity(), inputText);
//                    } else {
//                        Toast.makeText(getActivity(), "输入不能为空", Toast.LENGTH_SHORT).show();
//                    }
//                    return true;
//                }
//                return false;
//            }
//        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onRefresh() {
        swipe_book_hot.setRefreshing(true);
//        RxUtils.callOnUI(mApiWrapper.getUserBookCollections())
//                .subscribe(new Action1<List<BookCollections>>() {
//                    @Override
//                    public void call(List<BookCollections> bookList) {
//                        upData(bookList);
//                        swipe_book_hot.setRefreshing(false);
//                    }
//                }, new Action1<Throwable>() {
//                    @Override
//                    public void call(Throwable throwable) {
//                        FlatHandler.handleError(throwable);
//                        swipe_book_hot.setRefreshing(false);
//                    }
//                });
    }

//    public void upData(List<BookCollections> bookList) {
//        mBookBoxAdapter.clear();
//        for (int i = 0; i < bookList.size(); i++) {
//            mBookBoxAdapter.add(bookList.get(i));
//        }
//        mBookBoxAdapter.notifyDataSetChanged();
//    }
}
