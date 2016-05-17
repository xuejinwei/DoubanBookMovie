package com.xuejinwei.doubanbookmovie.doubanbookmovie.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.twiceyuan.commonadapter.library.adapter.CommonAdapter;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.R;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.adapter.CelebrityHolder;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.model.SimpleCelebrity;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.ui.base.activity.SwipeBackActivity;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.ui.fragment.CommentsFragment;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.util.CommonUtil;

import net.qiujuer.genius.blur.StackBlur;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by xuejinwei on 16/3/31.
 * Email:xuejinwei@outlook.com
 * 电影详情activity
 */
public class MovieDetailActivity extends SwipeBackActivity {

    private static final String MOVIE_ID = "movie_id";
    @Bind(R.id.imagehead)             ImageView               imagehead;
    @Bind(R.id.toolbar)               Toolbar                 toolbar;
    @Bind(R.id.collapsing_toolbar)    CollapsingToolbarLayout collapsing_toolbar;
    @Bind(R.id.tv_title)              TextView                tv_titles;
    @Bind(R.id.tv_genres)             TextView                tv_genres;
    @Bind(R.id.tv_directors)          TextView                tv_directors;
    @Bind(R.id.tv_casts)              TextView                tv_casts;
    @Bind(R.id.expandable_tv_summary) ExpandableTextView      expandable_tv_summary;
    @Bind(R.id.tv_rating)             TextView                tv_rating;
    @Bind(R.id.ratingBar)             AppCompatRatingBar      ratingBar;
    @Bind(R.id.tv_rating_sum)         TextView                tv_rating_sum;
    @Bind(R.id.imagehead_bg)          ImageView               imagehead_bg;
    @Bind(R.id.ll_root)               LinearLayout            ll_root;
    @Bind(R.id.progressBar)           ProgressBar             progressBar;
    @Bind(R.id.rv_casts)              RecyclerView            rv_casts;
    @Bind(R.id.fab_collection)        FloatingActionButton    fab_collection;

    private String                                          mMovieId;
    private CommonAdapter<SimpleCelebrity, CelebrityHolder> mSimpleCelebrityCommonAdapter;

    public static void start(Activity activity, String movie_id) {

        Intent starter = new Intent(activity, MovieDetailActivity.class);
        starter.putExtra(MOVIE_ID, movie_id);
        activity.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);
        collapsing_toolbar.setTitle("");
        ll_root.setVisibility(View.GONE);

        mMovieId = getIntent().getStringExtra(MOVIE_ID);
        if (mMovieId == null) {
            finish();
            CommonUtil.toast("movie_id无效");
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mSimpleCelebrityCommonAdapter = new CommonAdapter<>(this, CelebrityHolder.class);
        mSimpleCelebrityCommonAdapter.setOnItemClickListener((position, simpleCelebrity) -> CelebrityDetailActivity.start(MovieDetailActivity.this, simpleCelebrity.id));

        rv_casts.setLayoutManager(linearLayoutManager);
        rv_casts.setAdapter(mSimpleCelebrityCommonAdapter);
        CommentsFragment.inject(this, R.id.framelayout_movie_comments, CommentsFragment.Type.MOVIE_COMMENTS, mMovieId);
        CommentsFragment.inject(this, R.id.framelayout_movie_reviews, CommentsFragment.Type.MOVIE_REVIEWS, mMovieId);
        runRxTaskOnUi(mApiWrapper.getMovieById(mMovieId), movie -> {
            Glide.with(MovieDetailActivity.this).load(movie.images.large).into(imagehead);
            Glide.with(MovieDetailActivity.this).load(movie.images.small).asBitmap().into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {

                    Bitmap scaledBitmap = Bitmap.createScaledBitmap(resource,
                            resource.getWidth() / 2,
                            resource.getHeight() / 2,
                            false);
                    Bitmap blurBitmap = StackBlur.blur(scaledBitmap, 8, true);
                    imagehead_bg.setImageBitmap(blurBitmap);

                }
            });

            tv_rating.setText(movie.rating.average);
            ratingBar.setRating(Float.parseFloat(movie.rating.average));
            if (!movie.rating.average.equals("0")) {
                tv_rating_sum.setText("(" + movie.ratings_count + "人评分)");
            } else {
                tv_rating_sum.setText("(暂无评分)");
            }
            tv_titles.setText(movie.title);
            tv_genres.setText(movie.getGenres());
            tv_directors.setText(movie.getDirectors());
            tv_casts.setText(movie.getCasts());
            expandable_tv_summary.setText(movie.summary);
            mSimpleCelebrityCommonAdapter.clear();
            mSimpleCelebrityCommonAdapter.addAll(movie.casts);
            mSimpleCelebrityCommonAdapter.notifyDataSetChanged();
            ll_root.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            fab_collection.setOnClickListener(v -> MovieCollectionDetailEditActivity.start(MovieDetailActivity.this, MovieCollectionDetailEditActivity.Type.ADD, movie.id, movie.title, movie.images.large));
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}
