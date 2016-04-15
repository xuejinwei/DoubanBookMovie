package com.xuejinwei.doubanbookmovie.doubanbookmovie.ui.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.github.siyamed.shapeimageview.CircularImageView;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.R;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.app.App;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.app.Setting;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.ui.base.fragment.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by xuejinwei on 16/4/14.
 * Email:xuejinwei@outlook.com
 */
public class MyFragment extends BaseFragment {

    @Bind(R.id.iv_avatar)      CircularImageView iv_avatar;
    @Bind(R.id.tv_title)       TextView          tv_title;
    @Bind(R.id.tv_description) TextView          tv_description;
    @Bind(R.id.ll_root_my)     LinearLayout      ll_root_my;

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
        if (Setting.getAuthState()) {
            if (!Setting.getSetting(Setting.Key.large_avatar, "").equals("")) {
                Glide.with(App.getApp()).load(Setting.getSetting(Setting.Key.large_avatar, "")).asBitmap().into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        iv_avatar.setImageBitmap(resource);
                    }
                });
            }
            tv_title.setText(Setting.getSetting(Setting.Key.douban_user_name, ""));
            tv_description.setText(Setting.getSetting(Setting.Key.desc, ""));
        } else {
            iv_avatar.setImageBitmap(null);
            tv_title.setText("");
            tv_description.setText("");
        }
    }
}
