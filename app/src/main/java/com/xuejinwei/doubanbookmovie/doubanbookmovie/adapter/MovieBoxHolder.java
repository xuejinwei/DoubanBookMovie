package com.xuejinwei.doubanbookmovie.doubanbookmovie.adapter;

import android.graphics.Bitmap;
import android.support.v4.content.ContextCompat;
import android.support.v7.graphics.Palette;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.twiceyuan.commonadapter.library.LayoutId;
import com.twiceyuan.commonadapter.library.ViewId;
import com.twiceyuan.commonadapter.library.holder.CommonHolder;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.R;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.model.MovieSimple;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.util.DensityUtil;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.util.ImageUtil;

import rx.functions.Action1;

/**
 * Created by xuejinwei on 16/3/25.
 */
@LayoutId(R.layout.item_movie_box)
public class MovieBoxHolder extends CommonHolder<MovieSimple> {

    // 绑定 View 资源
    @ViewId(R.id.img_avatar)   SimpleDraweeView img_avatar;
    @ViewId(R.id.tv_title)     TextView         tv_title;
    @ViewId(R.id.ratingBar)    RatingBar        ratingBar;
    @ViewId(R.id.tv_rating)    TextView         tv_rating;
    @ViewId(R.id.rl_buttom)    RelativeLayout   rl_buttom;
    @ViewId(R.id.rl_root_view) RelativeLayout   rl_root_view;

    static int img_heigh;
    int mColor;

    public MovieBoxHolder(View itemView) {
        super(itemView);
        /**
         *  图片的高度，是 图片宽度的1.5倍，宽度是 屏幕宽度减去padding等的三分之一
         */
        img_heigh = (DensityUtil.getScreenW(getItemView().getContext()) - DensityUtil.dp2px(32f)) / 3 / 2 * 3;

    }

    @Override
    public void bindData(MovieSimple movieSimple) {
        img_avatar.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, img_heigh));

        ImageUtil.setupImage(movieSimple.images.large)
                .subscribe(new Action1<Bitmap>() {
                    @Override
                    public void call(Bitmap bitmap) {
                        img_avatar.setImageBitmap(bitmap);
                        /**
                         * 返回一个柔和的颜色
                         */
                        Palette.Swatch swatchMuted = Palette.from(bitmap).generate().getMutedSwatch();
                        /**
                         * 返回一个活力的颜色
                         */
                        Palette.Swatch swatchVibrant = Palette.from(bitmap).generate().getVibrantSwatch();
                        if (swatchMuted != null) {
                            mColor = swatchMuted.getRgb();//百分之5透明格式化
                        } else {
                            if (swatchVibrant != null) {
                                mColor = swatchVibrant.getRgb();
                            } else {
                                mColor = ContextCompat.getColor(getItemView().getContext(), R.color.colorPrimary);
                            }
                        }
                        //百分之5透明格式化mcolor 并且设置到背景
                        rl_buttom.setBackgroundColor(mColor & 0x00ffffff | 0xf2000000);
                    }
                });
        tv_title.setText(movieSimple.title);
        if (movieSimple.rating.average.equals(0)) {
            tv_rating.setText("暂无评分");
            ratingBar.setVisibility(View.GONE);
        } else {
            tv_rating.setText(movieSimple.rating.average);
            ratingBar.setRating(Float.parseFloat(movieSimple.rating.average) / 2);
            ratingBar.setVisibility(View.VISIBLE);
        }
    }
}
