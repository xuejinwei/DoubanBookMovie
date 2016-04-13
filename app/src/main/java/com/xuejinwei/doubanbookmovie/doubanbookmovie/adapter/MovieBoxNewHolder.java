package com.xuejinwei.doubanbookmovie.doubanbookmovie.adapter;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.twiceyuan.commonadapter.library.LayoutId;
import com.twiceyuan.commonadapter.library.ViewId;
import com.twiceyuan.commonadapter.library.holder.CommonHolder;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.R;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.model.MovieSimple;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.ui.activity.MovieDetailActivity;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.util.DensityUtil;

/**
 * Created by xuejinwei on 16/4/12.
 * Email:xuejinwei@outlook.com
 */
@LayoutId(R.layout.item_movie_box_api)
public class MovieBoxNewHolder extends CommonHolder<MovieSimple> {

    // 绑定 View 资源
    @ViewId(R.id.img_avatar)   ImageView      img_avatar;
    @ViewId(R.id.tv_title)     TextView       tv_title;
    @ViewId(R.id.ratingBar)    RatingBar      ratingBar;
    @ViewId(R.id.tv_rating)    TextView       tv_rating;
    @ViewId(R.id.rl_root_view) RelativeLayout rl_root_view;
    static                     int            img_heigh;

    public MovieBoxNewHolder(View itemView) {
        super(itemView);
        /**
         *  图片的高度，是 图片宽度的1.5倍，宽度是 屏幕宽度减去padding等的三分之一
         */
        img_heigh = (int) ((DensityUtil.getScreenW(getItemView().getContext())) / 1.95);

    }

    @Override
    public void bindData(MovieSimple movieSimple) {
        rl_root_view.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, img_heigh));
        Glide.with(getItemView().getContext()).load(movieSimple.images.large).into(img_avatar);
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
