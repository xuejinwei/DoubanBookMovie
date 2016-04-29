package com.xuejinwei.doubanbookmovie.doubanbookmovie.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.R;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.model.BookCollections;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.ui.base.activity.SwipeBackActivity;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.util.CommonUtil;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.widget.DialogUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.functions.Action1;

/**
 * Created by xuejinwei on 16/4/25.
 * Email:xuejinwei@outlook.com
 */
public class BookCollectionDetailActivity extends SwipeBackActivity {
    public static final String COLLECTION_ID = "collection_id";
    String bookid;

    @Bind(R.id.ivImage)            ImageView               ivImage;
    @Bind(R.id.toolbar)            Toolbar                 toolbar;
    @Bind(R.id.collapsing_toolbar) CollapsingToolbarLayout collapsing_toolbar;
    @Bind(R.id.tv_status)          TextView                tv_status;
    @Bind(R.id.tv_update)          TextView                tv_update;
    @Bind(R.id.tv_commit)          TextView                tv_commit;

    public static void start(Activity activity, String book_id) {
        Intent starter = new Intent(activity, BookCollectionDetailActivity.class);
        starter.putExtra(COLLECTION_ID, book_id);
        activity.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_collection_detail);
        ButterKnife.bind(this);
        bookid = getIntent().getStringExtra(COLLECTION_ID);
        runRxTaskOnUi(mApiWrapper.getBookCollectionsDetail(bookid), new Action1<BookCollections>() {
            @Override
            public void call(BookCollections bookCollections) {
                Glide.with(BookCollectionDetailActivity.this)
                        .load(bookCollections.book.images.large)
                        .fitCenter()
                        .into(ivImage);
                tv_commit.setText(bookCollections.comment);
                tv_status.setText(bookCollections.getStatus());
                tv_update.setText(bookCollections.updated);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_book_collection_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_book_collection_edit) {
            BookCollectionDetailEditActivity.start(BookCollectionDetailActivity.this, BookCollectionDetailEditActivity.Type.EDIT, bookid);
            return true;
        }

        if (item.getItemId() == R.id.action_book_collection_delete) {
            DialogUtil.simpleMessage(this, "确定删除？", () -> runRxTaskOnUi(mApiWrapper.deleteBookCollections(bookid), success -> {
                CommonUtil.toast("删除成功");
                finish();
            }));

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
