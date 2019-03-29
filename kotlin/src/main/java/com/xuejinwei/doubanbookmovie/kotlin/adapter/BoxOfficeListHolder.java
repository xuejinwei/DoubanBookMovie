package com.xuejinwei.doubanbookmovie.kotlin.adapter;

import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.twiceyuan.commonadapter.library.LayoutId;
import com.twiceyuan.commonadapter.library.ViewId;
import com.twiceyuan.commonadapter.library.holder.CommonHolder;
import com.xuejinwei.doubanbookmovie.kotlin.R;
import com.xuejinwei.doubanbookmovie.kotlin.model.JvHeBoxMovie;

/**
 * Created by xuejinwei on 16/5/31.
 * Email:xuejinwei@outlook.com
 */
@LayoutId(R.layout.item_movie_box_office)
public class BoxOfficeListHolder extends CommonHolder<JvHeBoxMovie> {

    // 绑定 View 资源
    @ViewId(R.id.tv_title)  TextView tv_title;
    @ViewId(R.id.tv_commit) TextView tv_commit;
    @ViewId(R.id.tv_update) TextView tv_update;
    @ViewId(R.id.iv_status) TextView iv_status;

    public BoxOfficeListHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void bindData(JvHeBoxMovie jvHeBoxMovie) {
        tv_title.setText(jvHeBoxMovie.name);

        String strw, strT;
        strw = "";
        strT = "";
        if (jvHeBoxMovie.wboxoffice < 10000) {
            strw = jvHeBoxMovie.wboxoffice + "万";
        } else {
            strw = jvHeBoxMovie.wboxoffice / 10000.0 + "亿";
        }

        if (jvHeBoxMovie.tboxoffice < 10000) {
            strT = jvHeBoxMovie.tboxoffice + "万";
        } else {
            strT = jvHeBoxMovie.tboxoffice / 10000.0 + "亿";
        }
        tv_commit.setText(strw);
        tv_update.setText(strT);

        if (jvHeBoxMovie.rid == 1) {
            iv_status.setText("Top"+jvHeBoxMovie.rid);
            iv_status.setTextColor(ContextCompat.getColor(getItemView().getContext(),R.color.google_red));
        } else if (jvHeBoxMovie.rid == 2) {
            iv_status.setText("Top"+jvHeBoxMovie.rid);
            iv_status.setTextColor(ContextCompat.getColor(getItemView().getContext(),R.color.google_yellow));
        } else if (jvHeBoxMovie.rid == 3) {
            iv_status.setText("Top"+jvHeBoxMovie.rid);
            iv_status.setTextColor(ContextCompat.getColor(getItemView().getContext(),R.color.google_blue));
        }else {
            iv_status.setText(""+jvHeBoxMovie.rid);
            iv_status.setTextColor(ContextCompat.getColor(getItemView().getContext(),R.color.text_color_dark));
        }

    }

}