package com.xuejinwei.doubanbookmovie.kotlin.ui.activity;

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

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.SaveCallback;
import com.orhanobut.logger.Logger;
import com.xuejinwei.doubanbookmovie.kotlin.R;
import com.xuejinwei.doubanbookmovie.kotlin.app.Setting;
import com.xuejinwei.doubanbookmovie.kotlin.model.MovieCollections;
import com.xuejinwei.doubanbookmovie.kotlin.ui.base.activity.SwipeBackActivity;
import com.xuejinwei.doubanbookmovie.kotlin.util.CommonUtil;
import com.xuejinwei.doubanbookmovie.kotlin.util.StringUtils;
import com.xuejinwei.doubanbookmovie.kotlin.widget.CountEditText;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by xuejinwei on 16/5/10.
 * Email:xuejinwei@outlook.com
 */
public class MovieCollectionDetailEditActivity extends SwipeBackActivity {
    public static final String ARG_TYPE         = "type";
    public static final String MOVIE_ID         = "movie_id";
    public static final String MOVIE_NAME       = "movie_name";
    public static final String MOVIE_IMG        = "movie_img";
    public static final String MOVIE_COLLECTION = "movie_collection";

    @Bind(R.id.toolbar)      Toolbar       toolbar;
    @Bind(R.id.ratingBar)    RatingBar     ratingBar;
    @Bind(R.id.tv_rating)    TextView      tv_rating;
    @Bind(R.id.count_desc)   CountEditText count_desc;
    @Bind(R.id.radioButton1) RadioButton   radioButton1;
    @Bind(R.id.radioButton2) RadioButton   radioButton2;
    @Bind(R.id.radioButton3) RadioButton   radioButton3;
    @Bind(R.id.radioGroup)   RadioGroup    radioGroup;
    @Bind(R.id.btn_submit)   Button        btn_submit;

    public enum Type {
        ADD,      // 添加
        EDIT,     // 修改
    }

    private Type   mType; // 类型
    private String movie_id;
    private String movie_name;
    private String movie_img;

    MovieCollections mMovieCollections;

    public static void start(Activity context, Type type, String movie_id, String movie_name, String movie_img) {
        Intent starter = new Intent(context, MovieCollectionDetailEditActivity.class);
        starter.putExtra(ARG_TYPE, type);
        starter.putExtra(MOVIE_ID, movie_id);
        starter.putExtra(MOVIE_NAME, movie_name);
        starter.putExtra(MOVIE_IMG, movie_img);
        context.startActivityForResult(starter, 0);
    }

    public static void start(Activity context, Type type, MovieCollections movieCollections) {
        Intent starter = new Intent(context, MovieCollectionDetailEditActivity.class);
        starter.putExtra(ARG_TYPE, type);
        starter.putExtra(MOVIE_COLLECTION, movieCollections);
        context.startActivityForResult(starter, 0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_collection_detail_edit);
        ButterKnife.bind(this);
        mType = (Type) getIntent().getSerializableExtra(ARG_TYPE);
        movie_id = getIntent().getStringExtra(MOVIE_ID);
        movie_name = getIntent().getStringExtra(MOVIE_NAME);
        movie_img = getIntent().getStringExtra(MOVIE_IMG);
        mMovieCollections = getIntent().getParcelableExtra(MOVIE_COLLECTION);

        ratingBar.setOnRatingBarChangeListener((bar, rating, fromUser) -> {
            int score = (int) rating;
            updateRatingText(score);
        });

        if (mType == Type.EDIT) {
            setTitle("编辑电影搜藏");

            count_desc.setText(mMovieCollections.getMovieCollectionText());
            ratingBar.setRating(mMovieCollections.getMovieRating());

            if (mMovieCollections.getMovieCollectionType() == 0) {
                radioButton1.setChecked(true);
            }
            if (mMovieCollections.getMovieCollectionType() == 1) {
                radioButton2.setChecked(true);
            }
            if (mMovieCollections.getMovieCollectionType() == 2) {
                radioButton3.setChecked(true);
            }
        } else if (mType == Type.ADD) {
            setTitle("添加电影搜藏");
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
            if (StringUtils.isEmpty(count_desc.getEditText().getText().toString())) {
                CommonUtil.toast("请输入搜藏寄语");
                return;
            }
            if (Setting.getAuthState()) {
                if (mType == Type.ADD) {
                    AVObject todo = new AVObject("MovieCollection");
                    todo.put("userId", Setting.getSetting(Setting.Key.douban_user_id, ""));
                    todo.put("movieId", movie_id);
                    todo.put("movieName", movie_name);
                    todo.put("movieImg", movie_img);
                    todo.put("movieRating", ratingBar.getRating());
                    todo.put("movieCollectionText", count_desc.getEditText().getText().toString());
                    todo.put("movieCollectionType", getStatus());
                    todo.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(AVException e) {
                            if (e == null) {
                                CommonUtil.toast("添加搜藏成功");
                                setResult(RESULT_OK);
                                finish();
                            } else {
                                Logger.e(e.getMessage());
                                if (e.getCode() == 137) {
                                    CommonUtil.toast("该电影已经搜藏，编辑请到个人中心电影搜藏");
                                }
                            }
                        }
                    });
                } else if (mType == Type.EDIT) {
                    mMovieCollections.put("movieRating", ratingBar.getRating());
                    mMovieCollections.put("movieCollectionText", count_desc.getEditText().getText().toString());
                    mMovieCollections.put("movieCollectionType", getStatus());
                    mMovieCollections.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(AVException e) {
                            if (e == null) {
                                CommonUtil.toast("更新搜藏成功");
                                setResult(RESULT_OK);
                                finish();
                            }
                        }
                    });
                }
            } else {
                CommonUtil.toast("请先登录");
                AuthActivity.start(this);
            }

        });
    }

    int getStatus() {
        if (radioGroup.getCheckedRadioButtonId() == R.id.radioButton1) {
            return 0;
        }
        if (radioGroup.getCheckedRadioButtonId() == R.id.radioButton2) {
            return 1;
        }
        if (radioGroup.getCheckedRadioButtonId() == R.id.radioButton3) {
            return 2;
        }
        return 1;
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