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
import com.xuejinwei.doubanbookmovie.doubanbookmovie.model.BookCollections;

/**
 * Created by xuejinwei on 16/4/25.
 * Email:xuejinwei@outlook.com
 */
@LayoutId(R.layout.item_book_collection_list)
public class BookCollectionListHolder extends CommonHolder<BookCollections> {

    // 绑定 View 资源
    @ViewId(R.id.iv_avatar) ImageView          iv_avatar;
    @ViewId(R.id.tv_title)  TextView           tv_title;
    @ViewId(R.id.ll_rating) LinearLayout       ll_rating;
    @ViewId(R.id.ratingBar) AppCompatRatingBar mRatingBar;
    @ViewId(R.id.tv_rating) TextView           tv_rating;
    @ViewId(R.id.tv_status) TextView           tv_status;
    @ViewId(R.id.tv_commit) TextView           tv_commit;
    @ViewId(R.id.tv_update) TextView           tv_update;

    public BookCollectionListHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void bindData(BookCollections bookCollections) {
        Glide.with(getItemView().getContext()).load(bookCollections.book.images.large).crossFade().into(iv_avatar);
        tv_title.setText(bookCollections.book.title);
        if (bookCollections.rating != null) {
            tv_rating.setText(String.valueOf(bookCollections.rating.value));
            mRatingBar.setRating(Float.parseFloat(String.valueOf(bookCollections.rating.value)));

        }
        tv_commit.setText(bookCollections.comment);
        tv_update.setText(bookCollections.updated);
        tv_status.setText(bookCollections.getStatus());
    }
}
