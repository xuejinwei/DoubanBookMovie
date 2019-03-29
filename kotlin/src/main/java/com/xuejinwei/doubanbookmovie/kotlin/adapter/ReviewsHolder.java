package com.xuejinwei.doubanbookmovie.kotlin.adapter;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.github.siyamed.shapeimageview.CircularImageView;
import com.twiceyuan.commonadapter.library.LayoutId;
import com.twiceyuan.commonadapter.library.ViewId;
import com.twiceyuan.commonadapter.library.holder.CommonHolder;
import com.xuejinwei.doubanbookmovie.kotlin.R;
import com.xuejinwei.doubanbookmovie.kotlin.model.Reviews;
import com.xuejinwei.doubanbookmovie.kotlin.ui.activity.ReviewsDetailActivity;

/**
 * Created by xuejinwei on 16/5/5.
 * Email:xuejinwei@outlook.com
 * 影评holder
 */
@LayoutId(R.layout.item_reviews)
public class ReviewsHolder extends CommonHolder<Reviews> {

    // 绑定 View 资源
    @ViewId(R.id.tv_title)   TextView          tv_title;
    @ViewId(R.id.rb_comment) RatingBar         rb_comment;
    @ViewId(R.id.iv_comment) CircularImageView iv_comment;
    @ViewId(R.id.tv_vote)    TextView          tv_vote;

    public ReviewsHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void bindData(Reviews comments) {
        tv_title.setText(comments.title);
        Glide.with(getItemView().getContext()).load(comments.img).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                iv_comment.setImageBitmap(resource);
            }
        });

        int rating = comments.rating;
        rb_comment.setRating(rating);
        tv_vote.setText(comments.useful);

        getItemView().setOnClickListener(v -> ReviewsDetailActivity.start(getItemView().getContext(),comments.link));
    }
}
