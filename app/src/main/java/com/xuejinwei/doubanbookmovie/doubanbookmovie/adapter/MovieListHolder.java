package com.xuejinwei.doubanbookmovie.doubanbookmovie.adapter;

import android.support.v7.widget.AppCompatRatingBar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
    @ViewId(R.id.iv_avatar)    ImageView          iv_avatar;
    @ViewId(R.id.tv_title)     TextView           tv_title;
    @ViewId(R.id.ll_rating)    LinearLayout       ll_rating;
    @ViewId(R.id.ratingBar)    AppCompatRatingBar mRatingBar;
    @ViewId(R.id.tv_rating)    TextView           tv_rating;
    @ViewId(R.id.tv_genres)    TextView           tv_genres;
    @ViewId(R.id.tv_directors) TextView           tv_directors;
    @ViewId(R.id.tv_casts)     TextView           tv_casts;

    public MovieListHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void bindData(MovieSimple movieSimple) {
        Glide.with(getItemView().getContext()).load(movieSimple.images.large).into(iv_avatar);
        tv_title.setText(movieSimple.title);
        if (!movieSimple.rating.average.equals("0")) {
            tv_rating.setText(movieSimple.rating.average);
            mRatingBar.setRating(Float.parseFloat(movieSimple.rating.average));
            ll_rating.setVisibility(View.VISIBLE);
        } else {
            ll_rating.setVisibility(View.GONE);
        }
        tv_genres.setText(movieSimple.getGenres());
        tv_directors.setText(movieSimple.getDirectors());
        tv_casts.setText(movieSimple.getCasts());

    }
}
