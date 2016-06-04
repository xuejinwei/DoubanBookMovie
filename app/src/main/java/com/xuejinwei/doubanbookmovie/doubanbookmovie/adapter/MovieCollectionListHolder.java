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
import com.xuejinwei.doubanbookmovie.doubanbookmovie.model.MovieCollections;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.util.DateUtil;

/**
 * Created by xuejinwei on 16/5/17.
 * Email:xuejinwei@outlook.com
 */
@LayoutId(R.layout.item_book_collection_list)
public class MovieCollectionListHolder extends CommonHolder<MovieCollections> {

    // 绑定 View 资源
    @ViewId(R.id.iv_avatar) ImageView          iv_avatar;
    @ViewId(R.id.tv_title)  TextView           tv_title;
    @ViewId(R.id.ll_rating) LinearLayout       ll_rating;
    @ViewId(R.id.ratingBar) AppCompatRatingBar mRatingBar;
    @ViewId(R.id.tv_rating) TextView           tv_rating;
    @ViewId(R.id.tv_commit) TextView           tv_commit;
    @ViewId(R.id.tv_update) TextView           tv_update;
    @ViewId(R.id.iv_status) ImageView          iv_status;

    public MovieCollectionListHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void bindData(MovieCollections movieCollections) {
        Glide.with(getItemView().getContext()).load(movieCollections.getMovieImg()).crossFade().into(iv_avatar);
        tv_title.setText(movieCollections.getMovieName());
        tv_rating.setText(String.valueOf(movieCollections.getMovieRating()));
        mRatingBar.setRating(movieCollections.getMovieRating());

        tv_commit.setText(movieCollections.getMovieCollectionText());
        tv_update.setText(DateUtil.translateDate(movieCollections.getCreatedAt().getTime()));
        iv_status.setImageResource(getStatus(movieCollections));
    }

    int getStatus(MovieCollections movieCollections) {
        if (movieCollections.getMovieCollectionType()==0) {
            return R.drawable.img_wish_m;
        }
        if (movieCollections.getMovieCollectionType()==1) {
            return R.drawable.img_reading_m;
        }
        if (movieCollections.getMovieCollectionType()==2) {
            return R.drawable.img_readed_m;
        }

        return -1;
    }
}
