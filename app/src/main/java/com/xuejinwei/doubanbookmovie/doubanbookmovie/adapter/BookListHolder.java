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
import com.xuejinwei.doubanbookmovie.doubanbookmovie.model.Book;

/**
 * Created by xuejinwei on 16/4/21.
 * Email:xuejinwei@outlook.com
 */
@LayoutId(R.layout.item_movie_list)
public class BookListHolder extends CommonHolder<Book> {

    // 绑定 View 资源
    @ViewId(R.id.iv_avatar) ImageView          iv_avatar;
    @ViewId(R.id.tv_title)  TextView           tv_title;
    @ViewId(R.id.ll_rating) LinearLayout       ll_rating;
    @ViewId(R.id.ratingBar) AppCompatRatingBar mRatingBar;
    @ViewId(R.id.tv_rating) TextView           tv_rating;
    @ViewId(R.id.tv_price)  TextView           tv_price;
    @ViewId(R.id.tv_author) TextView           tv_author;

    public BookListHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void bindData(Book book) {
        Glide.with(getItemView().getContext()).load(book.images.large).crossFade().into(iv_avatar);
        tv_title.setText(book.title);
        if (!book.rating.average.equals("0")) {
            tv_rating.setText(book.rating.average);
            mRatingBar.setRating(Float.parseFloat(book.rating.average));
            ll_rating.setVisibility(View.VISIBLE);
        } else {
            ll_rating.setVisibility(View.GONE);
        }
        tv_price.setText(book.price);
        tv_author.setText(book.getAuthor());

    }
}
