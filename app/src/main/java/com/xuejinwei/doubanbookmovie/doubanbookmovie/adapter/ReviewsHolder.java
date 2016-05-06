package com.xuejinwei.doubanbookmovie.doubanbookmovie.adapter;

import android.view.View;
import android.widget.TextView;

import com.twiceyuan.commonadapter.library.LayoutId;
import com.twiceyuan.commonadapter.library.ViewId;
import com.twiceyuan.commonadapter.library.holder.CommonHolder;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.R;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.model.Reviews;

/**
 * Created by xuejinwei on 16/5/5.
 * Email:xuejinwei@outlook.com
 * 影评holder
 */
@LayoutId(R.layout.item_reviews)
public class ReviewsHolder extends CommonHolder<Reviews> {

    // 绑定 View 资源
    @ViewId(R.id.tv_title) TextView tv_title;
    @ViewId(R.id.tv_time)  TextView tv_time;
    @ViewId(R.id.tv_vote)  TextView tv_vote;

    public ReviewsHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void bindData(Reviews comments) {
        tv_time.setText(comments.time);
        tv_title.setText(comments.title);
        tv_vote.setText(comments.useful.substring(0, comments.useful.indexOf("/")));
    }
}
