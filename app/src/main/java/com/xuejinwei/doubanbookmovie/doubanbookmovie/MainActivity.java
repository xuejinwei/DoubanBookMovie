package com.xuejinwei.doubanbookmovie.doubanbookmovie;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;

import com.xuejinwei.doubanbookmovie.doubanbookmovie.ui.base.activity.AbsToolbarActivity;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.ui.fragment.BookFragment;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.ui.fragment.MovieFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AbsToolbarActivity {

    @Bind(R.id.toolbar)      Toolbar      toolbar;
    @Bind(R.id.tabLayout)    TabLayout    tabLayout;
    @Bind(R.id.viewPager)    ViewPager    viewPager;
    @Bind(R.id.main_content) LinearLayout main_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setTitle("");
        if (viewPager != null) {
            setupViewPager(viewPager);
        }
        tabLayout.setupWithViewPager(viewPager);

//        btn_click.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                RxUtils.callOnUI(mApiWrapper.getMovieInTheaters(0, 1))
//                        .subscribe(new Action1<List<MovieSimple>>() {
//                            @Override
//                            public void call(List<MovieSimple> movieSimples) {
//                                List<MovieSimple> movieSimpleList = movieSimples;
//                                Toast.makeText(MainActivity.this, "aa", Toast.LENGTH_SHORT).show();
//                            }
//                        });
//            }
//        });
    }

    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(new MovieFragment(), "电影");
        adapter.addFragment(new BookFragment(), "我的图书");
        viewPager.setAdapter(adapter);
    }

    /**
     * fragment adapter
     */
    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments      = new ArrayList<>();
        private final List<String>   mFragmentTitles = new ArrayList<>();

        public Adapter(FragmentManager fm) {
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
