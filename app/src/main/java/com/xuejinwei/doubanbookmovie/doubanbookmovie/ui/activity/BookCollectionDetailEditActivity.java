package com.xuejinwei.doubanbookmovie.doubanbookmovie.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.xuejinwei.doubanbookmovie.doubanbookmovie.R;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.model.CollectionUpdate;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.ui.base.activity.SwipeBackActivity;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.util.CommonUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by xuejinwei on 16/4/25.
 * Email:xuejinwei@outlook.com
 */
public class BookCollectionDetailEditActivity extends SwipeBackActivity {
    public static final String ARG_TYPE = "type";
    public static final String BOOK_ID  = "book_id";

    private CollectionUpdate collectionUpdate;

    @Bind(R.id.et_comment)   EditText           et_comment;
    @Bind(R.id.radioButton1) RadioButton        radioButton1;
    @Bind(R.id.radioButton2) RadioButton        radioButton2;
    @Bind(R.id.radioButton3) RadioButton        radioButton3;
    @Bind(R.id.radioGroup)   RadioGroup         radioGroup;
    @Bind(R.id.ratingBar)    AppCompatRatingBar ratingBar;
    @Bind(R.id.tv_rating)    TextView           tv_rating;
    @Bind(R.id.toolbar)      Toolbar            toolbar;
    @Bind(R.id.btn_submit)   Button             btn_submit;

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
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_collection_detail_edit);
        ButterKnife.bind(this);
        mType = (Type) getIntent().getSerializableExtra(ARG_TYPE);
        book_id = getIntent().getStringExtra(BOOK_ID);
        collectionUpdate = new CollectionUpdate();
        if (mType == Type.EDIT) {
            setTitle("编辑搜藏");
            runRxTaskOnUi(mApiWrapper.getBookCollectionsDetail(book_id), bookCollections -> {
                collectionUpdate.comment = bookCollections.comment;
                collectionUpdate.status = bookCollections.status;
                collectionUpdate.rating = String.valueOf(bookCollections.rating.value);

                et_comment.setText(bookCollections.comment);
                ratingBar.setRating(Float.valueOf(String.valueOf(bookCollections.rating.value)));
                tv_rating.setText(String.valueOf(bookCollections.rating.value));
                if (bookCollections.status.equals("wish")) {
                    radioButton1.setChecked(true);
                }
                if (bookCollections.status.equals("reading")) {
                    radioButton2.setChecked(true);
                }
                if (bookCollections.status.equals("doing")) {
                    radioButton2.setChecked(true);
                }
            });
        } else if (mType == Type.ADD) {
            setTitle("添加搜藏");
        } else {
            CommonUtil.toast("类型错误");
            finish();
        }
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar1, float rating, boolean fromUser) {
//                tv_rating.setText((int) rating);
            }
        });
        btn_submit.setOnClickListener(v -> {
            collectionUpdate.comment = et_comment.getText().toString();
            collectionUpdate.rating = String.valueOf((int) ratingBar.getRating());
            collectionUpdate.status = getStatus();
            if (mType == Type.EDIT) {
                runRxTaskOnUi(mApiWrapper.updateBookCollections(book_id, collectionUpdate), bookCollections -> {
                    CommonUtil.toast("更新搜藏成功");
                    finish();

                });
            } else if (mType == Type.ADD) {
                runRxTaskOnUi(mApiWrapper.addBookCollections(book_id, collectionUpdate), bookCollections -> {
                    CommonUtil.toast("添加搜藏成功");
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
}
