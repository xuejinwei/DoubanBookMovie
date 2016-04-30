package com.xuejinwei.doubanbookmovie.doubanbookmovie.ui.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.github.siyamed.shapeimageview.CircularImageView;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.R;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.app.App;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.app.Setting;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.ui.activity.AuthActivity;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.ui.activity.BookCollectionListActivity;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.ui.base.fragment.BaseFragment;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.util.CommonUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by xuejinwei on 16/4/14.
 * Email:xuejinwei@outlook.com
 */
public class MyFragment extends BaseFragment {

    @Bind(R.id.iv_avatar)           CircularImageView iv_avatar;
    @Bind(R.id.tv_title)            TextView          tv_title;
    @Bind(R.id.tv_description)      TextView          tv_description;
    @Bind(R.id.ll_root_my)          LinearLayout      ll_root_my;
    @Bind(R.id.imagehead_bg)        ImageView         imagehead_bg;
    @Bind(R.id.ll_book_collection)  LinearLayout      ll_book_collection;
    @Bind(R.id.ll_movie_collection) LinearLayout      ll_movie_collection;
    @Bind(R.id.btn_login)           Button            btn_login;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onRefresh();

    }

    private void onRefresh() {
        if (Setting.getAuthState()) {
            ll_root_my.setVisibility(View.VISIBLE);
            btn_login.setVisibility(View.GONE);
            if (!Setting.getSetting(Setting.Key.large_avatar, "").equals("")) {
                Glide.with(App.getApp()).load(Setting.getSetting(Setting.Key.large_avatar, "")).asBitmap().into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        iv_avatar.setImageBitmap(resource);
                    }
                });
            }
            CommonUtil.blur(imagehead_bg, Setting.getSetting(Setting.Key.large_avatar, ""));
            tv_title.setText(Setting.getSetting(Setting.Key.douban_user_name, ""));
            tv_description.setText(Setting.getSetting(Setting.Key.desc, ""));
            ll_book_collection.setOnClickListener(v -> startActivity(new Intent(getActivity(), BookCollectionListActivity.class)));
        } else {
            ll_root_my.setVisibility(View.GONE);
            btn_login.setVisibility(View.VISIBLE);
            btn_login.setOnClickListener(v -> AuthActivity.start(getActivity()));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
