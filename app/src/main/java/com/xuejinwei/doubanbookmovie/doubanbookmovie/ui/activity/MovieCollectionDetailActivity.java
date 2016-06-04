package com.xuejinwei.doubanbookmovie.doubanbookmovie.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.DeleteCallback;
import com.bumptech.glide.Glide;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.R;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.model.MovieCollections;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.ui.base.activity.SwipeBackActivity;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.util.CommonUtil;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.util.DateUtil;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.widget.DialogUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by xuejinwei on 16/5/17.
 * Email:xuejinwei@outlook.com
 */
public class MovieCollectionDetailActivity extends SwipeBackActivity {
    public static final String MOVIE_ID = "movie_id";
    public static final String MOVIE_COLLECTION = "movie_collection";
    String mMovieid;
    MovieCollections mMovieCollections;
    @Bind(R.id.toolbar)     Toolbar            toolbar;
    @Bind(R.id.iv_avatar)   ImageView          iv_avatar;
    @Bind(R.id.tv_title)    TextView           tv_title;
    @Bind(R.id.ratingBar)   AppCompatRatingBar ratingBar;
    @Bind(R.id.tv_rating)   TextView           tv_rating;
    @Bind(R.id.ll_rating)   LinearLayout       ll_rating;
    @Bind(R.id.tv_author)   TextView           tv_author;
    @Bind(R.id.tv_price)    TextView           tv_price;
    @Bind(R.id.tv_update)   TextView           tv_update;
    @Bind(R.id.tv_commit)   TextView           tv_commit;
    @Bind(R.id.progressBar) ProgressBar        progressBar;
    @Bind(R.id.ll_root)     LinearLayout       ll_root;
    @Bind(R.id.cardview)    CardView           cardview;
    @Bind(R.id.rl_card)     RelativeLayout     rl_card;
    @Bind(R.id.iv_status)   ImageView          iv_status;
    @Bind(R.id.tv_genres)   TextView           tv_genres;

    int[] bg_card = {
            R.drawable.bg_blue,
            R.drawable.bg_gray,
            R.drawable.bg_green,
            R.drawable.bg_red,
            R.drawable.bg_yellow};

    public static void start(Activity activity, String movie_id, MovieCollections movieCollections) {
        Intent starter = new Intent(activity, MovieCollectionDetailActivity.class);
        starter.putExtra(MOVIE_ID, movie_id);
        starter.putExtra(MOVIE_COLLECTION, movieCollections);
        activity.startActivityForResult(starter,0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_collection_detail);
        ButterKnife.bind(this);
        setTitle("电影搜藏详情");
        mMovieid = getIntent().getStringExtra(MOVIE_ID);
        mMovieCollections = getIntent().getParcelableExtra(MOVIE_COLLECTION);
        rl_card.setBackgroundResource(bg_card[(int) (System.currentTimeMillis() % 5)]);
        onRefresh();
    }

    private void onRefresh() {
        ll_root.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        runRxTaskOnUi(mApiWrapper.getMovieById(mMovieid), movie -> {

            Glide.with(MovieCollectionDetailActivity.this)
                    .load(movie.images.large)
                    .fitCenter()
                    .into(iv_avatar);
            tv_title.setText(movie.title);
            if (!movie.rating.average.equals("0")) {
                tv_rating.setText(movie.rating.average);
                ratingBar.setRating(Float.parseFloat(movie.rating.average));
                ll_rating.setVisibility(View.VISIBLE);
            } else {
                ll_rating.setVisibility(View.GONE);
            }
            tv_genres.setText(movie.getGenres());
            tv_price.setText(movie.getDirectors());
            tv_author.setText(movie.getCasts());

            ll_root.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            cardview.setOnClickListener(v -> MovieDetailActivity.start(this, movie.id));
        });

        tv_commit.setText("        " + mMovieCollections.getMovieCollectionText());
        iv_status.setImageResource(getStatus(mMovieCollections));
        tv_update.setText(DateUtil.translateDate(mMovieCollections.getCreatedAt().getTime()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_book_collection_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_book_collection_edit) {
            MovieCollectionDetailEditActivity.start(MovieCollectionDetailActivity.this, MovieCollectionDetailEditActivity.Type.EDIT, mMovieCollections);
            return true;
        }

        if (item.getItemId() == R.id.action_book_collection_delete) {
            DialogUtil.simpleMessage(this, "确定删除？", new DialogUtil.OnConfirm() {
                @Override
                public void onConfirm() {
                    mMovieCollections.deleteInBackground(new DeleteCallback() {
                        @Override
                        public void done(AVException e) {
                            if (e == null) {
                                CommonUtil.toast("删除成功");
                                setResult(RESULT_OK);
                                finish();
                            }
                        }
                    });
                }
            });

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            onRefresh();
        }
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