package com.xuejinwei.doubanbookmovie.kotlin.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.xuejinwei.doubanbookmovie.kotlin.R;
import com.xuejinwei.doubanbookmovie.kotlin.ui.base.activity.SwipeBackActivity;
import com.xuejinwei.doubanbookmovie.kotlin.ui.fragment.SubjectCollectionFragment;

/**
 * Created by xuejinwei on 16/4/14.
 * Email:xuejinwei@outlook.com
 */
public class SubjectCollectionActivity extends SwipeBackActivity {
    public static final String SUBJECT_COLLECTION_TYPE = "subject_collection_type";
    public String mType;

    public static void start(Activity activity, String subject_collection_type) {
        Intent starter = new Intent(activity, SubjectCollectionActivity.class);
        starter.putExtra(SUBJECT_COLLECTION_TYPE, subject_collection_type);
        activity.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_collection);
        mType = getIntent().getStringExtra(SUBJECT_COLLECTION_TYPE);
        SubjectCollectionFragment.inject(this, R.id.ll_container_base, mType);
    }
}
