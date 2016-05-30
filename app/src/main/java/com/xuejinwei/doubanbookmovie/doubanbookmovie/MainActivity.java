package com.xuejinwei.doubanbookmovie.doubanbookmovie;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.siyamed.shapeimageview.CircularImageView;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.app.Setting;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.ui.activity.AuthActivity;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.ui.activity.SearchActivity;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.ui.base.activity.BackActivity;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.ui.fragment.BookFragment;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.ui.fragment.MovieFragment;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.ui.fragment.MyFragment;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.util.CommonUtil;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.widget.DialogUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BackActivity {

    @Bind(R.id.tabLayout)     TabLayout      tabLayout;
    @Bind(R.id.viewPager)     ViewPager      viewPager;
    @Bind(R.id.navigation)    NavigationView navigation;
    @Bind(R.id.drawer_layout) DrawerLayout   drawer_layout;

    TextView          tv_header_title;
    TextView          tv_header_login;
    TextView          tv_header_desc;
    CircularImageView img_header_avatars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        View headview = navigation.getHeaderView(0);
        tv_header_title = (TextView) headview.findViewById(R.id.tv_header_title);
        tv_header_login = (TextView) headview.findViewById(R.id.tv_header_login);
        tv_header_desc = (TextView) headview.findViewById(R.id.tv_header_description);
        img_header_avatars = (CircularImageView) headview.findViewById(R.id.iv_header_avatar);

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawer_layout, mToolbar, R.string.drawer_open,
                R.string.drawer_close);
        drawerToggle.syncState();
        drawer_layout.addDrawerListener(drawerToggle);
        setupDrawerContent(navigation);


        tv_header_login.setOnClickListener(v -> {
            if (Setting.getAuthState()) {
                DialogUtil.simpleMessage(this, "确定注销？", () -> {
                    Setting.Logout();
                    refresh();
                    CommonUtil.toast("注销成功");
                });
            } else {
                AuthActivity.start(MainActivity.this);
            }
            if (drawer_layout.isDrawerOpen(Gravity.LEFT)) {
                drawer_layout.closeDrawer(Gravity.LEFT);
            }
        });
        refresh();

    }

    private void refresh() {
        if (Setting.getAuthState()) {
            if (!Setting.getSetting(Setting.Key.large_avatar, "").equals("")) {
                Glide.with(this).load(Setting.getSetting(Setting.Key.large_avatar, "")).asBitmap().into(img_header_avatars);
            }
            tv_header_title.setText(Setting.getSetting(Setting.Key.douban_user_name, ""));
            tv_header_desc.setText(Setting.getSetting(Setting.Key.desc, ""));
            tv_header_login.setText("注销");
        } else {
            img_header_avatars.setImageResource(R.drawable.default_avatar);
            tv_header_title.setText("");
            tv_header_desc.setText("");
            tv_header_login.setText("登录");
        }
        if (viewPager != null) {
            setupViewPager(viewPager);
        }
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK)
            refresh();
    }

    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(new MovieFragment(), "电影");
        adapter.addFragment(new BookFragment(), "读书");
        adapter.addFragment(new MyFragment(), "我的");
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(adapter.getCount());
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                menuItem -> {
                    switch (menuItem.getItemId()) {

                        case R.id.navigation_movies:
                            selectPage(0);
                            menuItem.setChecked(true);
                            break;
                        case R.id.navigation_book:
                            selectPage(1);
                            menuItem.setChecked(true);
                            break;
                        case R.id.navigation_my:
                            selectPage(2);
                            menuItem.setChecked(true);
                            break;

                    }
                    drawer_layout.closeDrawers();
                    return true;
                });
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

    void selectPage(int pageIndex) {
        tabLayout.setScrollPosition(pageIndex, 0f, true);
        viewPager.setCurrentItem(pageIndex);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_search) {
            startActivity(new Intent(this, SearchActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (drawer_layout.isDrawerOpen(Gravity.LEFT)) {
            drawer_layout.closeDrawer(Gravity.LEFT);
        } else {
            doExitApp();
        }
    }

    private long exitTime = 0;

    public void doExitApp() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            CommonUtil.toast("再按一次退出");
            exitTime = System.currentTimeMillis();
        } else {
            finish();
        }
    }
}
