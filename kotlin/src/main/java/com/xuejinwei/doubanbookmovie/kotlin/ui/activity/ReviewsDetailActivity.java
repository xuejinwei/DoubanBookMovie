package com.xuejinwei.doubanbookmovie.kotlin.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.github.siyamed.shapeimageview.CircularImageView;
import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.xuejinwei.doubanbookmovie.kotlin.R;
import com.xuejinwei.doubanbookmovie.kotlin.ui.base.activity.SwipeBackActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by xuejinwei on 16/5/9.
 * Email:xuejinwei@outlook.com
 */
public class ReviewsDetailActivity extends SwipeBackActivity {
    private static final String REVIEW_LINK = "review_link";

    @Bind(R.id.iv_comment)                 CircularImageView  iv_comment;
    @Bind(R.id.tv_name)                    TextView           tv_name;
    @Bind(R.id.rb_comment)                 RatingBar          rb_comment;
    @Bind(R.id.expandable_text)            TextView           expandable_text;
    @Bind(R.id.expand_collapse)            ImageButton        expand_collapse;
    @Bind(R.id.expandable_tv_reviews_text) ExpandableTextView expandable_tv_reviews_text;
    @Bind(R.id.progressBar)                ProgressBar        progressBar;
    @Bind(R.id.ll_root)                    LinearLayout       ll_root;
    @Bind(R.id.tv_title)                   TextView           tv_title;

    public static void start(Context context, String review_link) {
        Intent starter = new Intent(context, ReviewsDetailActivity.class);
        starter.putExtra(REVIEW_LINK, review_link);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews_detail);
        ButterKnife.bind(this);

        setTitle("评价详情");
        progressBar.setVisibility(View.VISIBLE);
        ll_root.setVisibility(View.GONE);

        String review_link = getIntent().getStringExtra(REVIEW_LINK);
        runRxTaskOnUi(mApiWrapper.getReviewsDetail(review_link), reviewsDetail -> {
            tv_title.setText(reviewsDetail.title);
            Glide.with(this).load(reviewsDetail.user_img).asBitmap().into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    iv_comment.setImageBitmap(resource);
                }
            });
            tv_name.setText(reviewsDetail.user_name);
            rb_comment.setRating(reviewsDetail.rating);
            expandable_tv_reviews_text.setText(reviewsDetail.reviews_text);

            progressBar.setVisibility(View.GONE);
            ll_root.setVisibility(View.VISIBLE);
        });
    }
}
