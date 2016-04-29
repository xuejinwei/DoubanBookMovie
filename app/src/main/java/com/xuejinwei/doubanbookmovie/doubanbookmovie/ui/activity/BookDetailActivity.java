package com.xuejinwei.doubanbookmovie.doubanbookmovie.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.R;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.model.Book;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.ui.base.activity.SwipeBackActivity;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.ui.fragment.DetailFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.functions.Action1;

/**
 * Created by xuejinwei on 16/4/19.
 * Email:xuejinwei@outlook.com
 */
public class BookDetailActivity extends SwipeBackActivity {

    @Bind(R.id.ivImage)            ImageView               ivImage;
    @Bind(R.id.collapsing_toolbar) CollapsingToolbarLayout collapsing_toolbar;
    @Bind(R.id.sliding_tabs)       TabLayout               sliding_tabs;
    @Bind(R.id.viewpager)          ViewPager               viewpager;
    @Bind(R.id.fab_collection)     FloatingActionButton    fab_collection;
    private                        Book                    mBook;
    private static final String BOOK_ID = "book_id";

    public static void start(Activity activity, String book_id) {
        Intent starter = new Intent(activity, BookDetailActivity.class);
        starter.putExtra(BOOK_ID, book_id);
        activity.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        ButterKnife.bind(this);
        String book_id = getIntent().getStringExtra(BOOK_ID);
        runRxTaskOnUi(mApiWrapper.getBookById(book_id), new Action1<Book>() {
            @Override
            public void call(Book book) {
                upDate(book);
            }
        });
        fab_collection.setOnClickListener(v -> BookCollectionDetailEditActivity.start(this, BookCollectionDetailEditActivity.Type.ADD, book_id));

    }

    public void upDate(Book book) {
        mBook = book;
        collapsing_toolbar.setTitle(mBook.title);

        Glide.with(BookDetailActivity.this)
                .load(mBook.images.large)
                .fitCenter()
                .into(ivImage);
        setupViewPager(viewpager);
        sliding_tabs.addTab(sliding_tabs.newTab().setText("内容简介"));
        sliding_tabs.addTab(sliding_tabs.newTab().setText("作者简介"));
        sliding_tabs.addTab(sliding_tabs.newTab().setText("目录"));
        sliding_tabs.setupWithViewPager(viewpager);
    }

    private void setupViewPager(ViewPager mViewPager) {
        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(DetailFragment.newInstance(mBook.summary), "内容简介");
        adapter.addFragment(DetailFragment.newInstance(mBook.author_intro), "作者简介");
        adapter.addFragment(DetailFragment.newInstance(mBook.catalog), "目录");
        mViewPager.setAdapter(adapter);
    }


    static class MyPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments      = new ArrayList<>();
        private final List<String>   mFragmentTitles = new ArrayList<>();

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }
}
