package com.xuejinwei.doubanbookmovie.doubanbookmovie.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.xuejinwei.doubanbookmovie.doubanbookmovie.R;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.ui.base.activity.SwipeBackActivity;

/**
 * Created by xuejinwei on 16/4/29.
 * Email:xuejinwei@outlook.com
 */
public class BookReviewEditActivity extends SwipeBackActivity{
    
    public static void start(Context context) {
        Intent starter = new Intent(context, BookReviewEditActivity.class);
        context.startActivity(starter);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_review_edit);
    }
}
