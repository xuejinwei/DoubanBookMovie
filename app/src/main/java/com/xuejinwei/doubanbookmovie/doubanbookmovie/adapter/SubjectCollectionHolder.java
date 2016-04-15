package com.xuejinwei.doubanbookmovie.doubanbookmovie.adapter;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.twiceyuan.commonadapter.library.LayoutId;
import com.twiceyuan.commonadapter.library.ViewId;
import com.twiceyuan.commonadapter.library.holder.CommonHolder;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.R;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.model.SubjectCollectionItems;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.ui.activity.MovieDetailActivity;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.util.CommonUtil;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.util.DensityUtil;

import java.text.DecimalFormat;

/**
 * Created by xuejinwei on 16/4/13.
 * Email:xuejinwei@outlook.com
 */
@LayoutId(R.layout.item_movie_box_api)
public class SubjectCollectionHolder extends CommonHolder<SubjectCollectionItems> {
    // 绑定 View 资源
    @ViewId(R.id.img_avatar)   ImageView      img_avatar;
    @ViewId(R.id.tv_title)     TextView       tv_title;
    @ViewId(R.id.ratingBar)    RatingBar      ratingBar;
    @ViewId(R.id.tv_rating)    TextView       tv_rating;
    @ViewId(R.id.rl_root_view) RelativeLayout rl_root_view;
    static                     int            img_heigh;

    public SubjectCollectionHolder(View itemView) {
        super(itemView);
        /**
         *  图片的高度，是 图片宽度的1.5倍，宽度是 屏幕宽度减去padding等的三分之一
         */
        img_heigh = (int) ((DensityUtil.getScreenW(getItemView().getContext())) / 1.95);
    }

    @Override
    public void bindData(SubjectCollectionItems subjectCollectionItems) {
        rl_root_view.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, img_heigh));
        Glide.with(getItemView().getContext()).load(subjectCollectionItems.cover.url).into(img_avatar);
        tv_title.setText(subjectCollectionItems.title);
        if (subjectCollectionItems.rating != null) {

            if (subjectCollectionItems.rating.value == null) {
                tv_rating.setText("暂无评分");
                ratingBar.setVisibility(View.GONE);
            } else {
                tv_rating.setText(new DecimalFormat("#.0").format(subjectCollectionItems.rating.value));
                ratingBar.setRating((float) (subjectCollectionItems.rating.value / 2));
                ratingBar.setVisibility(View.VISIBLE);
            }
        } else {
            tv_rating.setText("暂无评分");
            ratingBar.setVisibility(View.GONE);
        }
        getItemView().setOnClickListener(v -> {
            switch (subjectCollectionItems.type) {
                case "movie":
                    MovieDetailActivity.start((AppCompatActivity) getItemView().getContext(), subjectCollectionItems.id);
                    break;
                case "book":
                    CommonUtil.toast("bookdetail待开发");
                    break;
                default:
                    CommonUtil.toast("类型错误");
                    break;
            }
        });
    }
}
