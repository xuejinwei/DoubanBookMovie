package com.xuejinwei.doubanbookmovie.doubanbookmovie.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.xuejinwei.doubanbookmovie.doubanbookmovie.R;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.model.CollectionUpdate;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.ui.base.activity.SwipeBackActivity;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.util.CommonUtil;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.util.StringUtils;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.widget.CountEditText;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by xuejinwei on 16/4/25.
 * Email:xuejinwei@outlook.com
 */
public class BookCollectionDetailEditActivity extends SwipeBackActivity {
    public static final String ARG_TYPE = "type";
    public static final String BOOK_ID  = "book_id";
    @Bind(R.id.toolbar)      Toolbar       toolbar;
    @Bind(R.id.ratingBar)    RatingBar     ratingBar;
    @Bind(R.id.tv_rating)    TextView      tv_rating;
    @Bind(R.id.count_desc)   CountEditText count_desc;
    @Bind(R.id.radioButton1) RadioButton   radioButton1;
    @Bind(R.id.radioButton2) RadioButton   radioButton2;
    @Bind(R.id.radioButton3) RadioButton   radioButton3;
    @Bind(R.id.radioGroup)   RadioGroup    radioGroup;
    @Bind(R.id.btn_submit)   Button        btn_submit;

    private CollectionUpdate collectionUpdate;


    public enum Type {
        ADD,      // 添加
        EDIT,     // 修改
    }

    private Type   mType; // 类型
    private String book_id; // 类型

    public static void start(Activity context, Type type, String book_id) {
        Intent starter = new Intent(context, BookCollectionDetailEditActivity.class);
        starter.putExtra(ARG_TYPE, type);
        starter.putExtra(BOOK_ID, book_id);
        context.startActivityForResult(starter, 0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_collection_detail_edit);
        ButterKnife.bind(this);
        mType = (Type) getIntent().getSerializableExtra(ARG_TYPE);
        book_id = getIntent().getStringExtra(BOOK_ID);
        collectionUpdate = new CollectionUpdate();
        ratingBar.setOnRatingBarChangeListener((bar, rating, fromUser) -> {
            int score = (int) rating;
            updateRatingText(score);
        });

        if (mType == Type.EDIT) {
            setTitle("编辑搜藏");
            runRxTaskOnUi(mApiWrapper.getBookCollectionsDetail(book_id), bookCollections -> {
                collectionUpdate.comment = bookCollections.comment;
                collectionUpdate.status = bookCollections.status;

                count_desc.setText(bookCollections.comment);
                if (bookCollections.rating != null) {
                    collectionUpdate.rating = String.valueOf(bookCollections.rating.value);
                    ratingBar.setRating(Float.valueOf(String.valueOf(bookCollections.rating.value)));
                }
                if (bookCollections.status.equals("wish")) {
                    radioButton1.setChecked(true);
                }
                if (bookCollections.status.equals("reading")) {
                    radioButton2.setChecked(true);
                }
                if (bookCollections.status.equals("read")) {
                    radioButton3.setChecked(true);
                }
            });
        } else if (mType == Type.ADD) {
            setTitle("添加搜藏");
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
            if (StringUtils.isEmpty(count_desc.getCountText().getText().toString())) {
                CommonUtil.toast("请输入搜藏寄语");
                return;
            }
            collectionUpdate.comment = count_desc.getEditText().getText().toString();
            collectionUpdate.rating = String.valueOf(ratingBar.getRating());
            collectionUpdate.status = getStatus();
            if (mType == Type.EDIT) {
                runRxTaskOnUi(mApiWrapper.updateBookCollections(book_id, collectionUpdate), bookCollections -> {
                    CommonUtil.toast("更新搜藏成功");
                    setResult(RESULT_OK);
                    finish();

                });
            } else if (mType == Type.ADD) {
                runRxTaskOnUi(mApiWrapper.addBookCollections(book_id, collectionUpdate), bookCollections -> {
                    CommonUtil.toast("添加搜藏成功");
                    setResult(RESULT_OK);
                    finish();
                });
            }
        });
    }

    String getStatus() {
        if (radioGroup.getCheckedRadioButtonId() == R.id.radioButton1) {
            return "wish";
        }
        if (radioGroup.getCheckedRadioButtonId() == R.id.radioButton2) {
            return "reading";
        }
        if (radioGroup.getCheckedRadioButtonId() == R.id.radioButton3) {
            return "read";
        }
        return "wish";
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
