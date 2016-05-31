package com.xuejinwei.doubanbookmovie.doubanbookmovie.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.R;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.model.Book;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.ui.base.activity.SwipeBackActivity;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.ui.fragment.CommentsFragment;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.util.CommonUtil;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.util.StringUtils;

import net.qiujuer.genius.blur.StackBlur;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by xuejinwei on 16/4/19.
 * Email:xuejinwei@outlook.com
 */
public class BookDetailActivity extends SwipeBackActivity {


    @Bind(R.id.fab_collection)               FloatingActionButton    fab_collection;
    @Bind(R.id.imagehead_bg)                 ImageView               imagehead_bg;
    @Bind(R.id.imagehead)                    ImageView               imagehead;
    @Bind(R.id.toolbar)                      Toolbar                 toolbar;
    @Bind(R.id.collapsing_toolbar)           CollapsingToolbarLayout collapsing_toolbar;
    @Bind(R.id.app_bar_layout)               AppBarLayout            app_bar_layout;
    @Bind(R.id.progressBar)                  ProgressBar             progressBar;
    @Bind(R.id.tv_title)                     TextView                tv_title;
    @Bind(R.id.ratingBar)                    AppCompatRatingBar      ratingBar;
    @Bind(R.id.tv_rating)                    TextView                tv_rating;
    @Bind(R.id.tv_author)                    TextView                tv_author;
    @Bind(R.id.tv_publisher)                 TextView                tv_publisher;
    @Bind(R.id.tv_publish_date)              TextView                tv_publish_date;
    @Bind(R.id.expandable_tv_summary)        ExpandableTextView      expandable_tv_summary;
    @Bind(R.id.expandable_tv_summary_author) ExpandableTextView      expandable_tv_summary_author;
    @Bind(R.id.expandable_tv_catalog)        ExpandableTextView      expandable_tv_catalog;
    @Bind(R.id.ll_root)                      LinearLayout            ll_root;
    @Bind(R.id.nested_scroll_view)           NestedScrollView        nested_scroll_view;

    private Book mBook;
    private String book_id;
    private static final String BOOK_ID = "book_id";

    public static void start(Activity activity, String book_id) {
        Intent starter = new Intent(activity, BookDetailActivity.class);
        starter.putExtra(BOOK_ID, book_id);
        activity.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        ButterKnife.bind(this);
        collapsing_toolbar.setTitle("");
        progressBar.setVisibility(View.VISIBLE);
        ll_root.setVisibility(View.GONE);

        book_id = getIntent().getStringExtra(BOOK_ID);
        if (book_id == null) {
            finish();
            CommonUtil.toast("movie_id无效");
        }
        CommentsFragment.inject(this, R.id.framelayout_book_comments, CommentsFragment.Type.BOOK_COMMENTS, book_id);
        CommentsFragment.inject(this, R.id.framelayout_book_reviews, CommentsFragment.Type.BOOK_REVIEWS, book_id);
        runRxTaskOnUi(mApiWrapper.getBookById(book_id), this::refresh);
        fab_collection.setOnClickListener(v -> BookCollectionDetailEditActivity.start(this, BookCollectionDetailEditActivity.Type.ADD, book_id));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_book_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_book_review_edit) {
            BookReviewEditActivity.start(this,BookReviewEditActivity.Type.ADD,book_id);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            CommentsFragment.inject(this, R.id.framelayout_book_comments, CommentsFragment.Type.BOOK_COMMENTS, book_id);
            CommentsFragment.inject(this, R.id.framelayout_book_reviews, CommentsFragment.Type.BOOK_REVIEWS, book_id);
        }
    }

    private void refresh(Book book){
        Glide.with(BookDetailActivity.this).load(book.images.large).into(imagehead);
        Glide.with(BookDetailActivity.this).load(book.images.small).asBitmap().into(new SimpleTarget<Bitmap>() {
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

        if (!book.rating.average.equals("0")) {
            ratingBar.setRating(Float.parseFloat(book.rating.average));
            tv_rating.setText(book.rating.average);
        } else {
            tv_rating.setText("(暂无评分)");
        }
        tv_title.setText(book.title);
        tv_publisher.setText(book.publisher);
        tv_publish_date.setText(book.pubdate);
        tv_author.setText(book.getAuthor());

        if (StringUtils.isNotEmpty(book.summary)) {
            expandable_tv_summary.setText(book.summary);
            ((LinearLayout) expandable_tv_summary.getParent()).setVisibility(View.VISIBLE);
        }

        if (StringUtils.isNotEmpty(book.author_intro)) {
            expandable_tv_summary_author.setText(book.author_intro);
            ((LinearLayout) expandable_tv_summary_author.getParent()).setVisibility(View.VISIBLE);
        }

        if (StringUtils.isNotEmpty(book.catalog)) {
            expandable_tv_catalog.setText(book.catalog);
            ((LinearLayout) expandable_tv_catalog.getParent()).setVisibility(View.VISIBLE);
        }

        ll_root.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }
}
