package com.xuejinwei.doubanbookmovie.kotlin.adapter;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.twiceyuan.commonadapter.library.LayoutId;
import com.twiceyuan.commonadapter.library.ViewId;
import com.twiceyuan.commonadapter.library.holder.CommonHolder;
import com.xuejinwei.doubanbookmovie.kotlin.R;
import com.xuejinwei.doubanbookmovie.kotlin.model.MovieSimple;
import com.xuejinwei.doubanbookmovie.kotlin.ui.activity.MovieDetailActivity;
import com.xuejinwei.doubanbookmovie.kotlin.util.DensityUtil;

/**
 * Created by xuejinwei on 16/3/25.
 * MovieBox 的holder
 */
@LayoutId(R.layout.item_movie_box)
public class MovieBoxHolder extends CommonHolder<MovieSimple> {

    // 绑定 View 资源
    @ViewId(R.id.img_avatar)   ImageView      img_avatar;
    @ViewId(R.id.tv_title)     TextView       tv_title;
    @ViewId(R.id.ratingBar)    RatingBar      ratingBar;
    @ViewId(R.id.tv_rating)    TextView       tv_rating;
    @ViewId(R.id.rl_root_view) RelativeLayout rl_root_view;
    static                     int            img_weigth;
    static                     int            img_heigh;

    public MovieBoxHolder(View itemView) {
        super(itemView);
        /**
         *  图片的高度，是 图片宽度的1.5倍，宽度是 屏幕宽度减去padding等的三分之一
         */
        img_weigth = (int) ((DensityUtil.getScreenW(getItemView().getContext())) / 3.5);
        img_heigh = (int) ((DensityUtil.getScreenW(getItemView().getContext())) / 2.2);

    }

    @Override
    public void bindData(MovieSimple movieSimple) {
        rl_root_view.setLayoutParams(new RelativeLayout.LayoutParams(img_weigth, img_heigh));
        Glide.with(getItemView().getContext()).load(movieSimple.images.large).crossFade().into(img_avatar);
        tv_title.setText(movieSimple.title);
        if (movieSimple.rating.average.equals("0")) {
            tv_rating.setText("暂无评分");
            ratingBar.setVisibility(View.GONE);
        } else {
            tv_rating.setText(movieSimple.rating.average);
            ratingBar.setRating(Float.parseFloat(movieSimple.rating.average) / 2);
            ratingBar.setVisibility(View.VISIBLE);
        }
        getItemView().setOnClickListener(v -> MovieDetailActivity.start((AppCompatActivity) getItemView().getContext(), movieSimple.id));
    }
}
