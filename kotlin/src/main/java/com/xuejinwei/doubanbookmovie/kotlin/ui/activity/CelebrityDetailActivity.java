package com.xuejinwei.doubanbookmovie.kotlin.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.xuejinwei.doubanbookmovie.kotlin.R;
import com.xuejinwei.doubanbookmovie.kotlin.ui.base.activity.SwipeBackActivity;
import com.xuejinwei.doubanbookmovie.kotlin.util.CommonUtil;

import net.qiujuer.genius.blur.StackBlur;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by xuejinwei on 16/4/1.
 * Email:xuejinwei@outlook.com
 */
public class CelebrityDetailActivity extends SwipeBackActivity {
    private static final String CELEBRITY_ID = "celebrity_id";
    @Bind(R.id.imagehead_bg)          ImageView               imagehead_bg;
    @Bind(R.id.imagehead)             ImageView               imagehead;
    @Bind(R.id.toolbar)               Toolbar                 toolbar;
    @Bind(R.id.collapsing_toolbar)    CollapsingToolbarLayout collapsing_toolbar;
    @Bind(R.id.app_bar_layout)        AppBarLayout            app_bar_layout;
    @Bind(R.id.progressBar)           ProgressBar             progressBar;
    @Bind(R.id.tv_title)              TextView                tv_title;
    @Bind(R.id.ll_name_en)            LinearLayout            ll_name_en;
    @Bind(R.id.ll_born_place)         LinearLayout            ll_born_place;
    @Bind(R.id.expandable_text)       TextView                expandable_text;
    @Bind(R.id.expand_collapse)       ImageButton             expand_collapse;
    @Bind(R.id.expandable_tv_summary) ExpandableTextView      expandable_tv_summary;
    @Bind(R.id.rv_works)              RecyclerView            rv_works;
    @Bind(R.id.ll_root)               LinearLayout            ll_root;
    @Bind(R.id.nested_scroll_view)    NestedScrollView        nested_scroll_view;
    @Bind(R.id.tv_name_en)            TextView                tv_name_en;
    @Bind(R.id.tv_born_place)         TextView                tv_born_place;

    private String mCelebrityId;

    public static void start(Activity activity, String celebrity_id) {

        Intent starter = new Intent(activity, CelebrityDetailActivity.class);
        starter.putExtra(CELEBRITY_ID, celebrity_id);
        activity.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_celebrity_detail);
        ButterKnife.bind(this);
        collapsing_toolbar.setTitle("");
//        ll_root.setVisibility(View.GONE);

        mCelebrityId = getIntent().getStringExtra(CELEBRITY_ID);
        if (mCelebrityId == null) {
            finish();
            CommonUtil.toast("celebrity_id无效");
        }

        runRxTaskOnUi(mApiWrapper.getCelebrityDetail(mCelebrityId), celebrity -> {
            Glide.with(CelebrityDetailActivity.this).load(celebrity.avatars.large).into(imagehead);
            Glide.with(CelebrityDetailActivity.this).load(celebrity.avatars.small).asBitmap().into(new SimpleTarget<Bitmap>() {
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

            tv_title.setText(celebrity.name);
            tv_name_en.setText(celebrity.name_en);
            tv_born_place.setText(celebrity.born_place);
            ll_root.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        });
    }
}
