package com.xuejinwei.doubanbookmovie.kotlin.util;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.facebook.common.executors.CallerThreadExecutor;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.xuejinwei.doubanbookmovie.kotlin.app.App;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by xuejinwei on 15/12/26.
 */
public class ImageUtil {

    public static Observable<Bitmap> setupImage(final String url) {
        return Observable.create(new Observable.OnSubscribe<Bitmap>() {
            @Override
            public void call(final Subscriber<? super Bitmap> subscriber) {
                // To get image using Fresco
                ImageRequest imageRequest = ImageRequestBuilder
                        .newBuilderWithSource(Uri.parse(url))
                        .setProgressiveRenderingEnabled(true)
                        .build();

                ImagePipeline imagePipeline = Fresco.getImagePipeline();
                DataSource<CloseableReference<CloseableImage>>
                        dataSource = imagePipeline.fetchDecodedImage(imageRequest, App.getApp());

                dataSource.subscribe(
                        new BaseBitmapDataSubscriber() {
                            @Override
                            public void onNewResultImpl(@Nullable Bitmap bitmap) {
                                subscriber.onNext(bitmap);
                                subscriber.onCompleted();
                            }

                            @Override
                            public void onFailureImpl(DataSource dataSource) {
                                subscriber.onError(new Error(dataSource.getFailureCause()));
                            }
                        }, CallerThreadExecutor.getInstance());
            }
        }).observeOn(AndroidSchedulers.mainThread());
    }
}
