package com.xuejinwei.doubanbookmovie.doubanbookmovie.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.twiceyuan.commonadapter.library.LayoutId;
import com.twiceyuan.commonadapter.library.ViewId;
import com.twiceyuan.commonadapter.library.holder.CommonHolder;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.R;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.model.MovieSimple;

/**
 * Created by xuejinwei on 16/3/28.
 * Email:xuejinwei@outlook.com
 */
@LayoutId(R.layout.item_movie_list)
public class MovieListHolder extends CommonHolder<MovieSimple> {

    // 绑定 View 资源
    @ViewId(R.id.iv_avatar) ImageView iv_avatar;
    @ViewId(R.id.tv_title)  TextView  tv_title;

    public MovieListHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void bindData(MovieSimple movieSimple) {
        Glide.with(getItemView().getContext()).load(movieSimple.images.large).into(iv_avatar);
        tv_title.setText(movieSimple.title);

    }
}
