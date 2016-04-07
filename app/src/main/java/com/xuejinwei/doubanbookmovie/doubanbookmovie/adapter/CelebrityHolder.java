package com.xuejinwei.doubanbookmovie.doubanbookmovie.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.twiceyuan.commonadapter.library.LayoutId;
import com.twiceyuan.commonadapter.library.ViewId;
import com.twiceyuan.commonadapter.library.holder.CommonHolder;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.R;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.model.SimpleCelebrity;

/**
 * Created by xuejinwei on 16/4/1.
 * Email:xuejinwei@outlook.com
 */
@LayoutId(R.layout.item_celebrity)
public class CelebrityHolder extends CommonHolder<SimpleCelebrity> {
    // 绑定 View 资源
    @ViewId(R.id.iv_avatar) ImageView iv_avatar;
    @ViewId(R.id.tv_title)  TextView  tv_title;

    public CelebrityHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void bindData(SimpleCelebrity simpleCelebrity) {
        Glide.with(getItemView().getContext()).load(simpleCelebrity.avatars.large).into(iv_avatar);
        tv_title.setText(simpleCelebrity.name);
    }
}
