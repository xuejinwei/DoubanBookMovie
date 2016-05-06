package com.xuejinwei.doubanbookmovie.doubanbookmovie.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.twiceyuan.commonadapter.library.LayoutId;
import com.twiceyuan.commonadapter.library.ViewId;
import com.twiceyuan.commonadapter.library.holder.CommonHolder;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.R;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.model.Comments;

/**
 * Created by xuejinwei on 16/5/4.
 * Email:xuejinwei@outlook.com
 * 短评holder
 */
@LayoutId(R.layout.item_comments)
public class CommentsHolder extends CommonHolder<Comments> {

    // 绑定 View 资源
    @ViewId(R.id.iv_comment) ImageView iv_comment;
    @ViewId(R.id.tv_name)    TextView  tv_name;
    @ViewId(R.id.tv_comment) TextView  tv_comment;
    @ViewId(R.id.tv_time)    TextView  tv_time;
    @ViewId(R.id.tv_vote)    TextView  tv_vote;
    @ViewId(R.id.rb_comment) RatingBar rb_comment;

    public CommentsHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void bindData(Comments comments) {
        tv_comment.setText(comments.comment);
        tv_time.setText(comments.time);
        tv_name.setText(comments.title);
        tv_vote.setText(comments.comment_vote);
        Glide.with(getItemView().getContext()).load(comments.img).crossFade().into(iv_comment);
        int rating = Integer.parseInt(comments.rating);
        if (rating == 0) {
            rb_comment.setVisibility(View.GONE);
        } else {
            rb_comment.setVisibility(View.VISIBLE);
            rb_comment.setRating(rating / 10);
        }
    }
}
