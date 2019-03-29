package com.xuejinwei.doubanbookmovie.kotlin.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.xuejinwei.doubanbookmovie.kotlin.R;
import com.xuejinwei.doubanbookmovie.kotlin.ui.base.activity.SwipeBackActivity;
import com.xuejinwei.doubanbookmovie.kotlin.util.CommonUtil;
import com.xuejinwei.doubanbookmovie.kotlin.util.StringUtils;
import com.xuejinwei.doubanbookmovie.kotlin.widget.ClearEditText;
import com.xuejinwei.doubanbookmovie.kotlin.widget.CountEditText;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by xuejinwei on 16/4/29.
 * Email:xuejinwei@outlook.com
 */
public class BookReviewEditActivity extends SwipeBackActivity {
    public static final String ARG_TYPE = "type";
    public static final String BOOK_ID  = "book_id";
    @Bind(R.id.et_title)       ClearEditText et_title;
    @Bind(R.id.ratingBar)      RatingBar     ratingBar;
    @Bind(R.id.tv_rating)      TextView      tv_rating;
    @Bind(R.id.et_description) CountEditText et_description;
    @Bind(R.id.btn_submit)     Button        btn_submit;

    public enum Type {
        ADD,      // 添加
        EDIT,     // 修改
    }

    private Type   mType; // 类型
    private String book_id; // 类型

    public static void start(Activity context, Type type, String book_id) {
        Intent starter = new Intent(context, BookReviewEditActivity.class);
        starter.putExtra(ARG_TYPE, type);
        starter.putExtra(BOOK_ID, book_id);
        context.startActivityForResult(starter, 0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_review_edit);
        ButterKnife.bind(this);

        mType = (Type) getIntent().getSerializableExtra(ARG_TYPE);
        book_id = getIntent().getStringExtra(BOOK_ID);
        ratingBar.setOnRatingBarChangeListener((bar, rating, fromUser) -> {
            int score = (int) rating;
            updateRatingText(score);
        });

        if (mType == Type.EDIT) {
            setTitle("编辑图书搜藏");
        } else if (mType == Type.ADD) {
            setTitle("添加图书搜藏");
            ratingBar.setRating(3);
        } else {
            CommonUtil.toast("类型错误");
            finish();
        }
        btn_submit.setOnClickListener(v -> {
            if (ratingBar.getRating() == 0) {
                CommonUtil.toast("请设置一个评分");
                return;
            }
            if (StringUtils.isEmpty(et_description.getEditText().getText().toString())) {
                CommonUtil.toast("请输入评价");
                return;
            }

            if (et_description.getEditText().getText().toString().length()<150) {
                CommonUtil.toast("评价正文必须大于150个字");
                return;
            }
            String comment = et_description.getEditText().getText().toString();
            String rating = String.valueOf(ratingBar.getRating());
            String title = et_title.getText().toString();

            if (mType == Type.EDIT) {
//                runRxTaskOnUi(mApiWrapper.updateBookCollections(book_id, collectionUpdate), bookCollections -> {
//                    CommonUtil.toast("更新搜藏成功");
//                    setResult(RESULT_OK);
//                    finish();
//
//                });
            } else if (mType == Type.ADD) {
                runRxTaskOnUi(mApiWrapper.addBookReviews(book_id, title,comment,rating), bookCollections -> {
                    CommonUtil.toast("添加搜藏成功");
                    setResult(RESULT_OK);
                    finish();
                });
            }
        });
    }

    public void updateRatingText(int score) {
        String[] ratingDescription = {"请选择", "较差", "中等", "很好", "优秀", "极好"};
        int colors[] = {
                R.color.text_color_grey,
                R.color.google_red,
                R.color.text_color_grey_full,
                R.color.md_green_500,
                R.color.md_green_400,
                R.color.md_green_300
        };
        LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(ContextCompat.getColor(this, colors[score]), PorterDuff.Mode.SRC_ATOP);
        tv_rating.setTextColor(ContextCompat.getColor(this, colors[score]));
        tv_rating.setText(ratingDescription[score]);
    }
}
